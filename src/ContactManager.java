import java.util.List;

//ContactManager now uses ContactRepository for data persistence.

public class ContactManager {
    private final ContactRepository repository;

    //Constructor - accepts a repository for dependency injection

    public ContactManager(ContactRepository repository) {
        this.repository = repository;
    }

    public void addContact(Contact contact) {
        repository.addContact(contact);
    }

    public List<Contact> getContacts() {
        return repository.getContacts();
    }

    public Contact findFirstByName(String name) {
        return repository.findFirstByName(name);
    }

    public List<Contact> search(String term) {
        return repository.search(term);
    }

    public void updateContact(Contact contact, String newName, int newAge, String newAddress) {
        repository.updateContact(contact, newName, newAge, newAddress);
    }

    public void deleteContact(Contact contact) {
        repository.deleteContact(contact);
    }
}
