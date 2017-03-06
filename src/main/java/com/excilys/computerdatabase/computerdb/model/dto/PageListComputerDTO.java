package com.excilys.computerdatabase.computerdb.model.dto;

import java.util.List;

public class PageListComputerDTO {
    private long totalPage;
    private long totalRow;
    private List<ComputerDTO> computerDTOList;

    public PageListComputerDTO(long totalPage, long totalRow, List<ComputerDTO> computerDTOList){
        this.totalPage = totalPage;
        this.totalRow = totalRow;
        this.computerDTOList = computerDTOList;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public List<ComputerDTO> getComputerDTOList() {
        return computerDTOList;
    }
}
