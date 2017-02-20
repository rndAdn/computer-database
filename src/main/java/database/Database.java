package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	String url = "jdbc:mysql://localhost:3306/";
    String dbName = "computer-database-db";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "admincdb";
    String password = "qwerty1234";

    private static Database db;   
    private Connection con ;
    private Statement statement;
    
    
    
    private Database() {
        System.out.println("Hello");
        con= createConnection();
    }

    @SuppressWarnings("rawtypes")
    public Connection createConnection() {
        Connection connection = null;
        try {
            // Load the JDBC driver
            Class driver_class = Class.forName(driver);
            Driver driver = (Driver) driver_class.newInstance();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(url + dbName, userName, password);
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
}
