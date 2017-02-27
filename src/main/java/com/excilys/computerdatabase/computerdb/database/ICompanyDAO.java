package com.excilys.computerdatabase.computerdb.database;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public interface ICompanyDAO {
	
	public static final String SELECT_COMPANY_BY_ID 			= "SELECT * FROM company WHERE id = ?";	
	public static final String SELECT_COMPANY_BY_NAME 			= "SELECT * FROM company WHERE name = ? LIMIT ?, ?";
	public static final String SELECT_ALL_COMPANY_WITH_LIMIT 	= "SELECT * FROM company LIMIT ?, ?";
	public static final String COUNT_TOTAL_COLUMN_NAME 			= "total";
	public static final String COUNT_COMPANY 					= "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM company";

	
	public Optional<Company> getCompanyById(long id) throws DaoException;
	
	public List<Pageable> getCompanyByName(String name, int limitStart, int size) throws DaoException;
	
	public List<Pageable> getCompanys(int limitStart, int size) throws DaoException;
	
	public long getNumberOfCompany() throws DaoException;

}
