// Imports for file handling
import java.io.*;
// Imports for data structures
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

     //File-based implementation of ContactRepository interface.
    //Stores contacts in a text file with persistence.
    //Adapted to work with existing Contact class using simple String fields.

public class FileContactRepository implements ContactRepository {
    // File path where contacts are stored
    private static final String FILE_PATH = "contacts.txt";
    // In-memory cache of contacts for faster access
    private final List<Contact> contacts;

    //Constructor - loads contacts from file into memory

    public FileContactRepository() {
        this.contacts = new ArrayList<>();// Initialize empty contact list
        loadFromFile();  // Load saved contacts at startup
    }

    // Loads all contacts from the file into memory
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        // If file doesn't exist, start with empty list
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line from file
            while ((line = reader.readLine()) != null) {
                Contact contact = parseContact(line);
                if (contact != null) {
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {// Handle file read errors
            System.err.println("Error loading contacts from file: " + e.getMessage());// Print error message
        }
    }
    // Saves all contacts from memory to the file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write each contact to file
            for (Contact contact : contacts) {
                writer.write(contactToString(contact));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving contacts to file: " + e.getMessage());
        }
    }

     //Converts a contact to a string format for file storage
    // Format: name|age|address|phone
    private String contactToString(Contact contact) {
        return contact.getName() + "|" +
               contact.getAge() + "|" +
               contact.getAddress() + "|" +
               contact.getPhone();
    }

      // Parses a string line from file into a Contact object
    private Contact parseContact(String line) {
        try {
            String[] parts = line.split("\\|");// Split string by pipe character: "John|30|Main St|123"
            if (parts.length == 4) {  // Validate that we have exactly 4 fields
                String name = parts[0];// First part: name
                int age = Integer.parseInt(parts[1]);// Second part: age (convert to int)
                String address = parts[2];// Third part: address
                String phone = parts[3];// Fourth part: phone
                return new Contact(name, age, address, phone);
            }
        } catch (Exception e) {
            System.err.println("Error parsing contact line: " + line);
        }
        return null;
    }

    @Override
    public void addContact(Contact contact) {
        // Add contact to memory
        contacts.add(contact);
        // Save directly to file
        saveToFile();
    }

    @Override
    public List<Contact> getContacts() {
        // Return copy of all contacts
        return new ArrayList<>(contacts);
    }
        // JUST FOR TESTING PURPOSES (Use it if needed for STORY 10,11,12,13)
    @Override
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

    @Override
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

    // Helper method to check if contact matches search term

    private boolean matches(Contact c, String needle) {
        return (c.getName() != null && c.getName().toLowerCase(Locale.ROOT).contains(needle))
                || (c.getAddress() != null && c.getAddress().toLowerCase(Locale.ROOT).contains(needle))
                || (c.getPhone() != null && c.getPhone().toLowerCase(Locale.ROOT).contains(needle));
    }

    @Override
    public void updateContact(Contact contact, String newName, int newAge, String newAddress, String newPhone) {
        // Update contact fields
        contact.setName(newName);
        contact.setAge(newAge);
        contact.setAddress(newAddress);
        contact.setPhone(newPhone);
        // Save changes to file
        saveToFile();
    }

    @Override
    public void deleteContact(Contact contact) {
        // Remove contact from memory
        contacts.remove(contact);
        // Save changes to file
        saveToFile();
    }
}

