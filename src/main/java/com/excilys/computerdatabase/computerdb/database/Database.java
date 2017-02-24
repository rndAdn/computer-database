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

public enum Database {
	
    INSTANCE;

    private Connection connection;
    
    private String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String dbName = "computer-database-db";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "admincdb";
	private String password = "qwerty1234";
    private Logger LOGGER;

    Database() {
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
        connection = createConnection();
    }

    
    private Connection createConnection() {
    	LOGGER.info("connexion à la base de donnée ");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection(){
    	if(connection == null){
    		connection = createConnection();
    	}
        return connection;
    }
    
    public void closeConnection(){
    	try {
			connection.close();
			connection = null;
			LOGGER.info("Connection Fermée");
		} catch (SQLException e) {
			LOGGER.error("Connection non Fermée");
		}
    }
    
    public void rollback(){
    	try {
			connection.rollback();
			LOGGER.info("Connection RollBack");
		} catch (SQLException e) {
			LOGGER.error("Connection RollBack");
		}
    }
}
