package com.excilys.computerdatabase.computerdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class DatabaseManager {

    private Logger  LOGGER = LoggerFactory.getLogger(DatabaseManager.class);
    //private HikariDataSource ds = null;
    
    @Autowired
    private MyDataSource ds;
    
    ThreadLocal<Connection> connectionThreadLocal;

    /**
     * DatabaseManager private Constructor.
     */
    DatabaseManager() {
        LOGGER = LoggerFactory.getLogger(getClass());
        LOGGER.info("DatabaseManager Constructor " +
        this);
        connectionThreadLocal = new ThreadLocal<>();
        
    }
    /*

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
    }*/


    /**
     * return the Connection, create a new one If connection is null.
     *
     * @return A Connection
     * @throws DaoException 
     */
    public Connection getConnection() throws DaoException {
        Connection connection;
        try {
            if (connectionThreadLocal.get() == null || connectionThreadLocal.get().isClosed()) {
                connection = ds.getConnection();
                connection.setAutoCommit(false);
                connectionThreadLocal.set(connection);
            }
        } catch (Exception e) {
            LOGGER.debug("get exception " + e.getMessage());
            throw new DaoException(e.getMessage());
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


    public MyDataSource getDataSource() {
        return ds;
    }
}
