package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.List;

public class Page {

    private List<Pageable> computerList;

    /**
     * Page Constructor.
     *
     * @param list of Pageable
     */
    public Page(List<Pageable> list) {
        this.computerList = list;

    }

    /**
     * Print content of page in console.
     */
    public void printContent() {
        System.out.println("\n----------------");
        for (Pageable p : computerList) {
            System.out.println(p);
        }
        System.out.println("\n");
    }

    /**
     * Get the list of element in the page.
     *
     * @return List Pageable
     */
    public List<Pageable> getList() {
        return computerList;
    }

}
