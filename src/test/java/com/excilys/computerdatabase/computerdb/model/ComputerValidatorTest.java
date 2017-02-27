package com.excilys.computerdatabase.computerdb.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.Test;

public class ComputerValidatorTest {
    
    @Test
    public void testCheckId_Negatif(){
        long id = -1;
        assertFalse(ComputerValidator.checkID(id));
    }
    
    @Test
    public void testCheckId_Zero(){
        long id = 0;
        assertFalse(ComputerValidator.checkID(id));
    }
    
    @Test
    public void testCheckId_MinLong(){
        long id = Long.MIN_VALUE;
        assertFalse(ComputerValidator.checkID(id));
    }
    
    @Test
    public void testCheckId_MaxLong(){
        long id = Long.MAX_VALUE;
        assertTrue(ComputerValidator.checkID(id));
    }
    
    @Test
    public void testCheckId_42(){
        long id = 42;
        assertTrue(ComputerValidator.checkID(id));
    }
    
    @Test
    public void testCheckCompany_Null(){
        assertFalse(ComputerValidator.checkCompany(null));    
    }
    
    @Test
    public void testCheckCompany_Empty(){
        assertTrue(ComputerValidator.checkCompany(new Company()));    
    }
    
    @Test
    public void testCheckCompany_Full(){
        Company company = new Company();
        company.setId(42);
        company.setName("FABLER BJÖRN");
        
        assertTrue(ComputerValidator.checkCompany(company));    
    }
    
    @Test 
    public void testCompareDate_NullNull(){
        Optional<LocalDate> intro = Optional.empty();
        Optional<LocalDate> fin = Optional.empty();
        assertTrue(ComputerValidator.compareDate(intro, fin));   
    }
    
    @Test 
    public void testCompareDate_DateNull(){
        Optional<LocalDate> intro = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Optional<LocalDate> fin = Optional.empty();
        assertTrue(ComputerValidator.compareDate(intro, fin));   
    }
    
    @Test 
    public void testCompareDate_NullDate(){
        Optional<LocalDate> intro = Optional.empty(); 
        Optional<LocalDate> fin = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertTrue(ComputerValidator.compareDate(intro, fin));   
    }
    
    @Test 
    public void testCompareDate_DateDate(){
        Optional<LocalDate> intro = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Optional<LocalDate> fin = Optional.of(LocalDate.parse("16-02-1993", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertTrue(ComputerValidator.compareDate(intro, fin));   
    }
    
    @Test 
    public void testCompareDate_DateDateFalse(){
        Optional<LocalDate> intro = Optional.of(LocalDate.parse("16-02-1993", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Optional<LocalDate> fin = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertFalse(ComputerValidator.compareDate(intro, fin));   
    }
    @Test 
    public void testCompareDate_DateEqualsDate(){
        Optional<LocalDate> intro = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Optional<LocalDate> fin = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertFalse(ComputerValidator.compareDate(intro, fin));   
    }
    
    
}
