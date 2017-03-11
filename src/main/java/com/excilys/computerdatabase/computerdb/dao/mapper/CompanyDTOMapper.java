package com.excilys.computerdatabase.computerdb.dao.mapper;

import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.controller.ControllerCompany;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyDTOMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDTOMapper.class);

    public static CompanyDTO mapperCompanyToDTO(Optional<Company> optionalCompany) {
        if (optionalCompany.isPresent()) {
            return mapperCompanyToDTO(optionalCompany.get());
        }

        return new CompanyDTO.CompanyDTOBuilder().id(-1).name("").build();
    }

    public static CompanyDTO mapperCompanyToDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO.CompanyDTOBuilder().id(company.getId()).name(company.getName()).build();
        return companyDTO;
    }

    public static Optional<Company> mapperCompanyFromDTO(CompanyDTO companyDTO) {
        Optional<Company> companyOptional = Optional.empty();
        if (ControllerCompany.CONTROLLER_COMPANY.checkId(companyDTO.getId())) {
            companyOptional = Optional.of(new Company.CompanyBuilder(companyDTO.getName()).id(companyDTO.getId()).build());
        } else {
            LOGGER.info("Company mapper False Check: " + companyDTO);
        }
        return companyOptional;
    }

}
