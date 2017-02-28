package com.excilys.computerdatabase.computerdb.database;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.computerdatabase.computerdb.model.Computer;

public class ComputerDaoTest {
    
    private ComputerDao computerDao;
    private ResultSet resultSet;
    
    @Before
    public void executerAvantChaqueTest() {
        computerDao = new ComputerDao();
        resultSet = Mockito.mock(ResultSet.class);
    }
    
    @Test
    public void testMapComputer() throws SQLException{
        String computerName = "blabla";
        long computerId = 42;
        LocalDate intro = LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate fin = LocalDate.parse("16-02-1993", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        long companyId = 7;
        String companyName = "company blabla";
        
        
        Mockito.when(resultSet.getLong("id")).thenReturn(computerId);
        Mockito.when(resultSet.getString("name")).thenReturn(computerName);
        Mockito.when(resultSet.getObject("introduced", LocalDate.class)).thenReturn(intro);
        Mockito.when(resultSet.getObject("discontinued", LocalDate.class)).thenReturn(fin);
        Mockito.when(resultSet.getLong("company_id")).thenReturn(companyId);
        Mockito.when(resultSet.getString("company_name")).thenReturn(companyName);
        
        Computer computer = computerDao.mapComputer(resultSet);
        
        
        
        assertEquals("Computer Name : ", computerName, computer.getName());
        assertEquals("Computer Id : ", computerId, computer.getId());
        
        assertEquals("Date Intro : ", intro, computer.getDateIntroduced().get());
        assertEquals("Date Fin : ", fin, computer.getDateDiscontinued().get());
        
        assertEquals("Company Id : ", companyId, computer.getCompany().get().getId());
        assertEquals("Company Name : ", companyName, computer.getCompany().get().getName());
        
    }

}
