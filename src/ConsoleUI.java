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
                case "2" -> switchToGuest();
                case "3" -> listContacts();
                case "4" -> searchContacts();
                case "5" -> addContact();
                case "6" -> updateContact();
                case "7" -> deleteContact();
                case "0" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("\n[ " + roleLabel() + " ] Select an option:");
        System.out.println("1) Log in as admin");
        System.out.println("2) Log out");
        System.out.println("3) List contacts");
        System.out.println("4) Search contacts");
        System.out.println("5) Add contact");
        System.out.println("6) Update contact");
        System.out.println("7) Delete contact");
        System.out.println("0) Quit");
    }

    private String roleLabel() {
        return (currentUser instanceof AdminUser) ? "admin" : "guest";
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
    private void switchToGuest() {}
    private void addContact() {}
    private void updateContact() {}
    private void deleteContact() {}
}
