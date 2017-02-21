package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.List;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class ListComputerAction implements ActionMenu {

	public void doAction() {
		List<Computer> computers = Database.getComputerDao().getAllComputers();
	    
		System.out.println("--------ComputerList-------------");
		for (Computer c : computers)
	    	System.out.println(c);
		System.out.println("--------EndComputerList----------");
	}

}
