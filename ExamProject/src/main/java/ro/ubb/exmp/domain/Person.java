package ro.ubb.exmp.domain;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private Long id;
    private String ssn;
    private String name;
    private List<Address> addresses;

    public Person() {
    }

    public Person(String ssn, String name) {
        this.ssn = ssn;
        this.name = name;
    }

    public Person(Long id, String ssn, String name) {
        this.id = id;
        this.ssn = ssn;
        this.name = name;
    }

    public Person(Long id, String ssn, String name, List<Address> addresses) {
        this.id = id;
        this.ssn = ssn;
        this.name = name;
        this.addresses = addresses;
    }

    public Person(String ssn) {

        this.ssn = ssn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", ssn='" + ssn + '\'' +
                ", name='" + name + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
