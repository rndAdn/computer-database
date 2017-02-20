package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.dbutils.DbUtils;
//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.ResultSetHandler;
//import org.apache.commons.dbutils.handlers.BeanHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.mysql.jdbc.StringUtils;


public class Database {
	String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
    String dbName = "computer-database-db";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "admincdb";
    String password = "qwerty1234";

    private static Database db;   
    protected Connection con ;
    
    
    
    private Database() {
        //System.out.println("Hello");
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
            connection = DriverManager.getConnection(url, userName, password);
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
    
    
    
   /* public Company getCompanyById(int id){
		QueryRunner run=new QueryRunner();

		BeanHandler beanHandler = new BeanHandler(Company.class);
		ResultSetHandler h = beanHandler;

		Object company=null;
		try{
			company =run.query(this.con, "SELECT * FROM company WHERE id = '" + id + "'", h);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Company)company;
	}     
    
    public List<Company> getCompanysByName(String name){

    	List<Company> results = new ArrayList<Company>();
        QueryRunner qr = new QueryRunner();
        try {
        	results = (List<Company>) qr.query(con, "SELECT * FROM company WHERE name = '" + name + "'",
                    new BeanListHandler(Company.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	return results;
        }
       
	}  
    
    public List<Company> getAllCompany(){

    	List<Company> results = new ArrayList<Company>();
        QueryRunner qr = new QueryRunner();
        try {
        	results = (List<Company>) qr.query(con, "SELECT * FROM company",
                    new BeanListHandler(Company.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	return results;
        }
       
	}
    */
    public static ComputerDao getComputerDao() {
        return new ComputerDao( getInstance() );
    }
    
    
}
