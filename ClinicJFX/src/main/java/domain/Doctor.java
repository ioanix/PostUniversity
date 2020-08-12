package domain;

import java.util.Objects;

public class Doctor extends Entity {

    private String firstName;
    private String lastName;
    private int age;

    public Doctor() {

    }

    public Doctor(String firstName, String lastName, int age) {

        //this.id = new SimpleIntegerProperty(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {

        return id + ". " + "Dr." + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        if (age != doctor.age) return false;
        if (firstName != null ? !firstName.equals(doctor.firstName) : doctor.firstName != null) return false;
        return lastName != null ? lastName.equals(doctor.lastName) : doctor.lastName == null;

    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, age);
    }
}
