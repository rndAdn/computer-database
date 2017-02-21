package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.Database;

public class PagesListComputer extends PagesList {

	public PagesListComputer(int totalRow) {
		super(totalRow);
	}

	@Override
	public List<Pageable> getList() {
		return Database.getComputerDao().getAllComputers((pageNumber-1) * rowByPages, rowByPages);
	}

}
