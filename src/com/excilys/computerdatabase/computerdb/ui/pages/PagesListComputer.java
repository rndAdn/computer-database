package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;

public class PagesListComputer extends PagesList {

	public PagesListComputer(long l) {
		super(l);
	}

	@Override
	public List<Pageable> getList() {
		List<Pageable> list = new ArrayList<>();
		try {
			ComputerDao computerDao = new ComputerDao();
			list = computerDao.getComputers((pageNumber-1) * rowByPages, rowByPages);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
