package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.ArrayList;
import java.util.List;

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
			list = Database.getCompanyDao().getCompanys((pageNumber-1) * rowByPages, rowByPages);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
