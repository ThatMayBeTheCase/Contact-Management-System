import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchService {
    private final ContactManager manager;

    public SearchService(ContactManager manager) {
        this.manager = manager;
    }

    private String norm(String s) {
        return s == null ? "" : s.trim().toLowerCase(Locale.ROOT);
    }

    public Contact findFirstByName(String name) {
        String needle = norm(name);
        for (Contact c : manager.getContacts()) {
            if (norm(c.getName()).equals(needle))
                return c;
        }
        return null;
    }

    public Contact findFirstByLastName(String lastName) {
        String needle = norm (lastName);
        for (Contact c : manager.getContacts()) {
            if (c.getName() != null && c.getName().toLowerCase(Locale.ROOT).contains(needle)) {
                return c;
            }
        }
        return null;
    }

    public List<Contact> findByFirstName(String firstName) {
        String needle = norm(firstName);
        List<Contact> out = new ArrayList<>();
        for (Contact c : manager.getContacts()) {
            if (norm(c.getName()).contains(needle))out.add(c);
        }
        return out;
    }

    public List<Contact> findByStreet(String street) {
        String needle = norm(street);
        List<Contact> out = new ArrayList<>();
        for (Contact c : manager.getContacts()) {
            if (norm(c.getAddress()).contains(needle))out.add(c);
        }
        return out;
    }

    public List<Contact> freeSearch(String term) {
        String needle = norm(term);
        List<Contact> out = new ArrayList<>();
        for (Contact c : manager.getContacts()) {
            if (matches(c, needle)) out.add(c);
        }
        return out;
    }

    // Free search helper
    private boolean matches (Contact c, String needle) {
        if (norm(c.getName()).contains(needle) || norm(c.getAddress()).contains(needle)) {
            return true;
        }
        // Search through all phone numbers
        for (PhoneNumber phone : c.getPhoneNumbers()) {
            if (phone.matches(needle)) {
                return true;
            }
        }
        return false;
    }

}
