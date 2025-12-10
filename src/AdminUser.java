public class AdminUser extends User{

    // Hardcoded admin password
    private static final String ADMIN_PASSWORD = "password123";

    // Constructor
    public AdminUser(String username) {
        super(username);
    }

    // Boolean to check if entered password is a match
    public boolean checkPassword(String input) {
        return ADMIN_PASSWORD.equals(input);
    }

    // Admin is allowed to create, update and delete: these return true.
    @Override
    public boolean canCreate() {
        return true;
    }
    @Override
    public boolean canUpdate() {
        return true;
    }
    @Override
    public boolean canDelete() {
        return true;
    }
}
