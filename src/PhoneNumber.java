public class PhoneNumber {
    private String number;
    private String type; // t.ex. "mobile", "work"

    public PhoneNumber(String number, String type) {
        this.number = number;
        this.type = normalizeType(type);
    }

    private String normalizeType(String type) {
        if (type == null) return "";
        String t = type.trim().toLowerCase();
        if (t.equals("mobil")) t = "mobile";
        if (t.equals("jobb")) t = "work";
        if (t.equals("mobile") || t.equals("work")) return t;
        return "";
        }

    public boolean isMobile() {
        return "mobile".equals(type);
    }
    public boolean isWork() {
        return "work".equals(type);
    }

    // Getters and setters
    public String getNumber() {
        return number;
    }

    // Method to check if phone number matches a search term
    public boolean matches(String term) {
        String lowerTerm = term.toLowerCase();
        return number.toLowerCase().contains(lowerTerm) ||
               type.toLowerCase().contains(lowerTerm);
    }


    @Override
    public String toString() {
        String displayType = switch (type) {
            case "mobile" -> "Mobile";
            case "work" -> "Work";
            default -> type;
        };
        return displayType + ": " + (number == null ? " " : number);
    }
}
