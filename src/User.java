public abstract class User {

    protected String username;

    public User(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public abstract boolean canCreate();
    public abstract boolean canUpdate();
    public abstract boolean canDelete();

    public boolean canSearch(){
        return true;
    }

}
