package repository.paging;

import model.Entity;
import repository.IRepository;
import repository.sorting.Sort;

public interface PagingAndSortingRepository<ID, T extends Entity<ID>> extends IRepository<ID, T> {

    Page<T> findAll(Pageable pageable);

    Iterable<T> sort(Sort<T> sort);
}
