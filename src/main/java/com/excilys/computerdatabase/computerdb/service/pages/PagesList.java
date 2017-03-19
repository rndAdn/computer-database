package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.List;

public abstract class PagesList {

    protected long rowByPages = 30;
    protected long pageNumber = 1;
    protected long totalNumberOfpages;
    protected long totalRow;


    /**
     * Get item List of current page.
     *
     * @return Pageable List.
     */
    public abstract List<Pageable> getList();

    /**
     * Get item List of current page filter by Name.
     *
     * @param name
     *            .
     * @return Pageable List.
     */
    public abstract List<Pageable> getListFilterByName(String name);

    /**
     * Switch to next Page.
     *
     */
    public void nextPage() {
        pageNumber = Math.min(totalNumberOfpages, pageNumber + 1);
    }

    /**
     * Switch to previous Page.
     *
     */
    public void previousPage() {
        pageNumber = Math.max(1, pageNumber - 1);
    }

    /**
     * Switch to first Page.
     *
     */
    public void firstPage() {
        pageNumber = 1;
    }

    /**
     * Switch to Last Page.
     *
     */
    public void lastPage() {
        pageNumber = totalNumberOfpages;
    }

    /**
     * Get the current page.
     *
     * @return The current page
     */
    public Page getPage() {
        List<Pageable> computers = getList();
        return new Page(computers);
    }

    /**
     * Get the current page index.
     *
     * @return page Index
     */
    public long getPageIndex() {
        return pageNumber;
    }

    /**
     * Set the current page index bound between 1 and totalNumberOfpages.
     *
     * @param pageNumber
     *            .
     */
    public void setPageIndex(long pageNumber) {
        this.pageNumber = Math.max(Math.min(totalNumberOfpages, pageNumber), 1);
    }

    public long getTotalPageNumber() {
        return totalNumberOfpages;
    }

    /**
     * Set the limit of item by page and set the number of page.
     *
     * @param limit
     *            of row on each page
     */
    public void setRowByPages(long limit) {
        this.rowByPages = limit;
        this.totalNumberOfpages = (long) Math.ceil(totalRow / (double) rowByPages);
    }

    public long getTotalRow() {
        return totalRow;
    }

    /**
     * Set the total number of item to display.
     *
     * @param nbRow
     *            .
     */
    public void setTotalNumberOfRow(long nbRow) {
        this.totalRow = nbRow;
        this.totalNumberOfpages = (int) Math.ceil(nbRow / (double) rowByPages);
    }

}
