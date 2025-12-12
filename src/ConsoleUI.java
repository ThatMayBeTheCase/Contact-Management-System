import java.util.Scanner;
import java.util.List;

public class ConsoleUI {
    private final ContactManager manager;
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser = new GuestUser("guest");

    public ConsoleUI(ContactManager manager) {
        this.manager = manager;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("> ");
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
                default -> System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("\n[ " + roleLabel() + " ] Select an option:");
        System.out.println("1) Log in as admin");

        if (currentUser instanceof AdminUser) {
            System.out.println("2) List contacts");
            System.out.println("3) Search contacts");
            System.out.println("4) Add contact");
            System.out.println("5) Update contact");
            System.out.println("6) Delete contact");
            System.out.println("7) Log out");
        } else {
            System.out.println("2) List contacts");
            System.out.println("3) Search contacts");
        }
        System.out.println("0) Quit");
    }

    private String roleLabel() {
        return (currentUser instanceof AdminUser) ? "Admin" : "Guest";
    }

    private void listContacts() {
        List<Contact> contacts = manager.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        for (Contact c : contacts) {
            System.out.println(c);
        }
    }

    private void searchContacts() {
        System.out.print("Search term: ");
        String term = scanner.nextLine();
        List<Contact> matches = manager.search(term);
        if (matches.isEmpty()) {
            System.out.println("No matches.");
            return;
        }
        for (Contact c : matches) {
            System.out.println(c);
        }
    }

    private void handleLogin() {
        System.out.print("Admin username: ");
        String username = scanner.nextLine();
        AdminUser admin = new AdminUser(username);

        System.out.print("Admin password: ");
        String password = scanner.nextLine();

        if (admin.checkPassword(password)) {
            currentUser = admin;
            System.out.println("Logged in as admin.");
        }
        else {
            System.out.println("Wrong password, still a guest.");
        }
    }

    private void switchToGuest() {
        currentUser = new GuestUser("guest");
        System.out.println("Switched to guest.");
    }

    private void addContact() {
        if (!currentUser.canCreate()) {
            System.out.println("That option is only available for admin users.");
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
            System.out.println("That option is only available for admin users.");
            return;
        }
        System.out.print("Name of contact to update: ");
        String name = scanner.nextLine();
        Contact c = manager.findFirstByName(name);
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
        Contact c = manager.findFirstByName(name);
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
