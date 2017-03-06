package com.excilys.computerdatabase.computerdb.model.dto;

import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Company.CompanyBuilder;

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
    
    public static Company mapperCompanyDTO(CompanyDTO companyDTO) {
        Company company = new Company.CompanyBuilder(companyDTO.getName()).id(companyDTO.getId()).build();
        return company;
    }

}
