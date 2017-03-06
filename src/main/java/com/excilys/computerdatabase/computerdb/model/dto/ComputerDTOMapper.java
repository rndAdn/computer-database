package com.excilys.computerdatabase.computerdb.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListComputer;

public class ComputerDTOMapper {

    public static ComputerDTO mapperComputerDTO(Computer computer) {
        ComputerDTO.ComputerDTOBuilder computerDTOBuilder = new ComputerDTO.ComputerDTOBuilder(computer.getName());
        if (computer.getDateIntroduced().isPresent()) {
            computerDTOBuilder.dateIntroduced(computer.getDateIntroduced().get().toString());
        }
        if (computer.getDateDiscontinued().isPresent()) {
            computerDTOBuilder.dateDiscontinued(computer.getDateDiscontinued().get().toString());
        }
        computerDTOBuilder.id(computer.getId()).company(CompanyDTOMapper.mapperCompanyDTO(computer.getCompany()));
        return computerDTOBuilder.build();
    }

    public static Computer mapperComputerDTO(ComputerDTO computerDTO) {
        
        ComputerBuilder computerBuilder = new ComputerBuilder(computerDTO.getName());
        computerBuilder.id(computerDTO.getId());
        Optional<LocalDate> intro = Utils.stringToDate(computerDTO.getDateIntroduced());
        Optional<LocalDate> fin = Utils.stringToDate(computerDTO.getDateDiscontinued());
        
        if (intro.isPresent()) {
            computerBuilder.dateIntroduced(intro.get());
        }
        if (fin.isPresent()) {
            computerBuilder.dateDiscontinued(intro.get());
        }

        Optional<Company> company = CompanyDTOMapper.mapperCompanyDTO(computerDTO.getCompany());
        
        computerBuilder.company(company.orElse(null));
        return computerBuilder.build();
    }

    public static List<ComputerDTO> mapperPagelistComputerToDTO(PagesListComputer pagesListComputer){
        List<Pageable> list = pagesListComputer.getCurrentPage().getList();
        List<ComputerDTO> dtoList = new ArrayList<>();
        for (Pageable computer : list) {
            Computer c = (Computer) computer;
            dtoList.add(ComputerDTOMapper.mapperComputerDTO(c));
        }
        return dtoList;
    }
}
