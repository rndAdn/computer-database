package com.excilys.computerdatabase.computerdb.model.dto;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.Computer.ComputerBuilder;

public class ComputerDTOMapper {

    public static ComputerDTO mapperComputerDTO(Optional<Computer> optionalComputer) {
        if (optionalComputer.isPresent()) {
            return mapperComputerDTO(optionalComputer.get());
        }
        return new ComputerDTO.ComputerDTOBuilder("").build();
    }

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
}
