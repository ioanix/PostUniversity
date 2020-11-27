package service;

import model.Bike;
import model.Customer;
import model.CustomerPurchasesViewModel;
import model.Sale;
import model.validators.BikeShopException;
import model.validators.Validator;
import repository.IRepository;
import repository.paging.Page;
import repository.paging.Pageable;
import repository.paging.PagingAndSortingRepository;
import repository.paging.impl.PageableImpl;
import repository.sorting.Sort;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SaleService {

    private PagingAndSortingRepository<Long, Sale> saleRepository;
    private PagingAndSortingRepository<Long, Bike> bikeRepository;
    private PagingAndSortingRepository<Long, Customer> customerRepository;

    private Validator<Sale> saleValidator;

    private int pageSize;
    private int pageNumber = 0;

    public SaleService(PagingAndSortingRepository<Long, Sale> saleRepository, PagingAndSortingRepository<Long, Bike> bikeRepository, PagingAndSortingRepository<Long, Customer> customerRepository, Validator<Sale> saleValidator) {

        this.saleRepository = saleRepository;
        this.bikeRepository = bikeRepository;
        this.customerRepository = customerRepository;
        this.saleValidator = saleValidator;
    }

    public void addSaleService(Long b_id, Long c_id, LocalDate saleDate) {

        Bike bike = bikeIdCheck(b_id);

        Customer customer = customerIdCheck(c_id);

        Sale sale = new Sale(bike, customer, saleDate);
        this.saleValidator.validate(sale);

        this.saleRepository.save(sale);
    }

    private Customer customerIdCheck(Long c_id) {

        return this.customerRepository.findOne(c_id).orElseThrow(() ->
                new IllegalArgumentException("The customer id does not exist"));
    }

    private Bike bikeIdCheck(Long b_id) {

        return this.bikeRepository.findOne(b_id).orElseThrow(() ->
                new IllegalArgumentException("The bike id does not exist"));
    }

    private Sale saleIdCheck(Long id) {

        return this.saleRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("The sale id does not exist"));
    }

    public Optional<Sale> findOneSale(Long id) {

        Sale sale = saleIdCheck(id);

        return this.saleRepository.findOne(sale.getId());
    }

    public Iterable<Sale> getAll() {

        Iterable<Sale> data = this.saleRepository.findAll();

        this.saleValidator.validateList(data);

        return data;
    }

    public Optional<Sale> deleteSaleService(Long id) {

        Sale sale = saleIdCheck(id);

        return this.saleRepository.delete(sale.getId());
    }

    public Optional<Sale> updateSaleService(Long s_id, Long b_id, Long c_id, LocalDate saleDate) {

        Bike bike = bikeIdCheck(b_id);

        Customer customer = customerIdCheck(c_id);

        Sale newSale = new Sale(bike, customer, saleDate);
        this.saleValidator.validate(newSale);

        Sale existingSale = saleIdCheck(s_id);

        return newSale.equals(existingSale) ? Optional.empty() : this.saleRepository.update(newSale);

    }

    public List<CustomerPurchasesViewModel> showCustomersOrderedByNumberOfPurchases() {

        Map<Customer, Integer> customerPurchases = new HashMap<>();  // map where a customer is the key and the number of purchases is the value

        StreamSupport.stream(this.saleRepository.findAll().spliterator(), false)
                .forEach(sale ->
                        customerPurchases.put(sale.getCustomer(),
                                customerPurchases.getOrDefault(sale.getCustomer(), 0) + 1));

        return customerPurchases.keySet().stream()
                .map(customer -> new CustomerPurchasesViewModel(customer.getId(),
                                                                customer.getFirstName(),
                                                                customer.getLastName(),
                                                                customerPurchases.get(customer)))
                .sorted(Comparator.comparing(CustomerPurchasesViewModel::getNumberOfPurchases).reversed())
                .collect(Collectors.toList());

    }

    public List<Sale> showSalesOrderedBySaleDate() {

         return StreamSupport.stream(this.saleRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Sale::getSaleDate).reversed())
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

    public List<Sale> getNextSales() {

        Page<Sale> currentPage = this.saleRepository.findAll(new PageableImpl(this.pageNumber, this.pageSize));

        Pageable pageable = currentPage.nextPageable();

        Page<Sale> nextPage = this.saleRepository.findAll(new PageableImpl(pageable.getPageNumber(), pageable.getPageSize()));

        this.pageNumber++;

        return nextPage.getContent()
                .collect(Collectors.toList());

    }

    public Iterable<Sale> getSalesSorted(String property) {

        return this.saleRepository.sort(Sort.saleBy(property));
    }

    public Iterable<Sale> getSalesSortedInDescendingOrder(String property) {

        return this.saleRepository.sort(Sort.saleBy(property).descending());
    }

    public Iterable<Sale> getSalesSortedByFirstFieldDescendingAndBySecondFieldAscending(String property, String anotherProperty) {

        return this.saleRepository.sort(Sort.saleBy(property).descending()
                .and(Sort.saleBy(anotherProperty).ascending()));
    }
}
