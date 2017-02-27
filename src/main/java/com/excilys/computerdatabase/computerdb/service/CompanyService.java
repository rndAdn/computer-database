package com.excilys.computerdatabase.computerdb.service;

import java.util.Optional;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.service.pages.PagesList;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListCompany;

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
	
	public PagesList getCompanys(){
		PagesListCompany pagesList = new PagesListCompany();
		CompanyDao companyDao = new CompanyDao();
		try {
			long nb_company = companyDao.getNumberOfCompany();
			
			pagesList.setNumberOfCompany(nb_company);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return pagesList;
	}

}
