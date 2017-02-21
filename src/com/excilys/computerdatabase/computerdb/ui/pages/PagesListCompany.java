package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.Database;

public class PagesListCompany extends PagesList {

	public PagesListCompany(int totalRow) {
		super(totalRow);
	}

	@Override
	public List<Pageable> getList() {
		return Database.getCompanyDao().getAllCompany((pageNumber-1) * rowByPages, rowByPages);
	}

}
