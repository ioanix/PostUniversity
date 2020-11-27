package repository.paging.impl;

import repository.paging.Pageable;

public class PageableImpl implements Pageable {

    private int pageSize;
    private int pageNumber;

    public PageableImpl(int pageNumber, int pageSize) {

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;

    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }
}
