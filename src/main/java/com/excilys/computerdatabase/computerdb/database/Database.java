package com.excilys.computerdatabase.computerdb.database;

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


    private String dbName;
    private String poolSize;
    private String userName;
    private String password;
    private Logger LOGGER;
    private HikariDataSource ds;
    private String dataSourceClassName;
    
    ThreadLocal<Connection> connectionThreadLocal;

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
            poolSize = config.getString("maximumPoolSize");
            //LOGGER.info(poolSize);
        } catch (ConfigurationException ce) {
            ce.printStackTrace();
        }


        Properties props = new Properties();
        props.setProperty("dataSourceClassName", dataSourceClassName);
        props.setProperty("dataSource.user", userName);
        props.setProperty("dataSource.password", password);
        props.setProperty("dataSource.databaseName", dbName);
        //props.setProperty("maximumPoolSize", poolSize);
        //props.put("dataSource.logWriter", new PrintWriter(System.out));

        HikariConfig config = new HikariConfig(props);
        ds = new HikariDataSource(config);
        connectionThreadLocal = new ThreadLocal<>();
    }


    /**
     * return the Connection, create a new one If connection is null.
     *
     * @return A Connection
     */
    public Connection getConnection() {
        Connection connection;
        try {
            if (connectionThreadLocal.get() == null || connectionThreadLocal.get().isClosed()){
                connection = ds.getConnection();
                connectionThreadLocal.set(connection);
            }
        } catch (Exception e) {
            LOGGER.debug("get exception "+e.getMessage());
        }
        
        
        return connectionThreadLocal.get();
    }

    /**
     * Close the Connection.
     */
    public void closeConnection() {
        try {
            if (connectionThreadLocal != null && !connectionThreadLocal.get().isClosed()) {
                connectionThreadLocal.get().close();
            }
        } catch (SQLException e) {
            LOGGER.error("Connection non Fermée");
        }
    }

    /**
     * Undo database commit.
     */
    public void rollback() {
        try {
            if (connectionThreadLocal != null && !connectionThreadLocal.get().isClosed()) {
                connectionThreadLocal.get().rollback();
            }
        } catch (SQLException e) {
            LOGGER.error("Connection RollBack");
        }
    }
}
