package com.excilys.computerdatabase.computerdb.database;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum Database {

    INSTANCE;


    private String url;
    private String dbName;
    private String driver;
    private String userName;
    private String password;
    private Logger LOGGER;
    private HikariDataSource ds;
    private String dataSourceClassName;

    /**
     * Database private Constructor.
     */
    Database() {
        LOGGER = LoggerFactory.getLogger(getClass());
        LOGGER.info("Database Constructor " + this);

        try {
            Configuration config = new PropertiesConfiguration("database.properties");
            dbName = config.getString("db_name");
            dataSourceClassName = config.getString("dataSourceClassName");
            userName = config.getString("dataSource.user");
            password = config.getString("dataSource.password");
        } catch (ConfigurationException ce) {
            ce.printStackTrace();
        }


        Properties props = new Properties();
        props.setProperty("dataSourceClassName", dataSourceClassName);
        props.setProperty("dataSource.user", userName);
        props.setProperty("dataSource.password", password);
        props.setProperty("dataSource.databaseName", dbName);
        props.put("dataSource.logWriter", new PrintWriter(System.out));

        HikariConfig config = new HikariConfig(props);
        ds = new HikariDataSource(config);

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
            connection = ds.getConnection();
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
     */
    public void closeConnection(Connection connection) {
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
