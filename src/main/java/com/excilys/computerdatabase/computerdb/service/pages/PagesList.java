package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.List;

public abstract class PagesList {

    protected long rowByPages = 30;
    protected long pageNumber = 1;
    protected long totalNumberOfpages;
    protected long totalRow;

    public abstract List<Pageable> getList();

    public abstract List<Pageable> getListFilterByName(String name);

    public void nextPage() {
        pageNumber = Math.min(totalNumberOfpages, pageNumber + 1);
    }

    public void previousPage() {
        pageNumber = Math.max(1, pageNumber - 1);
    }

    public void firstPage() {
        pageNumber = 1;
    }

    public void lastPage() {
        pageNumber = totalNumberOfpages;
    }

    public Page getPage() {
        List<Pageable> computers = getList();
        return new Page(computers);
    }

    public long getPageIndex() {
        return pageNumber;
    }

    public void setPageIndex(long pageNumber) {
        this.pageNumber = Math.max(Math.min(totalNumberOfpages, pageNumber), 1);
    }

    public long getTotalPageNumber() {
        return totalNumberOfpages;
    }

    public void setRowByPages(long limit) {
        this.rowByPages = limit;
        this.totalNumberOfpages = (long) Math.ceil(totalRow / (double) rowByPages);
    }

    public long getTotalRow() {
        return totalRow;
    }

}
