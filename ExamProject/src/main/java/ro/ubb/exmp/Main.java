package ro.ubb.exmp;

import ro.ubb.exmp.dataimport.AddressDbImport;
import ro.ubb.exmp.dataimport.EmployeeDbImport;
import ro.ubb.exmp.dataimport.PersonDbImport;
import ro.ubb.exmp.repository.ConfigXmlRepository;
import ro.ubb.exmp.repository.EmployeeXmlRepository;
import ro.ubb.exmp.repository.PersonRepository;
import ro.ubb.exmp.repository.PersonXmlRepository;
import ro.ubb.exmp.service.ConfigurationService;
import ro.ubb.exmp.service.EmployeeService;
import ro.ubb.exmp.service.PersonService;
import ro.ubb.exmp.ui.Console;

public class Main {
    public static void main(String[] args) {

        ConfigXmlRepository configXmlRepository = ConfigXmlRepository.getInstance("data/config.xml");
//        PersonXmlRepository personXmlRepository = new PersonXmlRepository("data/persons.xml");
//        EmployeeXmlRepository employeeXmlRepository = new EmployeeXmlRepository("data/employees.xml");

        PersonDbImport personDbImport = new PersonDbImport();
        AddressDbImport addressDbImport = new AddressDbImport();
        EmployeeDbImport employeeDbImport = new EmployeeDbImport();

        PersonRepository personRepository = new PersonRepository(configXmlRepository);

        ConfigurationService configurationService = new ConfigurationService(configXmlRepository);
        PersonService personService = new PersonService(personDbImport, addressDbImport, personRepository);
        EmployeeService employeeService = new EmployeeService(employeeDbImport);

        Console console = new Console(configurationService, personService, employeeService);

        console.runUserInterface();
    }
}
