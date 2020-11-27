package ro.ubb.exmp.dataimport;

import ro.ubb.exmp.domain.Person;
import ro.ubb.exmp.repository.ConfigXmlRepository;
import ro.ubb.exmp.repository.PersonXmlRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDbImport {

    ConfigXmlRepository configXmlRepository;
    PersonXmlRepository personXmlRepository;

    private final String URL;
    private final String USER;
    private final String PASS;

    public PersonDbImport() {

        this.configXmlRepository = ConfigXmlRepository.getInstance("data/config.xml");
        this.personXmlRepository = new PersonXmlRepository("data/persons.xml");

        this.URL = this.configXmlRepository.getUrl();
        this.USER = this.configXmlRepository.getUser();
        this.PASS = this.configXmlRepository.getPassword();
    }

    public void save() {

        String sql = "insert into person(ssn, name) values (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            List<Person> persons = this.personXmlRepository.loadPersonsFromXml();

            persons.forEach(person -> {

                try {
                    ps.setString(1, person.getSsn());
                    ps.setString(2, person.getName());

                    ps.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

        } catch (SQLException sqe) {

            sqe.printStackTrace();
        }
    }

    public Iterable<Person> findAll() {

        List<Person> persons = new ArrayList<>();
        String sql = "select * from person";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {

                    Long id = resultSet.getLong("id");
                    String ssn = resultSet.getString("ssn");
                    String name = resultSet.getString("name");

                    Person person = new Person(id, ssn, name);
                    persons.add(person);
                }

            } catch (SQLException ex) {

                ex.printStackTrace();
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return new ArrayList<>(persons);
    }
}
