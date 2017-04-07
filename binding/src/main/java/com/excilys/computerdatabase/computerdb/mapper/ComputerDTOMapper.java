package com.excilys.computerdatabase.computerdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.entities.Computer.ComputerBuilder;
import com.excilys.computerdatabase.computerdb.model.entities.Page;
import com.excilys.computerdatabase.computerdb.model.entities.Pageable;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerDTOMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDTOMapper.class);

    public static ComputerDTO mapperComputerToDTO(Computer computer) {
        ComputerDTO.ComputerDTOBuilder computerDTOBuilder = new ComputerDTO.ComputerDTOBuilder(computer.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (computer.getDateIntroduced().isPresent()) {
            computerDTOBuilder.dateIntroduced(computer.getDateIntroduced().get().format(formatter));
        }
        if (computer.getDateDiscontinued().isPresent()) {
            computerDTOBuilder.dateDiscontinued(computer.getDateDiscontinued().get().format(formatter));
        }
        computerDTOBuilder.id(computer.getId()).company(CompanyDTOMapper.mapperCompanyToDTO(computer.getCompany()));
        ComputerDTO computerDTO = computerDTOBuilder.build();
        if (computer.getCompany().isPresent()) {
            computerDTO.setCompanyId(computer.getCompanyId());
            computerDTO.setCompanyName(computer.getCompany().get().getName());
        }
        return computerDTO;
    }

    public static ComputerDTO mapperComputerToDTO(Optional<Computer> computerOptional) {
        Computer computer;
        computer = computerOptional.orElseGet(() -> new ComputerBuilder("").id(-1).build());
        ComputerDTO computerDTO = mapperComputerToDTO(computer);
        return computerDTO;
    }

    public static Computer mapperComputerFromDTO(ComputerDTO computerDTO) {

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
        CompanyDTO companyDTO = new CompanyDTO.CompanyDTOBuilder().id(computerDTO.getCompanyId()).name(computerDTO.getCompanyName()).build();
        Optional<Company> company = CompanyDTOMapper.mapperCompanyFromDTO(companyDTO);
        computerBuilder.company(company.orElse(null));
        return computerBuilder.build();
    }

    public static List<ComputerDTO> mapperPagelistComputerToDTO(Page pagesListComputer) {
        List<Pageable> list = pagesListComputer.getListe();
        List<ComputerDTO> dtoList = new ArrayList<>();
        for (Pageable computer : list) {
            Computer c = (Computer) computer;
            dtoList.add(ComputerDTOMapper.mapperComputerToDTO(c));
        }
        return dtoList;
    }
}
