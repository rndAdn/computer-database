package com.excilys.computerdatabase.computerdb.model.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "PageListComputerDTO")
public class PageListComputerDTO {
    private long pageSize;
    private     String filter;
    private long totalPage;
    private long totalRow;
    private long pageNumber;
    private List<ComputerDTO> computerDTOList;
    private String orderBy;

    public PageListComputerDTO(String filter, long totalPage, long totalRow, long pageNumber, long pageSize, List<ComputerDTO> computerDTOList, String orderBy) {
        this.filter = filter;
        this.totalPage = totalPage;
        this.totalRow = totalRow;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.computerDTOList = computerDTOList;
    }
    
    public PageListComputerDTO() {
    
    }

    public long getTotalPage() {
        return totalPage;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public List<ComputerDTO> getComputerDTOList() {
        return computerDTOList;
    }

    public long getPageSize() {
        return pageSize;
    }

    public String getFilter() {
        return filter;
    }

    public String getOrderBy() {
        return orderBy;
    }
    
    

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setComputerDTOList(List<ComputerDTO> computerDTOList) {
        this.computerDTOList = computerDTOList;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "PageListComputerDTO [pageSize=" + pageSize + ", filter=" + filter + ", totalPage=" + totalPage
                + ", totalRow=" + totalRow + ", pageNumber=" + pageNumber + ", computerDTOList=" + computerDTOList
                + ", orderBy=" + orderBy + "]";
    }
    
    
    
    
}
