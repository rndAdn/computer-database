package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;

public class PagesListCompany extends PagesList {

	public PagesListCompany(long l) {
		super(l);
	}

	@Override
	public List<Pageable> getList() {
		
		List<Pageable> list = new ArrayList<>();
		try {
			CompanyDao companyDao = new CompanyDao();
			list = companyDao.getCompanys((pageNumber-1) * rowByPages, rowByPages);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return list;
	}

}
