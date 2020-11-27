package service;

import model.Bike;
import model.BikeType;
import model.validators.CategoryValidator;
import model.validators.Validator;
import repository.paging.Page;
import repository.paging.Pageable;
import repository.paging.PagingAndSortingRepository;
import repository.paging.impl.PageableImpl;
import repository.sorting.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BikeService {

    private PagingAndSortingRepository<Long, Bike> bikeRepository;
    private Validator<Bike> bikeValidator;

    private int pageSize;
    private int pageNumber = 0;

    public BikeService(PagingAndSortingRepository<Long, Bike> bikeRepository, Validator<Bike> bikeValidator) {
        this.bikeRepository = bikeRepository;
        this.bikeValidator = bikeValidator;
    }

    /**
     * Adds a bike to the repository
     *
     * @param name     the given name of the bike
     * @param bikeType the given bikeType of the bike
     * @param price    the given price of the bike
     */
    public void addBikeService(String name, BikeType bikeType, double price) {

        Bike bike = new Bike(name, bikeType, price);

        this.bikeValidator.validate(bike);

        //bike.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

        this.bikeRepository.save(bike);
    }

    public Optional<Bike> findOneBike(Long id) {

        return Optional.of(this.bikeRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("The bike_id does not exist")));

    }

    public Optional<Bike> updateBikeService(Long id, String name, BikeType bikeType, double price) {

        Bike newBike = new Bike(id, name, bikeType, price);
        this.bikeValidator.validate(newBike);

        Bike existingBike = this.bikeRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("The bike id does not exist"));

        return  newBike.equals(existingBike) ? Optional.empty() : this.bikeRepository.update(newBike);

    }

    /**
     * Gets all the bikes from the repository
     *
     * @return the bikes
     */
    public Iterable<Bike> getAll() {

        Iterable<Bike> data = this.bikeRepository.findAll();

        this.bikeValidator.validateList(data);

        return data;
    }

    /**
     * Deletes a bike from repository
     *
     * @param id
     * @return the deleted bike
     */
    public Optional<Bike> deleteBikeService(Long id) {

        Bike bike = this.bikeRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("The bike id does not exist"));

        return this.bikeRepository.delete(bike.getId());
    }

    /**
     * Gets the bikes with max price
     *
     * @return the bikes
     */
    public List<Bike> showBikeWithMaxPrice() {

        double max = StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .mapToDouble(Bike::getPrice)
                .max()
                .orElse(-1);

        return StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .filter(bike -> bike.getPrice() == max)
                .collect(Collectors.toList());

    }

    /**
     * Gets the bikes grouped by category
     *
     * @return all the categories with the corresponding bikes
     */
    public Map<BikeType, List<Bike>> showBikesGroupedByCategory() {

        return StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(Bike::getType));
    }

    /**
     * Gets the bikes in a certain category
     *
     * @param category the given category
     * @return the bikes
     */
    public List<Bike> searchBikeByCategory(String category) {

        CategoryValidator.validate(category);
        return StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .filter(bike -> bike.getType().equals(BikeType.valueOf(category.toUpperCase())))
                .collect(Collectors.toList());
    }

    public Iterable<Bike> showBikesOrderedByPrice() {

        return StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparingDouble(Bike::getPrice).reversed())
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


    public List<Bike> getNextBikes() {

        Page<Bike> currentPage = this.bikeRepository.findAll(new PageableImpl(this.pageNumber, this.pageSize));

        Pageable pageable = currentPage.nextPageable();

        Page<Bike> nextPage = this.bikeRepository.findAll(new PageableImpl(pageable.getPageNumber(), pageable.getPageSize()));

        this.pageNumber++;

        return nextPage.getContent()
                .collect(Collectors.toList());

    }

    public Iterable<Bike> getBikesSorted(String property) {

        return this.bikeRepository.sort(Sort.bikeBy(property));
    }

    public Iterable<Bike> getBikesSortedInDescendingOrder(String property) {

        return this.bikeRepository.sort(Sort.bikeBy(property).descending());
    }

    public Iterable<Bike> getBikesSortedByFirstFieldDescendingAndBySecondFieldAscending(String property, String anotherProperty) {

        return this.bikeRepository.sort(Sort.bikeBy(property).descending()
                .and(Sort.bikeBy(anotherProperty).ascending()));
    }
}
