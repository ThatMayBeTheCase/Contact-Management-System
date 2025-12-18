import java.util.ArrayList;
import java.util.List;

public class Contact {

    private String name;
    private int age;
    private String address;
    private List<PhoneNumber> phoneNumbers;

    public Contact(String name, int age, String address){
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumbers = new ArrayList<>();
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String adress){
        this.address = adress;
    }

    public List<PhoneNumber> getPhoneNumbers(){
        return phoneNumbers;
    }

    public void addPhoneNumber(PhoneNumber phoneNumber){
        this.phoneNumbers.add(phoneNumber);
    }

    public void removePhoneNumber(PhoneNumber phoneNumber){
        this.phoneNumbers.remove(phoneNumber);
    }

    public void clearPhoneNumbers(){
        this.phoneNumbers.clear();
    }

    // Legacy method for backward compatibility
    public String getPhone(){
        if (phoneNumbers.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < phoneNumbers.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(phoneNumbers.get(i).toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Contact{name='" + name + "', age=" + age +
                ", address='" + address + "', phones=" + phoneNumbers + "}";
    }

}
