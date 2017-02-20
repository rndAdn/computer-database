package com.excilys.computerdatabase.computerdb;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.computerdb.model.Computer;

import database.Database;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        System.out.println( "Hello World!" );
        
        Database db = Database.getInstance();
        
        List<Computer> computers = db.getAllComputers();
        for (Computer c : computers)
        	System.out.println(c);
        
       
    }
}
