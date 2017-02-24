package com.excilys.computerdatabase.computerdb.service;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class ComputerService {
	
	
	public static boolean ajoutComputer(Computer computer){
		ComputerDao dao = new ComputerDao();
		boolean result = false;
		try {
			result = dao.insertComputer(computer);
		} catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon");
		}
		finally {
			System.out.println();
			
		}
		return result;
	}

	public void deleteComputer(Computer computer) {
		ComputerDao computerDao = new ComputerDao();
		try {
			computerDao.deleteComputer(computer);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
	}
	
}
