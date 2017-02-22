package com.excilys.computerdatabase.computerdb.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.ui.pages.Pageable;

public class CompanyDao implements ICompanyDAO{
	private Database database;

	CompanyDao( Database database ) {
		this.database = database;
	}
	
	@Override
	public Company getCompanyById(long id){
		PreparedStatement selectStatement;
		
		try {
			selectStatement = database.con.prepareStatement(SELECT_COMPANY_BY_ID);
			
			selectStatement.setLong(1, id);
			
			ResultSet rset = selectStatement.executeQuery();
			if(rset.next()){
				return mapCompany(rset);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
		
	}     

	@Override
	public List<Pageable> getCompanyByName(String name, int limitStart, int size){    
	    List<Pageable> result = new ArrayList<>();
	    PreparedStatement selectStatement;
	    
		try {
			
			selectStatement = database.con.prepareStatement(SELECT_COMPANY_BY_NAME);
			
			selectStatement.setString(1, name);
			selectStatement.setLong(2, limitStart);
			selectStatement.setLong(3, size);
			
			ResultSet rset = selectStatement.executeQuery();
			
			while(rset.next()){
				result.add(mapCompany(rset));
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			return result;
		}
		
	   
	}  

	
	@Override
	public List<Pageable> getCompanys(int limitStart, int size){
		
		List<Pageable> result = new ArrayList<>();
	    PreparedStatement selectStatement;
	    
		try {
			
			selectStatement = database.con.prepareStatement(SELECT_ALL_COMPANY_WITH_LIMIT);
			
			selectStatement.setLong(1, limitStart);
			selectStatement.setLong(2, size);
			
			ResultSet rset = selectStatement.executeQuery();
			
			while(rset.next()){
				result.add(mapCompany(rset));
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			return result;
		}  
	}
	
	private Company mapCompany(ResultSet rset) throws SQLException{
		Company company = new Company();
		company.setId( rset.getInt("id"));
		company.setName( rset.getString("name"));
		return company;
	}


	public long getNumberOfCompany() {
		long number = 0;
		try {
			Statement st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery(COUNT_COMPANY);
			if(rset.next()){
				number = rset.getLong(COUNT_TOTAL_COLUMN_NAME);
				//logger.debug("getNumberOfComputer : " + number);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			return number;
		}  
	}
}

