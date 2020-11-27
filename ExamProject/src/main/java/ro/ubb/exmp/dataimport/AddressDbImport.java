package ro.ubb.exmp.dataimport;

import ro.ubb.exmp.domain.Address;
import ro.ubb.exmp.domain.Person;
import ro.ubb.exmp.repository.ConfigXmlRepository;
import ro.ubb.exmp.repository.PersonXmlRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AddressDbImport {

    ConfigXmlRepository configXmlRepository;
    PersonXmlRepository personXmlRepository;

    private final String URL;
    private final String USER;
    private final String PASS;

    public AddressDbImport() {

        this.configXmlRepository = ConfigXmlRepository.getInstance("data/config.xml");
        this.personXmlRepository = new PersonXmlRepository("data/persons.xml");

        this.URL = this.configXmlRepository.getUrl();
        this.USER = this.configXmlRepository.getUser();
        this.PASS = this.configXmlRepository.getPassword();
    }

    public void saveAddress() {

        String sql = "insert into address(street, phone, type, person_id) values(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            List<Person> persons = this.personXmlRepository.loadPersonsFromXml();

            persons.forEach(person -> {

                Optional<Person> optionalPerson = findOne(person.getSsn());
                Person dbPerson = optionalPerson.orElseThrow();

                List<Address> personAddresses = person.getAddresses();

                personAddresses.forEach(address -> {

                    try {

                        ps.setString(1, address.getStreet());
                        ps.setString(2, address.getPhone());
                        ps.setString(3, address.getType().getAddressType().toLowerCase());
                        ps.setLong(4, dbPerson.getId());

                        ps.executeUpdate();

                    } catch (SQLException throwables) {

                        throwables.printStackTrace();
                    }
                });
            });

        } catch (SQLException sqe) {

            sqe.printStackTrace();
        }
    }

    public Optional<Person> findOne(String ssn) {

        String sql = "select * from person where ssn = ?";

        ResultSet resultSet = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ssn);

            resultSet = ps.executeQuery();
            while (resultSet.next()) {

                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");

                Person person = new Person(id, ssn, name);
                return Optional.of(person);
            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        } finally {

            if (resultSet != null) {

                try {
                    resultSet.close();
                } catch (SQLException sqe) {
                    sqe.printStackTrace();
                }
            }
        }

        return Optional.empty();
    }
}
