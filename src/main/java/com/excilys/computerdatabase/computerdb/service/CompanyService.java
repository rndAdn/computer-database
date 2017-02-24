package com.excilys.computerdatabase.computerdb.service;

import java.util.Optional;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Company;

public class CompanyService {
	
	public static Optional<Company> getCompanyByid(long id){
		CompanyDao companyDao = new CompanyDao();
		try {
			return companyDao.getCompanyById(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
