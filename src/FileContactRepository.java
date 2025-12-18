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
    // Format: Name: ***|Age: ***|Address: ***|Mobile: ***|Work: ***
    private String contactToString(Contact contact) {
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(contact.getName())
              .append("|Age: ").append(contact.getAge())
              .append("|Address: ").append(contact.getAddress());

        // Add phone numbers
        String mobileNumber = "";
        String workNumber = "";
            // For each phone number, check its type and assign accordingly
        for (PhoneNumber phone : contact.getPhoneNumbers()) {
            if (phone.getType().equalsIgnoreCase("mobile") || phone.getType().equalsIgnoreCase("mobil")) {
                mobileNumber = phone.getNumber();
            } else if (phone.getType().equalsIgnoreCase("work") || phone.getType().equalsIgnoreCase("jobb")) {
                workNumber = phone.getNumber();
            }
        }

        result.append("|Mobile: ").append(mobileNumber)
              .append("|Work: ").append(workNumber);

        return result.toString();
    }

      // Parses a string line from file into a Contact object
    private Contact parseContact(String line) {
        try {
            String[] parts = line.split("\\|");

            // New format: Name: |Age: |Address: |Mobile: |Work:
            if (parts.length >= 3 && parts[0].startsWith("Name: ")) {
                String name = parts[0].substring(6); // Remove "Name: " prefix
                int age = Integer.parseInt(parts[1].substring(5).trim()); // Remove "Age: " prefix
                String address = parts[2].substring(9).trim(); // Remove "Address: " prefix

                Contact contact = new Contact(name, age, address);

                // Parse Mobile number if it exists
                if (parts.length >= 4 && parts[3].startsWith("Mobile: ")) {
                    String mobile = parts[3].substring(8).trim();
                    if (!mobile.isBlank()) {
                        contact.addPhoneNumber(new PhoneNumber(mobile, "mobile"));
                    }
                }

                // Parse Work number if it exists
                if (parts.length >= 5 && parts[4].startsWith("Work: ")) {
                    String work = parts[4].substring(6).trim();
                    if (!work.isBlank()) {
                        contact.addPhoneNumber(new PhoneNumber(work, "work"));
                    }
                }

                return contact;
            }
            // Old format 1: name|age|address|phone1:type1;phone2:type2
            else if (parts.length >= 3) {
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                String address = parts[2];

                Contact contact = new Contact(name, age, address);

                // Parse phone numbers if they exist
                if (parts.length >= 4 && !parts[3].isBlank()) {
                    String phonePart = parts[3];
                    String[] phones = phonePart.split(";");
                    for (String phone : phones) {
                        if (!phone.isBlank()) {
                            String[] phoneParts = phone.split(":");
                            if (phoneParts.length == 2) {
                                contact.addPhoneNumber(new PhoneNumber(phoneParts[0], phoneParts[1]));
                            } else {
                                // Legacy format: just a number without type
                                contact.addPhoneNumber(new PhoneNumber(phone, "mobile"));
                            }
                        }
                    }
                }

                return contact;
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
    public void updateContact(Contact contact, String newName, int newAge, String newAddress) {
        // Update contact fields
        contact.setName(newName);
        contact.setAge(newAge);
        contact.setAddress(newAddress);
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

