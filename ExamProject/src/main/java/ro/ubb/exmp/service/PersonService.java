package ro.ubb.exmp.service;

import ro.ubb.exmp.dataimport.AddressDbImport;
import ro.ubb.exmp.dataimport.PersonDbImport;
import ro.ubb.exmp.domain.Person;
import ro.ubb.exmp.repository.PersonRepository;

import java.util.List;

public class PersonService {

    private PersonDbImport personDbImport;
    private AddressDbImport addressDbImport;
    private PersonRepository personRepository;

    public PersonService(PersonDbImport personDbImport, AddressDbImport addressDbImport, PersonRepository personRepository) {

        this.personDbImport = personDbImport;
        this.addressDbImport = addressDbImport;
        this.personRepository = personRepository;
    }

//    public Iterable<Person> getAll() {
//
//        return this.personXmlRepository.loadPersonsFromXml();
//    }

    public void addPersonService() {

        this.personDbImport.save();
    }

    public void addAddressService() {

        this.addressDbImport.saveAddress();
    }

    public List<Person> getAllPersons() {

        return this.personRepository.findAll();
    }

}
