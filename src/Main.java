public class Main {
    public static void main(String[] args) {
        ContactManager manager = new ContactManager();
        ConsoleUI ui = new ConsoleUI(manager);
        ui.run();
    }
}
