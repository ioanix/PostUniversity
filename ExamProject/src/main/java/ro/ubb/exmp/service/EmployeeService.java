package ro.ubb.exmp.service;

import ro.ubb.exmp.dataimport.EmployeeDbImport;

public class EmployeeService {

    private EmployeeDbImport employeeDbImport;

    public EmployeeService(EmployeeDbImport employeeDbImport) {

        this.employeeDbImport = employeeDbImport;
    }

    public void addEmployeeService() {

        this.employeeDbImport.saveEmployee();
    }
}
