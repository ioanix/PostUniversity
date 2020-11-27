package ro.ubb.exmp.repository;

import ro.ubb.exmp.domain.Address;
import ro.ubb.exmp.domain.AddressType;
import ro.ubb.exmp.domain.Employee;
import ro.ubb.exmp.domain.Person;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class PersonRepository {

    ConfigXmlRepository configXmlRepository;

    private final String URL;
    private final String USER;
    private final String PASS;

    public PersonRepository(ConfigXmlRepository configXmlRepository) {
        this.configXmlRepository = configXmlRepository;

        this.URL = this.configXmlRepository.getUrl();
        this.USER = this.configXmlRepository.getUser();
        this.PASS = this.configXmlRepository.getPassword();
    }

    public List<Person> findAll() {

//        List<Person> persons = new ArrayList<>();

        Map<String, Employee> employeeMap = new HashMap<>();

        String sql = "select * from persons";

        Map<String, List<Address>> map = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {

                    Long id = resultSet.getLong("id");
                    String ssn = resultSet.getString("ssn");
                    String name = resultSet.getString("name");

                    String street = resultSet.getString("street");
                    String phone = resultSet.getString("phone");
                    AddressType type = AddressType.valueOf(resultSet.getString("type").toUpperCase());

                    Address address = new Address(type, street, phone);

                    List<Address> addressList = map.computeIfAbsent(ssn, key -> new ArrayList<>());
                    addressList.add(address);

                    int salary = resultSet.getInt("salary");

                    Employee employee = new Employee(id, ssn, name, addressList, salary);
                    employeeMap.putIfAbsent(ssn, employee);


//                    if (map.containsKey(ssn)) {
//
//                        map.get(ssn).add(address);
//
//                    } else {
//
//                        List<Address> addresses = new ArrayList<>();
//                        addresses.add(address);
//
//                        map.put(ssn, addresses);
//
//                        int salary = resultSet.getInt("salary");
//
//                        Employee employee = new Employee(id, ssn, name, addresses, salary);
//                        persons.add(employee);
//                    }
                }

            } catch (SQLException ex) {

                ex.printStackTrace();
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return employeeMap.keySet().stream()
                .map(employeeMap::get)
                .sorted(Comparator.comparing(Person::getSsn))
                .collect(Collectors.toList());
    }
}
