package com.excilys.computerdatabase.computerdb.dao.mapper;

import com.excilys.computerdatabase.computerdb.mapper.CompanyDTOMapper;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.Assert.*;


public class CompanyDTOMapperTest {
    @Test
    public void mapperCompanyToDTO_OptionalC_Empty_DTO() throws Exception {
        Optional<Company> companyOptional = Optional.empty();
        CompanyDTO companyDTO = CompanyDTOMapper.mapperCompanyToDTO(companyOptional);
        assertEquals(-1, companyDTO.getId());
        assertEquals("", companyDTO.getName());
    }

    @Test
    public void mapperCompanyToDTO_OptionalC_Name_ID_DTO() throws Exception {
        long id = 42;
        String name = "FABLER BJÖRN";
        Optional<Company> companyOptional = Optional.empty();
        Company company= new Company.CompanyBuilder(name).id(id).build();
        companyOptional = Optional.of(company);
        CompanyDTO companyDTO = CompanyDTOMapper.mapperCompanyToDTO(companyOptional);
        assertEquals(id, companyDTO.getId());
        assertEquals(name, companyDTO.getName());
    }

    @Test
    public void mapperCompanyToDTO_C_Full_DTO() throws Exception {
        long id = 42;
        String name = "FABLER BJÖRN";
        Company company= new Company.CompanyBuilder(name).id(id).build();
        CompanyDTO companyDTO = CompanyDTOMapper.mapperCompanyToDTO(company);
        assertEquals(id, companyDTO.getId());
        assertEquals(name, companyDTO.getName());
    }

    @Test
    public void mapperCompanyFromDTO_C_Empty_Optional() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO.CompanyDTOBuilder().build();
        Optional<Company> companyOptional = CompanyDTOMapper.mapperCompanyFromDTO(companyDTO);
        assertEquals(Optional.empty(), companyOptional);
    }

    @Test
    public void mapperCompanyFromDTO_C_Full_Optional() throws Exception {
        long id = 42;
        String name = "FABLER BJÖRN";
        CompanyDTO companyDTO = new CompanyDTO.CompanyDTOBuilder().id(id).name(name).build();
        Optional<Company> companyOptional = CompanyDTOMapper.mapperCompanyFromDTO(companyDTO);
        assertTrue(companyOptional.isPresent());
        assertEquals(id, companyOptional.get().getId());
        assertEquals(name, companyOptional.get().getName());
    }

}