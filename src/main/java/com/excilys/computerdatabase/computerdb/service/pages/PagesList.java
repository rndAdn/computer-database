package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.List;

public abstract class PagesList {

    protected int rowByPages = 30;
    protected int pageNumber = 1;
    protected int totalNumberOfpages;
    protected long totalRow;

    protected abstract List<Pageable> getList();

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
        pageNumber = Math.max(Math.min(totalNumberOfpages, pageNumber), 1);
    }

    public long getTotalPageNumber() {
        return totalNumberOfpages;
    }

    public void setRowByPages(int limit) {
        this.rowByPages = limit;
        this.totalNumberOfpages = (int) Math.ceil(totalRow / (double) rowByPages);
    }

    public long getTotalRow() {
        return totalRow;
    }

}
