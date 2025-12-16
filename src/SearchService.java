import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchService {
    private final ContactManager manager;

    public SearchService(ContactManager manager) {
        this.manager = manager;
    }

    public Contact findFirstByLastName(String LastName) {
        String needle = norm (lastName);
        for (Contact c : manager.getContacts()) {
            if (c.getName() != null && c.getName().toLowerCase(Locale.ROOT).contains(needle)) {
                return c;
            }
        }
        return null;
    }

    public List<Contact> findByFirstName(String firstName) {}

    public List<Contact> findByStreet(String street) {}

    public List<Contact> freeSearch(String term) {}

    // Free search helper
    private boolean matches (Contact c, String needle) {}

}
