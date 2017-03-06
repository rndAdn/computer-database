package com.excilys.computerdatabase.computerdb.model.dto;

import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Company.CompanyBuilder;
import com.excilys.computerdatabase.computerdb.model.ComputerValidator;
import com.excilys.computerdatabase.computerdb.model.Utils;

public class CompanyDTOMapper {

    public static CompanyDTO mapperCompanyDTO(Optional<Company> optionalCompany) {
        if (optionalCompany.isPresent()) {
            return mapperCompanyDTO(optionalCompany.get());
        }

        return new CompanyDTO.CompanyDTOBuilder().build();
    }

    public static CompanyDTO mapperCompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO.CompanyDTOBuilder().id(company.getId()).name(company.getName()).build();
        return companyDTO;
    }
    
    public static Optional<Company> mapperCompanyDTO(CompanyDTO companyDTO) {
        Optional<Company> companyOptional = Optional.empty();
        if (ComputerValidator.checkID(companyDTO.getId()))
            companyOptional.of(new Company.CompanyBuilder(companyDTO.getName()).id(companyDTO.getId()).build());
        return companyOptional;
    }

}
