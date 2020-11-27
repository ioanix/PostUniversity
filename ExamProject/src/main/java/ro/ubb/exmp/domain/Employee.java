package ro.ubb.exmp.domain;

import java.util.List;

public class Employee extends Person {

    private int salary;

    public Employee() {

    }

    public Employee(String ssn, int salary) {
        super(ssn);
        this.salary = salary;
    }

    public Employee(Long id, String ssn, String name, int salary) {

        super(id, ssn, name);
        this.salary = salary;
    }

    public Employee(Long id, String ssn, String name, List<Address> addresses, int salary) {

        super(id, ssn, name, addresses);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {

        return super.toString() + ", salary=" + salary;
    }

}
