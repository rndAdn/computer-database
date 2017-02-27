package com.excilys.computerdatabase.computerdb.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;

public class UtilsTest {
    
    @Test
    public void stringToDate_Null(){
        String date = null;
        assertEquals(Optional.empty(), Utils.stringToDate(date));
    }
    
    @Test
    public void stringToDate_Aaaaa(){
        String date = "aaaaaaa";
        assertEquals(Optional.empty(), Utils.stringToDate(date));
    }
    
    @Test
    public void stringToDate_53_13_2012(){
        String date = "53-12-2012";
        assertEquals(Optional.empty(), Utils.stringToDate(date));
    }
    
    @Test
    public void stringToDate_Bb_Cc_Dddd(){
        String date = "bb-cc-dddd";
        assertEquals(Optional.empty(), Utils.stringToDate(date));
    }
    
    @Test
    public void stringToDate_Empty(){
        String date = "";
        assertEquals(Optional.empty(), Utils.stringToDate(date));
    }
    
    @Test
    public void stringToDate_07_08_1990(){
        String date = "07-08-1990";
        assertTrue(LocalDate.of(1990, 8, 7).isEqual(Utils.stringToDate(date).get()));
    }

    @Test
    public void stringToId_Null(){
        String id = null;
        assertEquals(-1, Utils.stringToId(id));
    }
    
    @Test
    public void stringToId_Aaaaaa(){
        String id = "Aaaaaa";
        assertEquals(-1, Utils.stringToId(id));
    }
    
    @Test
    public void stringToId_Empty(){
        String id = "";
        assertEquals(-1, Utils.stringToId(id));
    }
    
    @Test
    public void stringToId_Negatif_42(){
        String id = "-42";
        assertEquals(-42, Utils.stringToId(id));
    }
    
    @Test
    public void stringToId_42(){
        String id = "42";
        assertEquals(42, Utils.stringToId(id));
    }
    
    @Test
    public void stringToId_MaxLong(){
        String id = "" + Long.MAX_VALUE;
        assertEquals(Long.MAX_VALUE, Utils.stringToId(id));
    }
    
    @Test
    public void stringToId_MinLong(){
        String id = "" + Long.MIN_VALUE;
        assertEquals(Long.MIN_VALUE, Utils.stringToId(id));
    }
}
