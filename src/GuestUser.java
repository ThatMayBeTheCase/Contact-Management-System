public class GuestUser extends User {

    // Constructor
    public GuestUser(String username) {
        super(username);
    }

    // Guest is not allowed to create, update and delete: these return false.
    @Override
    public boolean canCreate() {
        return false;
    }
    @Override
    public boolean canUpdate() {
        return false;
    }
    @Override
    public boolean canDelete() {
        return false;
    }
}
