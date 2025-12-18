public class PhoneNumber {
    private String number;
    private String type; // t.ex. "mobile", "job"

    public PhoneNumber(String number, String type) {
        this.number = number;
        this.type = type;
    }
    // Getters and setters
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    // Method to check if phone number matches a search term
    public boolean matches(String term) {
        String lowerTerm = term.toLowerCase();
        return number.toLowerCase().contains(lowerTerm) ||
               type.toLowerCase().contains(lowerTerm);
    }

    @Override
    public String toString() {
        return type + ": " + number;
    }
}
