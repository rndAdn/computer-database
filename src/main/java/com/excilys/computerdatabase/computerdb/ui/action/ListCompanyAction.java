package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesList;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesListCompany;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesListComputer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListCompanyAction implements ActionMenu {
	
	private static Logger LOGGER;
	
	public ListCompanyAction(){
		LOGGER = LoggerFactory.getLogger("com.excilys.computerdatabase.computerdb.ui.action.ListCompanyAction");
	}

	public void doAction() {
		PagesList pagesList;
		try {
			CompanyDao companyDao = new CompanyDao();
			pagesList = new PagesListCompany(companyDao.getNumberOfCompany());
			pagesList.showPage();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
	}

}
