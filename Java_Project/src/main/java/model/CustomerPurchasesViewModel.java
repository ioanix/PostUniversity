package model;

import java.util.Objects;

public class CustomerPurchasesViewModel {

    private Long customerId;
    private String firstName;
    private String lastName;
    private int numberOfPurchases;

    public CustomerPurchasesViewModel(Long customerId, String firstName, String lastName, int numberOfPurchases) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfPurchases = numberOfPurchases;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public void setNumberOfPurchases(int numberOfPurchases) {
        this.numberOfPurchases = numberOfPurchases;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerPurchasesViewModel that = (CustomerPurchasesViewModel) o;
        return numberOfPurchases == that.numberOfPurchases &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, numberOfPurchases);
    }

    @Override
    public String toString() {
        return "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", numberOfPurchases=" + numberOfPurchases +
                '}';
    }
}
