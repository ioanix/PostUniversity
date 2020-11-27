package service;

import model.Customer;
import model.validators.Validator;
import repository.paging.Page;
import repository.paging.Pageable;
import repository.paging.PagingAndSortingRepository;
import repository.paging.impl.PageableImpl;
import repository.sorting.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CustomerService {

    private PagingAndSortingRepository<Long, Customer> customerRepository;
    private Validator<Customer> customerValidator;

    private int pageSize;
    private int pageNumber = 0;

    public CustomerService(PagingAndSortingRepository<Long, Customer> customerRepository, Validator<Customer> customerValidator) {

        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    /**
     * Adds a customer to the repository
     *
     * @param firstName the given firstName of the customer
     * @param lastName  the given lastName of the customer
     * @param phone     the given phone of the customer
     * @param city      the given city of the customer
     * @param street    the given street of the customer
     * @param number    the given number of the customer
     */
    public void addCustomerService(String firstName, String lastName, String phone, String city, String street, String number) {

        Customer customer = new Customer(firstName, lastName, phone, city, street, number);

        this.customerValidator.validate(customer);

//        customer.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

        this.customerRepository.save(customer);
    }

    public Optional<Customer> findOneCustomer(Long id) {

        return Optional.of(this.customerRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("The customer id does not exist")));
    }

    /**
     * Gets all the customers from the repository
     *
     * @return the customers
     */
    public Iterable<Customer> getAll() {

        Iterable<Customer> data = this.customerRepository.findAll();

        this.customerValidator.validateList(data);

        return data;
    }

    public Optional<Customer> updateCustomerService(Long id, String firstName, String lastName, String phone, String city, String street, String number) {

        Customer newCustomer = new Customer(id, firstName, lastName, phone, city, street, number);
        this.customerValidator.validate(newCustomer);

        Customer existingCustomer = this.customerRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("The customer id does not exist"));

        return newCustomer.equals(existingCustomer) ? Optional.empty() : this.customerRepository.update(newCustomer);
    }

    /**
     * Gets the customers in a specific city
     *
     * @param city the given city
     * @return the customers
     */
    public List<Customer> showCustomersFromASpecificCity(String city) {

        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(customer -> customer.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());

    }

    /**
     * Deletes a customer from repository
     *
     * @param id
     * @return the deleted customer
     */
    public Optional<Customer> deleteCustomerService(Long id) {

        Customer customer = this.customerRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("The customer id does not exist"));

        return this.customerRepository.delete(customer.getId());
    }

    public List<Customer> showCustomersOrderedByLastName() {

        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Customer::getLastName))
                .collect(Collectors.toList());

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<Customer> getNextCustomers() {

        Page<Customer> currentPage = this.customerRepository.findAll(new PageableImpl(this.pageNumber, this.pageSize));

        Pageable pageable = currentPage.nextPageable();

        Page<Customer> nextPage = this.customerRepository.findAll(new PageableImpl(pageable.getPageNumber(), pageable.getPageSize()));

        this.pageNumber++;

        return nextPage.getContent()
                .collect(Collectors.toList());

    }

    public Iterable<Customer> getCustomersSorted(String property) {

        return this.customerRepository.sort(Sort.customerBy(property));
    }

    public Iterable<Customer> getCustomersSortedInDescendingOrder(String property) {

        return this.customerRepository.sort(Sort.customerBy(property).descending());
    }

    public Iterable<Customer> getCustomersSortedByFirstFieldDescendingAndBySecondFieldAscending(String property, String anotherProperty) {

        return this.customerRepository.sort(Sort.customerBy(property).descending()
                .and(Sort.customerBy(anotherProperty).ascending()));
    }
}
