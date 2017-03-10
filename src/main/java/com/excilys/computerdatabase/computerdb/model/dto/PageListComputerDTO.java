package com.excilys.computerdatabase.computerdb.model.dto;

import java.util.List;

public class PageListComputerDTO {
    private long totalPage;
    private long totalRow;
    private long pageNumber;
    private List<ComputerDTO> computerDTOList;

    public PageListComputerDTO(long totalPage, long totalRow, long pageNumber, List<ComputerDTO> computerDTOList) {
        this.totalPage = totalPage;
        this.totalRow = totalRow;
        this.pageNumber = pageNumber;
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
}
