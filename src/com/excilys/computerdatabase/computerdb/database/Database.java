package com.excilys.computerdatabase.computerdb.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.mysql.jdbc.StringUtils;


public class Database {
	String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
    String dbName = "computer-database-db";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "admincdb";
    String password = "qwerty1234";

    private static Database db;   
    protected Connection con ;
    private Logger logger;
    
    
    private Database() {
        //System.out.println("Hello");
    	logger = LoggerFactory.getLogger("com.excilys.computerdatabase.computerdb.database.Database");
        con= createConnection();
        
    }

    public Connection createConnection() {
    	logger.info("creation de la connexion à la base de donnée ");
        Connection connection = null;
        try {
            // Load the JDBC driver
            Class driver_class = Class.forName(driver);
            Driver driver = (Driver) driver_class.newInstance();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return connection;
    }

    
    public static Database getInstance() {
        if (db == null) {
        	db = new Database();
        }
        return db;
    }
    
    
    
    public static IComputerDAO getComputerDao() {
        return new ComputerDao( getInstance() );
    }
    
    public static CompanyDao getCompanyDao() {
        return new CompanyDao( getInstance() );
    }
    
    
}
