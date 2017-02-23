package com.excilys.computerdatabase.computerdb.database;

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
import com.excilys.computerdatabase.computerdb.ui.pages.Pageable;

//import ch.qos.logback.classic.Logger;


public class ComputerDao implements IComputerDAO{
	
	private Database database;
	private static Logger LOGGER;
	
	ComputerDao( Database database ) {
        this.database = database;
        LOGGER = LoggerFactory.getLogger("database.ComputerDao");
        
    }

	// DONE
	@Override
	public Computer getComputerById(long id) throws DaoException{
		PreparedStatement selectStatement;
		
		try {
			selectStatement = database.con.prepareStatement(SELECT_COMPUTER_BY_ID);
			
			selectStatement.setLong(1, id);
			
			
			ResultSet rset=null;
			//rset = st.executeQuery("SELECT * FROM computer WHERE id = '" + id + "'");
			
			rset = selectStatement.executeQuery();
			
			if(rset.next()){
				return mapComputer(rset);
			}
			
		} catch (SQLException e) {
			LOGGER.error("getComputerById : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return null;
		
	}     

	// DONE
	@Override
	public List<Computer> getComputersByName(String name, int limitStart, int size) throws DaoException{
		PreparedStatement selectStatement;
		List<Computer> result = new ArrayList<>();
		
		try {
			selectStatement = database.con.prepareStatement(SELECT_COMPUTER_BY_NAME);
			
			selectStatement.setString(1, name);
			selectStatement.setInt(2, limitStart);
			selectStatement.setInt(3, size);

			ResultSet rset=null;
			rset = selectStatement.executeQuery();
			
			while(rset.next()){
				result.add(mapComputer(rset));
			}
			
		} catch (SQLException e) {
			LOGGER.error("getComputersByName : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		finally {
			return result;
		}
		
       
	}  
	
	//DONE
	@Override
    public List<Pageable> getComputers(int limitStart, int size) throws DaoException {
    	
		PreparedStatement selectStatement;
		List<Pageable> result = new ArrayList<>();
		
		try {
			selectStatement = database.con.prepareStatement(SELECT_ALL_COMPUTERS_WITH_LIMIT);
			
			selectStatement.setInt(1, limitStart);
			selectStatement.setInt(2, size);
			
			ResultSet rset=null;
			rset = selectStatement.executeQuery();
			
			while(rset.next()){
				result.add(mapComputer(rset));
			}
			
		} catch (SQLException e) {
			LOGGER.error("getComputers : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		finally {
			//logger.debug("getAllComputers : " + result.size());
			return result;
		}  
	}
    
	//DONE
	@Override
	public boolean deleteComputer(Computer computer)  throws DaoException{
		try {
			PreparedStatement deleteStatment = database.con.prepareStatement(DELETE_COMPUTER);
			
			deleteStatment.setLong(1, computer.getId());
			deleteStatment.executeUpdate(); 
		} catch (SQLException e) {
			LOGGER.error("deleteComputer : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return true;
	}
	
	//DONE
	@Override
	public boolean updateComputer(Computer computer)  throws DaoException{
		try {
			//logger.debug("Update de l'ordinateur " + computer);
			PreparedStatement updateStatment = database.con.prepareStatement(UPDATE_COMPUTER);
			updateStatment.setString(1, computer.getName());
			updateStatment.setDate(2, computer.getDateIntroduced());
			updateStatment.setDate(3, computer.getDateDiscontinued());
			
			Long companyId = computer.getCompanyId();
			
			if( companyId == null) updateStatment.setNull(4, java.sql.Types.INTEGER);
			else updateStatment.setLong(4, companyId);
						
			updateStatment.setLong(5, computer.getId());
			
			updateStatment.executeUpdate(); 
		} catch (SQLException e) {
			LOGGER.error("updateComputer : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return true;
	}
	
	//DONE
	@Override
	public boolean insertComputer(Computer computer) throws DaoException {
		try {

			PreparedStatement insertStatment = database.con.prepareStatement(INSERT_COMPUTER);
			
			insertStatment.setString(1, computer.getName());
			insertStatment.setDate(2, computer.getDateIntroduced());
			insertStatment.setDate(3, computer.getDateDiscontinued());
			
			Long companyId = computer.getCompanyId();
			
			if(companyId == null) insertStatment.setNull(4, java.sql.Types.INTEGER);
			else insertStatment.setLong(4, companyId);
			
			insertStatment.executeUpdate(); 
		} catch (SQLException e) {
			LOGGER.error("insertComputer : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return true;
	}
	
	//DONE
	@Override
	public long countComputers() throws DaoException{
		long number = 0;
		try {
			Statement st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery(COUNT_COMPUTERS);
			if(rset.next()){
				number = rset.getLong(COUNT_TOTAL_COLUMN_NAME);
				//logger.debug("getNumberOfComputer : " + number);
			}
		} catch (SQLException e) {
			LOGGER.error("countComputers : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		finally {
			return number;
		}  
	}

	private Computer mapComputer(ResultSet rset) throws SQLException{
    	Computer computer = new Computer();
    	computer.setId( rset.getLong("id"));
    	computer.setName( rset.getString("name"));
    	computer.setDateIntroduced( rset.getDate("introduced"));
		computer.setDateDiscontinued( rset.getDate("discontinued"));
		
		Company company = new Company();
		long company_id = rset.getLong("company_id"); 
		if(! rset.wasNull()){
			company.setId(company_id);
			company.setName(rset.getString("company_name"));
			
		}
		computer.setCompagny(company);
		return computer;
	}

	
}
