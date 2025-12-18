import java.util.List;

// Interface that defines the contract for contact management.
//  Adapted to work with existing ContactManager implementation.

public interface ContactRepository {

    // Adds a new contact to the repository
    void addContact(Contact contact);

    // Returns all contacts as a list
    List<Contact> getContacts();


    // Finds first contact by exact name match
    Contact findFirstByName(String name);

    // Searches contacts by term (name, address, phone)
    List<Contact> search(String term);
    // Updates a contact with new values
    void updateContact(Contact contact, String newName, int newAge, String newAddress);

    // Deletes a contact from the repository
    void deleteContact(Contact contact);
}
