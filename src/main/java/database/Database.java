package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.mysql.jdbc.StringUtils;


public class Database {
	String url = "jdbc:mysql://localhost:3306/";
    String dbName = "computer-database-db";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "admincdb";
    String password = "qwerty1234";

    private static Database db;   
    private Connection con ;
    
    
    
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
    
    public Computer getComputerById(int id){
		QueryRunner run=new QueryRunner();

		BeanHandler beanHandler = new BeanHandler(Computer.class);
		ResultSetHandler h = beanHandler;

		Object computer=null;
		try{
			computer =run.query(this.con, "SELECT * FROM computer WHERE id = '" + id + "'", h);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Computer)computer;
	}     
    
    public List<Computer> getComputersByName(String name){

    	List<Computer> results = new ArrayList<Computer>();
        QueryRunner qr = new QueryRunner();
        try {
        	results = (List<Computer>) qr.query(con, "SELECT * FROM computer WHERE name = '" + name + "' LIMIT 10",
                    new BeanListHandler(Computer.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	return results;
        }
       
	}     
}
