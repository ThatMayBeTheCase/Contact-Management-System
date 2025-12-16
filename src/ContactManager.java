import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private final List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
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
