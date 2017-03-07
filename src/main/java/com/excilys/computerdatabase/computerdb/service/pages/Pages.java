package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.List;

public abstract class Pages {

    protected long rowByPages = 30;
    protected long pageNumber = 1;
    protected long totalRow;
    protected String filter;

    /**
     * Get item List of current page.
     *
     * @return Pageable List.
     */
    public abstract List<Pageable> getItems();

    /**
     * Get the current page.
     *
     * @return The current page
     */
    public Page getCurrentPage() {
        List<Pageable> pageables = getItems();
        return new Page(pageables);
    }

    public void resetFilter() {
        filter = "";
    }

    public void setFilter(String pattern) {
        this.filter = pattern;
    }

    public String getFilter() {
        return filter;
    }

    /**
     * Switch to next Page.
     */
    public void nextPage() {
        pageNumber = Math.min(getTotalNumberOfPage(), pageNumber + 1);
    }

    /**
     * Switch to previous Page.
     */
    public void previousPage() {
        pageNumber = Math.max(1, pageNumber - 1);
    }

    /**
     * Switch to first Page.
     */
    public void firstPage() {
        pageNumber = 1;
    }

    /**
     * Switch to Last Page.
     */
    public void lastPage() {
        pageNumber = getTotalNumberOfPage();
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
     * @param pageNumber .
     */
    public void setPageIndex(long pageNumber) {
        this.pageNumber = Math.max(Math.min(getTotalNumberOfPage(), pageNumber), 1);
    }

    /**
     * Set the limit of item by page and set the number of page.
     *
     * @param limit of row on each page
     */
    public void setRowByPages(long limit) {
        this.rowByPages = limit;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public long getTotalNumberOfPage() {
        return (long) Math.ceil(totalRow / (double) rowByPages);
    }

    /**
     * Set the total number of item to display.
     *
     * @param nbRow .
     */
    public void setTotalNumberOfRow(long nbRow) {
        this.totalRow = nbRow;
    }

}
