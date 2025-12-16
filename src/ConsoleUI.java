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
        List<Contact> contacts = manager.getContacts();
        if (contacts.isEmpty()) {
            System.out.println(INFO_COLOR + "No contacts found." + RESET);
            return;
        }
        for (Contact c : contacts) {
            System.out.println(c);
        }
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
                if (c == null)
                    System.out.println("No match.");
                else
                    System.out.println(c);
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
        if (list == null || list.isEmpty()) {
            System.out.println("No matches.");
            return;
        }
        for (Contact c : list)
            System.out.println(c);
    }

    private void handleLogin() {
        System.out.print("Admin username: ");
        String username = scanner.nextLine();
        AdminUser admin = new AdminUser(username);

        System.out.print("Admin password: ");
        String password = scanner.nextLine();

        if (admin.checkPassword(password)) {
            System.out.println(ITALIC + "Verifying admin credentials ..." + RESET);
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

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        Contact c = new Contact(name, age, address, phone);
        manager.addContact(c);
        System.out.println("Added: " + c);
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

        System.out.print("New phone [" + c.getPhone() + "]: ");
        String newPhone = scanner.nextLine();
        if (newPhone.isBlank())
            newPhone = c.getPhone();

        manager.updateContact(c, newName, newAge, newAddress, newPhone);
        System.out.println("Updated: " + c);
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
