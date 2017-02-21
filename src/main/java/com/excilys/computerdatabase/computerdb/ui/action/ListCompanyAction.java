package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesList;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesListCompany;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesListComputer;

public class ListCompanyAction implements ActionMenu {

	public void doAction() {
		
		PagesList pagesList = new PagesListCompany(Database.getCompanyDao().getNumberOfCompany());
		pagesList.showPage();
	}

}
