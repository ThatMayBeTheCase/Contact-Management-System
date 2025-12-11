import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactManager {
    private final List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Contact findFirstByName(String name) {
        if (name == null) return null;
        String needle = name.trim().toLowerCase(Locale.ROOT);
        for (Contact c : contacts) {
            if (c.getName() != null && c.getName().trim().toLowerCase(Locale.ROOT).equals(needle)) {
                return c;
            }
        }
        return null;
    }

    public List<Contact> search(String term) {
        List<Contact> result = new ArrayList<>();
        if (term == null || term.isBlank()) return result;
        String needle = term.trim().toLowerCase(Locale.ROOT);
        for (Contact c : contacts) {
            if (matches(c, needle)) {
                result.add(c);
            }
        }
        return result;
    }

    private boolean matches(Contact c, String needle) {
        return (c.getName() != null && c.getName().toLowerCase(Locale.ROOT).contains(needle))
                || (c.getAddress() != null && c.getAddress().toLowerCase(Locale.ROOT).contains(needle))
                || (c.getPhone() != null && c.getPhone().toLowerCase(Locale.ROOT).contains(needle));
    }

    public void updateContact(Contact contact, String newName, int newAge, String newAddress, String newPhone) {
        contact.setName(newName);
        contact.setAge(newAge);
        contact.setAddress(newAddress);
        contact.setPhone(newPhone);
    }

    public void deleteContact(Contact contact) {
        contacts.remove(contact);
    }
}
