package repository.paging.impl;

import model.Entity;
import repository.paging.Page;
import repository.paging.Pageable;
import repository.paging.PagingAndSortingRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PageImpl<ID, T extends Entity<ID>> implements Page<T> {

    private Pageable pageable;
    private PagingAndSortingRepository<ID, T> pagingAndSortingRepository;


    public PageImpl(Pageable pageable, PagingAndSortingRepository<ID, T> pagingAndSortingRepository) {

        this.pageable = pageable;
        this.pagingAndSortingRepository = pagingAndSortingRepository;
    }

    @Override
    public Pageable getPageable() {

        return this.pageable;
    }

    @Override
    public Pageable nextPageable() {

        return new PageableImpl(this.pageable.getPageNumber() + 1, this.pageable.getPageSize());

    }

    @Override
    public Stream<T> getContent() {

        List<T> list = StreamSupport.stream(this.pagingAndSortingRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        int firstEntity = this.pageable.getPageNumber() * this.pageable.getPageSize() - this.pageable.getPageSize();
        int lastEntity = firstEntity + this.pageable.getPageSize();

        lastEntity = Math.min(lastEntity, list.size());

        return IntStream.range(firstEntity, lastEntity)
                .mapToObj(list::get);
    }
}
