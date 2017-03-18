package com.excilys.computerdatabase.computerdb.model.dto;

import java.util.List;

public class PageListComputerDTO {
    private final long pageSize;
    private final String filter;
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
}
