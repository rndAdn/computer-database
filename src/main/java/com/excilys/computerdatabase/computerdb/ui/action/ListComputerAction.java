package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesList;

public class ListComputerAction implements ActionMenu {

	public void doAction() {
		
		PagesList pagesList = new PagesList(Database.getComputerDao().getNumberOfComputer());
		pagesList.showPage();
		
	}

}
