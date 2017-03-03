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


    private String url;
    private String dbName;
    private String driver;
    private String userName;
    private String password;
    private Logger LOGGER;

    /**
     * Database private Constructor.
     */
    Database() {
        LOGGER = LoggerFactory.getLogger(getClass());
        Class driverClass;
        LOGGER.info("Database Constructor " + this);

        try {
            Configuration config = new PropertiesConfiguration("database.properties");
            url = config.getString("url");
            dbName = config.getString("db_name");
            driver = config.getString("driver");
            userName = config.getString("username");
            password = config.getString("password");
        } catch (ConfigurationException ce) {
            ce.printStackTrace();
        }

        try {
            driverClass = Class.forName(driver);
            Driver driver = (Driver) driverClass.newInstance();
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

    /**
     * private method to Create a new Connection.
     *
     * @return Connection to the database.
     */
    private Connection createConnection() {
        LOGGER.info("connexion à la base de donnée ");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * return the Connection, create a new one If connection is null.
     *
     * @return A Connection
     */
    public Connection getConnection() {
        return createConnection();
    }

    /**
     * Close the Connection.
     *
     */
    public void closeConnection(Connection connection ) {
        try {
            connection.close();
            connection = null;
            LOGGER.info("Connection Fermée");
        } catch (SQLException e) {
            LOGGER.error("Connection non Fermée");
        }
    }

    /**
     * Undo database commit.
     */
    public void rollback(Connection connection) {
        try {
            connection.rollback();
            LOGGER.info("Connection RollBack");
        } catch (SQLException e) {
            LOGGER.error("Connection RollBack");
        }
    }
}
