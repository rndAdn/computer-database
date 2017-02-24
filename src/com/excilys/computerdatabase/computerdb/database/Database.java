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
	*/
    private static Database db;   
    private static Logger logger;
    
    
    private Database() {
        //System.out.println("Hello");
    	
    	logger = LoggerFactory.getLogger("com.excilys.computerdatabase.computerdb.database.Database");
    	Class driver_class;
		try {
			driver_class = Class.forName(driver);
			Driver driver = (Driver) driver_class.newInstance();
	        DriverManager.registerDriver(driver);
	        //con= createConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
    }

    private static Connection createConnection() {
    	logger.info("connexion à la base de donnée ");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public static Connection getConnection(){
    	return Database.getInstance().createConnection();
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
			logger.info("Connection Fermée");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Connection non Fermée");
		}
    }
    
    public static void rollback(Connection connection){
    	try {
			connection.rollback();
			logger.info("Connection RollBack");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Connection RollBack");
		}
    }
    
    
}
