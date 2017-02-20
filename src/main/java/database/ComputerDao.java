package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.ResultSetHandler;
//import org.apache.commons.dbutils.handlers.BeanHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.excilys.computerdatabase.computerdb.model.Computer;
//import com.mysql.jdbc.Statement;

public class ComputerDao {
	
	private Database database;
	
	ComputerDao( Database database ) {
        this.database = database;
    }

	
	public Computer getComputerById(int id){
		Statement st;
		try {
			st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery("SELECT * FROM computer WHERE id = '" + id + "'");
			if(rset.next()){
				Computer computer = new Computer();
				computer.setId( rset.getInt("id"));
				computer.setName( rset.getString("name"));
				computer.setDateIntroduced( rset.getDate("introduced"));
				computer.setDateDiscontinued( rset.getDate("discontinued"));
				computer.setCompagnyId( rset.getInt("company_id"));
				return computer;
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
		
	}     
    
    public List<Computer> getComputersByName(String name){
        
        
        List<Computer> result = new ArrayList<Computer>();
		try {
			Statement st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery("SELECT * FROM computer WHERE name = '" + name + "'");
			
			while(rset.next()){
				Computer computer = new Computer();
				computer.setId( rset.getInt("id"));
				computer.setName( rset.getString("name"));
				computer.setDateIntroduced( rset.getDate("introduced"));
				computer.setDateDiscontinued( rset.getDate("discontinued"));
				computer.setCompagnyId( rset.getInt("company_id"));
				result.add(computer);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			return result;
		}
		
       
	}  
    
    public List<Computer> getAllComputers(){
    	
    	List<Computer> result = new ArrayList<Computer>();
		try {
			Statement st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery("SELECT * FROM computer");
			
			while(rset.next()){
				Computer computer = new Computer();
				computer.setId( rset.getInt("id"));
				computer.setName( rset.getString("name"));
				computer.setDateIntroduced( rset.getDate("introduced"));
				computer.setDateDiscontinued( rset.getDate("discontinued"));
				computer.setCompagnyId( rset.getInt("company_id"));
				result.add(computer);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			return result;
		}  
	}

}
