package com.excilys.computerdatabase.computerdb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.ui.pages.Pageable;

public class CompanyDao implements ICompanyDAO{
	private static final Logger logger = LoggerFactory.getLogger("com.excilys.computerdatabase.computerdb.database.CompanyDao");
	
	@Override
	public Optional<Company> getCompanyById(long id) throws DaoException{
		Optional<Company> optionalCompany = Optional.empty();
		PreparedStatement selectStatement;
		Connection connection = Database.INSTANCE.getConnection();
		
		try {
			selectStatement = connection.prepareStatement(SELECT_COMPANY_BY_ID);
			
			selectStatement.setLong(1, id);
			
			ResultSet rset = selectStatement.executeQuery();
			if(rset.next()){
				optionalCompany = Optional.of(mapCompany(rset));
			}
			
		} catch (SQLException e) {
			logger.error("getCompanyById : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		finally {
			Database.INSTANCE.closeConnection();
		}
		logger.info("getCompanyById result :" + optionalCompany);
		return optionalCompany;
		
	}     

	@Override
	public List<Pageable> getCompanyByName(String name, int limitStart, int size)throws DaoException{    
	    List<Pageable> result = new ArrayList<>();
	    PreparedStatement selectStatement;
	    Connection connection =  Database.INSTANCE.getConnection();
		
		try {
			selectStatement = connection.prepareStatement(SELECT_COMPANY_BY_NAME);
			
			selectStatement.setString(1, name);
			selectStatement.setLong(2, limitStart);
			selectStatement.setLong(3, size);
			
			ResultSet rset = selectStatement.executeQuery();
			
			while(rset.next()){
				result.add(mapCompany(rset));
			}
			
		} catch (SQLException e) {
			logger.error("getCompanyByName : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		finally {
			Database.INSTANCE.closeConnection();
		}
		logger.info("getCompanyByName result size : " + result.size());
		return result;
		
	   
	}  
	
	@Override
	public List<Pageable> getCompanys(int limitStart, int size)throws DaoException{
		
		List<Pageable> result = new ArrayList<>();
	    PreparedStatement selectStatement;
	    Connection connection =  Database.INSTANCE.getConnection();
		
		try {
			selectStatement = connection.prepareStatement(SELECT_ALL_COMPANY_WITH_LIMIT);
			
			selectStatement.setLong(1, limitStart);
			selectStatement.setLong(2, size);
			
			ResultSet rset = selectStatement.executeQuery();
			
			while(rset.next()){
				result.add(mapCompany(rset));
			}
			
		} catch (SQLException e) {
			logger.error("getCompanys : " + e.getMessage());
			throw new DaoException(e.getMessage());
		}
		finally {
			Database.INSTANCE.closeConnection();
		}
		logger.info("getCompanys result size : " + result.size());
		return result;
	}
	
	private Company mapCompany(ResultSet rset)throws SQLException{
		Company company = new Company();
		company.setId( rset.getInt("id"));
		company.setName(rset.getString("name"));
		return company;
	}

	public long getNumberOfCompany() throws DaoException{
		long number = 0;
		Connection connection = Database.INSTANCE.getConnection();
		
		try {
			Statement st = connection.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery(COUNT_COMPANY);
			if(rset.next()){
				number = rset.getLong(COUNT_TOTAL_COLUMN_NAME);
			}
		} catch (SQLException e1) {
			throw new DaoException(e1.getMessage());
		}
		finally {
			Database.INSTANCE.closeConnection();
		}
		logger.info("getNumberOfCompany result : " + number);
		return number;
	}
}
