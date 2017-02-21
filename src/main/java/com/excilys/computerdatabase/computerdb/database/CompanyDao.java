package com.excilys.computerdatabase.computerdb.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.computerdb.model.Company;

public class CompanyDao {
	private Database database;

	CompanyDao( Database database ) {
		this.database = database;
	}
	
	
	public Company getCompanyById(int id){
		Statement st;
		try {
			st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery("SELECT * FROM company WHERE id = '" + id + "'");
			if(rset.next()){
				return mapCompany(rset);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
		
	}     

	public List<Company> getCompanyByName(String name){
	    
	    
	    List<Company> result = new ArrayList<Company>();
		try {
			Statement st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery("SELECT * FROM company WHERE name = '" + name + "'");
			
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

	public List<Company> getAllCompany(){
		
		List<Company> result = new ArrayList<Company>();
		try {
			Statement st = database.con.createStatement();

			ResultSet rset=null;
			rset = st.executeQuery("SELECT * FROM company");
			
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
}

