package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.Company;

//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.ResultSetHandler;
//import org.apache.commons.dbutils.handlers.BeanHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.excilys.computerdatabase.computerdb.model.Computer;
//import com.mysql.jdbc.Statement;

public class ComputerDao {
	
	private Database database;
	private Logger logger;
	
	ComputerDao( Database database ) {
        this.database = database;
        logger = LoggerFactory.getLogger("database.ComputerDao");
        
    }

	
	public Computer getComputerById(int id){
		Statement st;
		try {
			st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery("SELECT * FROM computer WHERE id = '" + id + "'");
			if(rset.next()){
				return mapComputer(rset);
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
				result.add(mapComputer(rset));
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
				result.add(mapComputer(rset));
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			return result;
		}  
	}
    
    public void deleteComputer(Computer computer){
		try {
			logger.debug("suppression de l'ordinateur " + computer);
			PreparedStatement deleteStatment = database.con.prepareStatement("DELETE FROM computer WHERE id=?;");
			deleteStatment.setInt(1, computer.getId());
			deleteStatment.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    
    private Computer mapComputer(ResultSet rset) throws SQLException{
    	Computer computer = new Computer();
    	computer.setId( rset.getInt("id"));
    	computer.setName( rset.getString("name"));
    	computer.setDateIntroduced( rset.getDate("introduced"));
		computer.setDateDiscontinued( rset.getDate("discontinued"));
		computer.setCompagny( Database.getCompanyDao().getCompanyById(rset.getInt("company_id")));
		return computer;
	}


	public void insertComputer(Computer computer) {
		try {
			logger.debug("Ajout de l'ordinateur " + computer);
			PreparedStatement insertStatment = database.con.prepareStatement("INSERT into computer (name,introduced,discontinued,company_id) values (?,?,?,?);");
			//insertStatment.setInt(1, computer.getId());
			insertStatment.setString(1, computer.getName());
			insertStatment.setDate(2, computer.getDateIntroduced());
			insertStatment.setDate(3, computer.getDateDiscontinued());
			insertStatment.setInt(4, computer.getCompany().getId());
			
			insertStatment.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
