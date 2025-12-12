import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Contact {
    // TODO: fields: firstName, lastName, age, Address, List<PhoneNumber>
    private String id;
    private String name;
    private int age;
    private String address;
    private List<String> phoneNumbers;

    public Contact(String name, int age, String address, List<String> phoneNumbers){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumbers = new ArrayList<phoneNumbers>;
    }

    public String getId(){
        return id;
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
    public List<String> getPhoneNumbers(){
        return phoneNumbers;
    }
    public void setPhoneNumbers(List <String> phoneNumbers){
        this.phoneNumbers = phoneNumbers;
    }
    public void addPhoneNumber(String phoneNumber){
        this.phoneNumbers.add(phoneNumber);
    }

    @Override
    public String toString() {
        return "Contact{id='" + id + "', name='" + name +
                "', age=" + age + ", address='" + address +
                "', phoneNumbers=" + phoneNumbers + "}";
    }

}
