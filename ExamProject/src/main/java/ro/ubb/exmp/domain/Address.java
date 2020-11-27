package ro.ubb.exmp.domain;

public class Address {

    private AddressType type;
    private String street;
    private String phone;

    public Address() {
    }

    public Address(AddressType type, String street, String phone) {
        this.type = type;
        this.street = street;
        this.phone = phone;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Address{" +
                "type=" + type +
                ", street='" + street + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
