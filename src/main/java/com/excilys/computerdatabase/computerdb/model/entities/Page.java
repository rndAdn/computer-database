package com.excilys.computerdatabase.computerdb.model.entities;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public class Page {
    
    private long rowByPages;
    private long pageNumber;
    private long totalRow;
    private String filter;
    private String orderBy;
    private List<Pageable> liste;
    
    private Page(BuilderPage builder){
        this.rowByPages = builder.rowByPages;
        this.pageNumber = builder.pageNumber;
        this.totalRow = builder.totalRow;
        this.filter = builder.filter;
        this.orderBy = builder.orderBy;   
        this.liste = builder.liste;
    }


    public long getRowByPages() {
        return rowByPages;
    }


    public long getPageNumber() {
        return pageNumber;
    }


    public long getTotalRow() {
        return totalRow;
    }


    public String getFilter() {
        return filter;
    }

    public String getOrderBy() {
        return orderBy;
    }


    public List<Pageable> getListe() {
        return liste;
    }

    
    
    public static class BuilderPage {
        private final long rowByPages;
        private final long pageNumber;
        private final String filter;
        private final String orderBy;
        
        private List<Pageable> liste;
        private long totalRow;
        
        public BuilderPage(String filter, String orderBy, long pageNumber, long rowByPages) {
            this.filter = filter;
            this.orderBy = orderBy;
            this.pageNumber = pageNumber;
            this.rowByPages = rowByPages;
        }
        
        public BuilderPage list(List<Pageable> list){
            this.liste = list;
            return this;
        }
        
        public BuilderPage totalRow(long totalRow){
            this.totalRow = totalRow;
            return this;
        }
        
        public Page build(){
            return new Page(this);
        }
        
    }



    public long getTotalNumberOfPage() {
        return (long) Math.ceil(totalRow / (double) rowByPages);
    }

}
