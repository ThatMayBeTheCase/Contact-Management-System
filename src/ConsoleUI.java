import java.util.Scanner;
import java.util.List;

public class ConsoleUI {
    private final ContactManager manager;
    private final SearchService search;
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser = new GuestUser("guest");

    // Styling constants
    private static final String RESET        = "\u001B[0m";

    private static final String BRIGHT_BLUE  = "\u001B[94m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String BRIGHT_YELLOW= "\u001B[93m";
    private static final String BRIGHT_RED   = "\u001B[91m";
    private static final String BRIGHT_CYAN  = "\u001B[96m";
    private static final String BRIGHT_PURPLE= "\u001B[95m";
    private static final String WHITE        = "\u001B[37m";

    private static final String TITLE_COLOR      = BRIGHT_BLUE;
    private static final String ROLE_ADMIN_COLOR = BRIGHT_PURPLE;
    private static final String ROLE_GUEST_COLOR = BRIGHT_CYAN;
    private static final String ERROR_COLOR      = BRIGHT_RED;
    private static final String SUCCESS_COLOR    = BRIGHT_GREEN;
    private static final String INFO_COLOR       = BRIGHT_YELLOW;
    private static final String PROMPT_COLOR     = WHITE;

    public static final String ITALIC = "\u001B[3m";

    private static final String SEP = "|=================================|";

    public ConsoleUI(ContactManager manager) {
        this.manager = manager;
        this.search = new SearchService(manager);
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print(PROMPT_COLOR + "> " + RESET);
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> handleLogin();
                case "2" -> listContacts();
                case "3" -> searchContacts();
                case "4" -> addContact();
                case "5" -> updateContact();
                case "6" -> deleteContact();
                case "7" -> switchToGuest();
                case "0" -> running = false;
                default -> System.out.println(ERROR_COLOR + "Invalid option." + RESET);
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println();
        System.out.println(TITLE_COLOR + SEP + RESET);
        System.out.println(TITLE_COLOR + " ~ • " + RESET + "Contact Management System" + TITLE_COLOR + " • ~ " + RESET);
        System.out.println(TITLE_COLOR + SEP + RESET);

        System.out.println("Current role: " + roleLabel());
        System.out.println();

        if (currentUser instanceof AdminUser) {
            System.out.println("2) List contacts");
            System.out.println("3) Search contacts");
            System.out.println("4) Add contact");
            System.out.println("5) Update contact");
            System.out.println("6) Delete contact");
            System.out.println("7) Log out");
        } else {
            System.out.println("1) Log in as admin");
            System.out.println("2) List contacts");
            System.out.println("3) Search contacts");
            System.out.println(INFO_COLOR + ITALIC + "   (Log in to see admin options)" + RESET);
        }
        System.out.println("0) Quit");
    }

    private String roleLabel() {
        if (currentUser instanceof AdminUser) {
            return ROLE_ADMIN_COLOR + "[ ADMIN ]" + RESET;
        } else {
            return ROLE_GUEST_COLOR + "[ GUEST ]" + RESET;
        }
    }

    private void listContacts() {
        printContactsTable(manager.getContacts());
    }

    private void searchContacts() {
        System.out.println("Search mode:");
        System.out.println("1) By last name (first match)");
        System.out.println("2) By first name (all matches)");
        System.out.println("3) By street (all matches)");
        System.out.println("4) Free search (all matches)");
        System.out.print("Choice: ");
        String mode = scanner.nextLine().trim();

        switch (mode) {
            case "1" -> {
                System.out.print("Last name: ");
                String ln = scanner.nextLine();
                Contact c = search.findFirstByLastName(ln);
                if (c == null) {
                    System.out.println("No match.");
                } else {
                    printContactsTable(List.of(c));
                }
            }
            case "2" -> {
                System.out.print("First name: ");
                String fn = scanner.nextLine();
                printList(search.findByFirstName(fn));
            }
            case "3" -> {
                System.out.print("Street: ");
                String st = scanner.nextLine();
                printList(search.findByStreet(st));
            }
            case "4" -> {
                System.out.print("Term: ");
                String term = scanner.nextLine();
                printList(search.freeSearch(term));
            }
            default -> System.out.println("Invalid search option.");
        }
    }

    private void printList(List<Contact> list) {
        printContactsTable(list);
    }

    private void handleLogin() {
        System.out.print("Admin username: ");
        String username = scanner.nextLine();
        AdminUser admin = new AdminUser(username);

        System.out.print("Admin password: ");
        String password = scanner.nextLine();

        if (admin.checkPassword(password)) {
            System.out.println(ITALIC + "\nVerifying admin credentials ..." + RESET);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            currentUser = admin;
            System.out.println(SUCCESS_COLOR + "Logged in as admin." + RESET);
        }
        else {
            System.out.println(ERROR_COLOR + "Wrong password." + RESET);
        }
    }

    private void switchToGuest() {
        System.out.println(ITALIC + "Signing out ..." + RESET);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        currentUser = new GuestUser("guest");
        System.out.println(SUCCESS_COLOR + "Switched to guest." + RESET);
    }

    private void addContact() {
        if (!currentUser.canCreate()) {
            System.out.println(ERROR_COLOR + "Invalid option." + RESET);
            return;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine();

        int age = readInt("Age: ");

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Contact c = new Contact(name, age, address);

        /*// Add phone numbers
        System.out.println("\n" + INFO_COLOR + "Add phone numbers (leave blank to skip):" + RESET);
*/
        System.out.print("Mobile number: ");
        String mobile = scanner.nextLine().trim();
        if (!mobile.isBlank()) {
            c.addPhoneNumber(new PhoneNumber(mobile, "mobile"));
        }

        System.out.print("Work number: ");
        String work = scanner.nextLine().trim();
        if (!work.isBlank()) {
            c.addPhoneNumber(new PhoneNumber(work, "work"));
        }

        manager.addContact(c);
        System.out.println(SUCCESS_COLOR + "\nContact added!" + RESET);
        System.out.println("Name: " + c.getName() + ", Age: " + c.getAge() + ", Address: " + c.getAddress());
        if (!c.getPhoneNumbers().isEmpty()) {
            System.out.println("Phone numbers: " + c.getPhone());
        }
        System.out.println();
    }

    private void updateContact() {
        if (!currentUser.canUpdate()) {
            System.out.println(ERROR_COLOR + "Invalid option." + RESET);
            return;
        }
        System.out.print("Name of contact to update: ");
        String name = scanner.nextLine();
        Contact c = search.findFirstByName(name);
        if (c == null) {
            System.out.println("No contact found with that name.");
            return;
        }

        System.out.print("New name [" + c.getName() + "]: ");
        String newName = scanner.nextLine();
        if (newName.isBlank())
            newName = c.getName();

        System.out.print("New age [" + c.getAge() + "]: ");
        String ageStr = scanner.nextLine();
        int newAge = c.getAge();
        if (!ageStr.isBlank()) {
            try {
                newAge = Integer.parseInt(ageStr);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid age, keeping old.");
            }
        }

        System.out.print("New address [" + c.getAddress() + "]: ");
        String newAddress = scanner.nextLine();
        if (newAddress.isBlank())
            newAddress = c.getAddress();

        // Update phone numbers
        System.out.println("\n" + INFO_COLOR + "Update phone numbers (leave blank to keep current):" + RESET);

        // Find existing numbers by type
        PhoneNumber existingMobile = null;
        PhoneNumber existingWork = null;

        for (PhoneNumber pn : c.getPhoneNumbers()) {
            if (pn.getType().equalsIgnoreCase("mobile") || pn.getType().equalsIgnoreCase("mobil")) {
                existingMobile = pn;
            } else if (pn.getType().equalsIgnoreCase("work") || pn.getType().equalsIgnoreCase("jobb")) {
                existingWork = pn;
            }
        }

        String currentMobile = existingMobile != null ? existingMobile.getNumber() : "";
        String currentWork = existingWork != null ? existingWork.getNumber() : "";

        System.out.print("Mobile number [" + currentMobile + "]: ");
        String newMobile = scanner.nextLine().trim();

        System.out.print("Work number [" + currentWork + "]: ");
        String newWork = scanner.nextLine().trim();

        // Update phone numbers
        c.clearPhoneNumbers();

        String finalMobile = newMobile.isBlank() ? currentMobile : newMobile;
        String finalWork = newWork.isBlank() ? currentWork : newWork;

        if (!finalMobile.isBlank()) {
            c.addPhoneNumber(new PhoneNumber(finalMobile, "mobile"));
        }
        if (!finalWork.isBlank()) {
            c.addPhoneNumber(new PhoneNumber(finalWork, "work"));
        }

        manager.updateContact(c, newName, newAge, newAddress, c.getPhone());
        System.out.println(SUCCESS_COLOR + "\nContact updated!" + RESET);
        System.out.println("Name: " + c.getName() + ", Age: " + c.getAge() + ", Address: " + c.getAddress());
        if (!c.getPhoneNumbers().isEmpty()) {
            System.out.println("Phone numbers: " + c.getPhone());
        }
        System.out.println();
    }
    private void deleteContact() {
        if (!currentUser.canDelete()) {
            System.out.println("That option is only available for admin users.");
            return;
        }

        System.out.print("Name of contact to delete: ");
        String name = scanner.nextLine();
        Contact c = search.findFirstByName(name);
        if (c == null) {
            System.out.println("No contact found with that name.");
            return;
        }

        System.out.print("Type 'yes' to confirm delete: ");
        String confirm = scanner.nextLine().trim();
        if ("yes".equalsIgnoreCase(confirm)) {
            manager.deleteContact(c);
            System.out.println("Contact deleted.");
        }
        else {
            System.out.println("Cancelled.");
        }
    }

    private void printContactsTable(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println(INFO_COLOR + "No contact found." + RESET);
            return;
        }

        System.out.println(INFO_COLOR + "\nFound " + contacts.size() + " contacts." + RESET);

        System.out.printf("%-3s %-17s %-5s %-20s %s%n",
                "#", "Name", "Age", "Address", "Phone");
        System.out.println("--------------------------------------------------------------");

        int i = 1;
        for (Contact c : contacts) {
            System.out.printf("%-3d %-17s %-5d %-20s %s%n",
                    i++,
                    cut(c.getName(), 17),
                    c.getAge(),
                    cut(c.getAddress(), 20),
                    c.getPhone()
            );
        }
    }

    private String cut(String s, int max) {
            if (s == null) return "";
            s = s.trim();
            if (s.length() <= max) return s;
            if (max <= 2) return s.substring(0, max);
            return s.substring(0, max - 2) + "..";
        }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
