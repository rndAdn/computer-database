package com.excilys.computerdatabase.computerdb.model.controller;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import org.junit.Test;

public class ControllerComputerTest {
    
    @Test
    public void testCheckId_Negatif(){
        long id = -1;
        assertFalse(ControllerComputer.CONTROLLER_COMPUTER.checkId(id));
    }
    
    @Test
    public void testCheckId_Zero(){
        long id = 0;
        assertFalse(ControllerComputer.CONTROLLER_COMPUTER.checkId(id));
    }
    
    @Test
    public void testCheckId_MinLong(){
        long id = Long.MIN_VALUE;
        assertFalse(ControllerComputer.CONTROLLER_COMPUTER.checkId(id));
    }
    
    @Test
    public void testCheckId_MaxLong(){
        long id = Long.MAX_VALUE;
        assertTrue(ControllerComputer.CONTROLLER_COMPUTER.checkId(id));
    }
    
    @Test
    public void testCheckId_42(){
        long id = 42;
        assertTrue(ControllerComputer.CONTROLLER_COMPUTER.checkId(id));
    }

    
    @Test 
    public void testCompareDate_NullNull(){
        Optional<LocalDate> intro = Optional.empty();
        Optional<LocalDate> fin = Optional.empty();
        assertTrue(ControllerComputer.CONTROLLER_COMPUTER.compareDate(intro, fin));
    }
    
    @Test 
    public void testCompareDate_DateNull(){
        Optional<LocalDate> intro = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Optional<LocalDate> fin = Optional.empty();
        assertTrue(ControllerComputer.CONTROLLER_COMPUTER.compareDate(intro, fin));
    }
    
    @Test 
    public void testCompareDate_NullDate(){
        Optional<LocalDate> intro = Optional.empty(); 
        Optional<LocalDate> fin = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertTrue(ControllerComputer.CONTROLLER_COMPUTER.compareDate(intro, fin));
    }
    
    @Test 
    public void testCompareDate_DateDate(){
        Optional<LocalDate> intro = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Optional<LocalDate> fin = Optional.of(LocalDate.parse("16-02-1993", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertTrue(ControllerComputer.CONTROLLER_COMPUTER.compareDate(intro, fin));
    }
    
    @Test 
    public void testCompareDate_DateDateFalse(){
        Optional<LocalDate> intro = Optional.of(LocalDate.parse("16-02-1993", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Optional<LocalDate> fin = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertFalse(ControllerComputer.CONTROLLER_COMPUTER.compareDate(intro, fin));
    }
    @Test 
    public void testCompareDate_DateEqualsDate(){
        Optional<LocalDate> intro = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Optional<LocalDate> fin = Optional.of(LocalDate.parse("07-08-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertTrue(ControllerComputer.CONTROLLER_COMPUTER.compareDate(intro, fin));
    }
    
    
}
