public class Contact {
    // TODO: fields: firstName, lastName, age, Address, List<PhoneNumber>

    private String name;
    private int age;
    private String address;
    private String phone;

    public Contact(String name, int age, String address, String phone){
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
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
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{name='" + name + "', age=" + age +
                ", address='" + address + "', phone='" + phone + "'}";
    }

}
