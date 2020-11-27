package ro.ubb.exmp.ui;

import ro.ubb.exmp.service.ConfigurationService;
import ro.ubb.exmp.service.EmployeeService;
import ro.ubb.exmp.service.PersonService;

import java.util.Scanner;
import java.util.stream.StreamSupport;

public class Console {

    private ConfigurationService configurationService;
    private PersonService personService;
    private EmployeeService employeeService;

    private Scanner scanner = new Scanner(System.in);

    public Console(ConfigurationService configurationService, PersonService personService, EmployeeService employeeService) {

        this.configurationService = configurationService;
        this.personService = personService;
        this.employeeService = employeeService;
    }

    private void showMenu() {

        System.out.println("show-config");
        System.out.println("import-persons");
        System.out.println("import-employees");
        System.out.println("list");
        System.out.println("x - to quit");
        System.out.println("Option: ");
    }

    public void runUserInterface() {

        while(true) {

            showMenu();

            String option = scanner.nextLine();


            switch (option) {

                case "show-config" -> this.handleShowConfig();

                case "import-persons" -> {
                    //this.handleImportPersons();
                    //this.handleImportAddresses();
                }
                
                case "import-employees" -> {
                    //this.handleImportEmployees();
                }

                case "list" -> this.handleShowAllPersons();

                case "x" -> {
                    return;
                }

                default -> System.out.println("Invalid option");
            }
        }
    }

    private void handleShowAllPersons() {

        this.personService.getAllPersons().forEach(System.out::println);
    }

    private void handleImportEmployees() {

        this.employeeService.addEmployeeService();
    }

    private void handleImportAddresses() {

        this.personService.addAddressService();
    }

    private void handleImportPersons() {

        this.personService.addPersonService();
    }

    private void handleShowConfig() {

        System.out.println(this.configurationService.getConfig());
    }
}
