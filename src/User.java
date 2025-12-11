public abstract class User {

    // NOTE: Is username actually needed?
    // We might not use it since we only have guest/admin
    protected String username;

    // Constructor
    public User(String username){
        this.username = username;
    }

    // Getter
    public String getUsername(){
        return username;
    }

    // Methods to define what a user is allowed to do.
    public abstract boolean canCreate();
    public abstract boolean canUpdate();
    public abstract boolean canDelete();

    // As all users are allowed to search this is default implementation.
    public boolean canSearch(){
        return true;
    }

}
