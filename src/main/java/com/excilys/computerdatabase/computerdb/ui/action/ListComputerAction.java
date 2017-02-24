package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesList;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesListComputer;

public class ListComputerAction implements ActionMenu {

	public void doAction() {
		PagesList pagesList;
		try {

			ComputerDao computerDao = new ComputerDao();
			pagesList = new PagesListComputer(computerDao.countComputers());
			pagesList.showPage();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		
	}

}
