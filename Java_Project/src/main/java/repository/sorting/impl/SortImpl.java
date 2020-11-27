package repository.sorting.impl;

import model.Entity;
import repository.paging.PagingAndSortingRepository;
import repository.sorting.DynamicComparator;
import repository.sorting.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class SortImpl<ID, T extends Entity<ID>> implements Sort<T> {

    private PagingAndSortingRepository<ID, T> pagingAndSortingRepository;
    private String property;

    private Iterable<T> entities;

    public SortImpl(PagingAndSortingRepository<ID, T> pagingAndSortingRepository, String property) {

        this.pagingAndSortingRepository = pagingAndSortingRepository;
        this.entities = this.pagingAndSortingRepository.findAll();

        this.property = property;
    }

    @Override
    public Iterable<T> getEntities() {

        return entities;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public void setEntities(Iterable<T> entities) {
        this.entities = entities;
    }

    @Override
    public Sort<T> and(Sort<T> sort) {

        List<T> entitiesList = StreamSupport.stream(sort.getEntities().spliterator(), false)
                .sorted(new DynamicComparator(property).reversed().thenComparing(new DynamicComparator(property)))
                .collect(Collectors.toList());

        sort.setEntities(entitiesList);

        return sort;
    }

    @Override
    public Sort<T> ascending() {

        List<T> entitiesList = StreamSupport.stream(entities.spliterator(), false)
                .sorted(new DynamicComparator(property))
                .collect(Collectors.toList());

        SortImpl<ID, T> sort = new SortImpl<>(pagingAndSortingRepository, property);
        sort.entities = entitiesList;

        return sort;

    }

    @Override
    public Sort<T> descending() {

        List<T> entitiesList = StreamSupport.stream(entities.spliterator(), false)
                .sorted(new DynamicComparator(property).reversed())
                .collect(Collectors.toList());

        SortImpl<ID, T> sort = new SortImpl<>(pagingAndSortingRepository, property);
        sort.entities = entitiesList;

        return sort;

    }
}
