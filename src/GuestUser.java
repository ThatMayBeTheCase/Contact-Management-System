public class GuestUser extends User {

    public GuestUser(String username) {
        super(username);
    }

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
