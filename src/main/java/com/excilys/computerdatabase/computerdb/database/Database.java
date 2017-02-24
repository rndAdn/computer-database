package com.excilys.computerdatabase.computerdb.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.mysql.jdbc.StringUtils;


public class Database {
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String dbName = "computer-database-db";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "admincdb";
	private static String password = "qwerty1234";

	/*private static String dbName;
	private static String driver;
	private static String userName;
	private static String password;
	private static String url;*/
	
    private static Database db;   
    private static Logger LOGGER;
    
    
    private Database() {
    	LOGGER = LoggerFactory.getLogger(getClass());
    	Class driver_class;
    	LOGGER.info("Database Constructor " + this);
    	
		try {
			driver_class = Class.forName(driver);
			Driver driver = (Driver) driver_class.newInstance();
	        DriverManager.registerDriver(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        
        
    }

    private static Connection createConnection() {
    	LOGGER.info("connexion à la base de donnée ");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public static Connection getConnection(){
    	return getInstance().createConnection();
    }

    
    private static Database getInstance() {
        if (db == null) {
        	db = new Database();
        }
        return db;
    }
    
    public static void closeConnection(Connection connection){
    	try {
			connection.close();
			LOGGER.info("Connection Fermée");
		} catch (SQLException e) {
			LOGGER.error("Connection non Fermée");
		}
    }
    
    public static void rollback(Connection connection){
    	try {
			connection.rollback();
			LOGGER.info("Connection RollBack");
		} catch (SQLException e) {
			LOGGER.error("Connection RollBack");
		}
    }
    
    
}
