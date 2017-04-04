package com.excilys.computerdatabase.computerdb.dao.mapper;


import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class ComputerDTOMapperTest {


    @Test
    public void mapperComputerToDTO_C_Name_DTO() throws Exception {
        String name = "FABLER BJÖRN";
        Computer computer = new Computer.ComputerBuilder(name).build();
        ComputerDTO computerDTO = ComputerDTOMapper.mapperComputerToDTO(computer);

        assertEquals(-1, computerDTO.getId());
        assertEquals(name, computerDTO.getName());
        assertEquals("", computerDTO.getDateIntroduced());
        assertEquals("", computerDTO.getDateDiscontinued());
        //assertEquals(-1, computerDTO.getCompany().getId());
        //assertEquals("", computerDTO.getCompany().getName());
    }

    @Test
    public void mapperComputerToDTO_C_Name_ID_DTO() throws Exception {
        long id = 42;
        String name = "FABLER BJÖRN";
        Computer computer = new Computer.ComputerBuilder(name).id(id).build();
        ComputerDTO computerDTO = ComputerDTOMapper.mapperComputerToDTO(computer);

        assertEquals(id, computerDTO.getId());
        assertEquals(name, computerDTO.getName());
        assertEquals("", computerDTO.getDateIntroduced());
        assertEquals("", computerDTO.getDateDiscontinued());
        //assertEquals(-1, computerDTO.getCompany().getId());
        //assertEquals("", computerDTO.getCompany().getName());
    }

    @Test
    public void mapperComputerToDTO_C_Full_WOut_Company_DTO() throws Exception {
        long id = 42;
        String name = "FABLER BJÖRN";
        String intro = "01-02-1990";
        String fin = "02-02-1990";

        Computer computer = new Computer.ComputerBuilder(name)
                .id(id)
                .dateIntroduced(LocalDate.parse(intro, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .dateDiscontinued(LocalDate.parse(fin, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
        ComputerDTO computerDTO = ComputerDTOMapper.mapperComputerToDTO(computer);

        assertEquals(id, computerDTO.getId());
        assertEquals(name, computerDTO.getName());
        assertEquals(intro, computerDTO.getDateIntroduced());
        assertEquals(fin, computerDTO.getDateDiscontinued());
        //assertEquals(-1, computerDTO.getCompany().getId());
        //assertEquals("", computerDTO.getCompany().getName());
    }

    @Test
    public void mapperComputerToDTO_C_Full_With_Company_DTO() throws Exception {
        long id = 42;
        String name = "FABLER BJÖRN";
        long idCompany = 41;
        String nameCompany = "FABLER BJÖRN Inc.";
        String intro = "01-02-1990";
        String fin = "02-02-1990";
        Company company = new Company.CompanyBuilder(nameCompany).id(idCompany).build();

        Computer computer = new Computer.ComputerBuilder(name)
                .id(id)
                .dateIntroduced(LocalDate.parse(intro, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .dateDiscontinued(LocalDate.parse(fin, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .company(company)
                .build();
        ComputerDTO computerDTO = ComputerDTOMapper.mapperComputerToDTO(computer);

        assertEquals(id, computerDTO.getId());
        assertEquals(name, computerDTO.getName());
        assertEquals(intro, computerDTO.getDateIntroduced());
        assertEquals(fin, computerDTO.getDateDiscontinued());
        //assertEquals(idCompany, computerDTO.getCompany().getId());
        //assertEquals(nameCompany, computerDTO.getCompany().getName());
    }


}