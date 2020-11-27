package ro.ubb.exmp.dataimport;

import ro.ubb.exmp.domain.Employee;
import ro.ubb.exmp.repository.ConfigXmlRepository;
import ro.ubb.exmp.repository.EmployeeXmlRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDbImport {

    ConfigXmlRepository configXmlRepository;
    EmployeeXmlRepository employeeXmlRepository;

    private final String URL;
    private final String USER;
    private final String PASS;

    public EmployeeDbImport() {

        this.configXmlRepository = ConfigXmlRepository.getInstance("data/config.xml");
        this.employeeXmlRepository = new EmployeeXmlRepository("data/employees.xml");

        this.URL = this.configXmlRepository.getUrl();
        this.USER = this.configXmlRepository.getUser();
        this.PASS = this.configXmlRepository.getPassword();
    }

    public void saveEmployee() {

        String sql = "insert into employee(id, salary) values (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            List<Employee> employees = this.employeeXmlRepository.loadEmployeesFromXml();

            employees.forEach(employee -> {

                try {
                    ps.setString(1, employee.getSsn());
                    ps.setInt(2, employee.getSalary());

                    ps.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

        } catch (SQLException sqe) {

            sqe.printStackTrace();
        }
    }
}
