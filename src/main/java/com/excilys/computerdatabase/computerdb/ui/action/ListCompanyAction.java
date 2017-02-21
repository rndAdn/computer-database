package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Company;

public class ListCompanyAction implements ActionMenu {

	public void doAction() {
		
		System.out.println("--------CompanyList-------------");
		List<Company> computers = Database.getCompanyDao().getAllCompany();
        for (Company c : computers)
        	System.out.println(c);
        System.out.println("--------EndCompanyList----------");
	}

}
