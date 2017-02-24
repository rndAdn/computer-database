package com.excilys.computerdatabase.computerdb.service;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class ComputerService {
	
	
	public static void ajoutComputer(Computer computer){
		ComputerDao dao = new ComputerDao();
		try {
			boolean result = dao.insertComputer(computer);
		} catch (DaoException e) {
			//sc.close();
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon");
			e.printStackTrace();
		}
		finally {
			//sc.close();
			System.out.println();
		}
	}

	public void deleteComputer(Computer computer) {
		ComputerDao computerDao = new ComputerDao();
		try {
			computerDao.deleteComputer(computer);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
