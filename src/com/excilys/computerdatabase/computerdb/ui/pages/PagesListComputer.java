package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.Database;

public class PagesListComputer extends PagesList {

	public PagesListComputer(long l) {
		super(l);
	}

	@Override
	public List<Pageable> getList() {
		return Database.getComputerDao().getComputers((pageNumber-1) * rowByPages, rowByPages);
	}

}
