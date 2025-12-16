public class Main {
    public static void main(String[] args) {
        // Create file-based repository for persistent storage
        ContactRepository repository = new FileContactRepository();

        // Inject repository into ContactManager
        ContactManager manager = new ContactManager(repository);

        // Create and run UI
        ConsoleUI ui = new ConsoleUI(manager);
        ui.run();
    }
}