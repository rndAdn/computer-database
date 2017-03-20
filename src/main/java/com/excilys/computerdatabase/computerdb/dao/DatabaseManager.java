package com.excilys.computerdatabase.computerdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Repository
@Scope("singleton")
public class DatabaseManager {

    private String dbName;
    private String serverName;
    private String poolSize;
    private String userName;
    private String password;
    private Logger LOGGER;
    private HikariDataSource ds = null;
    private String dataSourceClassName;
    private String autocommit;
    
    ThreadLocal<Connection> connectionThreadLocal;

    /**
     * DatabaseManager private Constructor.
     */
    DatabaseManager() {
        LOGGER = LoggerFactory.getLogger(getClass());
        LOGGER.info("DatabaseManager Constructor " + this);

        try {
            Configuration config = new PropertiesConfiguration("database.properties");
            dbName = config.getString("db_name");
            serverName = config.getString("dataSource.serverName");
            dataSourceClassName = config.getString("dataSourceClassName");
            userName = config.getString("dataSource.user");
            password = config.getString("dataSource.password");
            poolSize = config.getString("maximumPoolSize");
            autocommit = config.getString("autocommit");
            //LOGGER.info(poolSize);
        } catch (ConfigurationException ce) {
            ce.printStackTrace();
        }


        Properties props = new Properties();
        props.setProperty("dataSourceClassName", dataSourceClassName);
        props.setProperty("dataSource.user", userName);
        props.setProperty("dataSource.password", password);
        props.setProperty("dataSource.databaseName", dbName);
        //props.setProperty("dataSource.serverName", serverName);

        HikariConfig config = new HikariConfig(props);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(Integer.parseInt(poolSize));
        config.setConnectionTimeout(8000);
        config.setAutoCommit(Boolean.parseBoolean(autocommit));
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
            if (connectionThreadLocal.get() == null || connectionThreadLocal.get().isClosed()) {
                connection = ds.getConnection();
                connectionThreadLocal.set(connection);
            }
        } catch (Exception e) {
            LOGGER.debug("get exception " + e.getMessage());
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
