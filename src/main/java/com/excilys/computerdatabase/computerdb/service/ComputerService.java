package com.excilys.computerdatabase.computerdb.service;

import java.util.Optional;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class ComputerService {
	
	
	public boolean ajoutComputer(Computer computer){
		ComputerDao dao = new ComputerDao();
		boolean result = false;
		try {
			result = dao.insertComputer(computer);
		} catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon de la création");
		}
		finally {
			System.out.println();
			
		}
		return result;
	}

	public boolean deleteComputer(Computer computer) {
		ComputerDao computerDao = new ComputerDao();
		boolean result = false;
		try {
			result = computerDao.deleteComputer(computer);
		} catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon de la suppression");
		} finally {
			System.out.println();
			
		}
		return result;
		
	}
	
	public Optional<Computer> getComputerById(long id){
		ComputerDao computerDao = new ComputerDao();
		Optional<Computer> optionalComputer = Optional.empty();
		try{
			optionalComputer = computerDao.getComputerById(id);
		}
		catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon de la supression");
		}
		finally {
			System.out.println();
		}
		return optionalComputer;
	}

	public boolean updateComputer(Computer computer){
		ComputerDao dao = new ComputerDao();
		boolean result = false;
		try {
			result = dao.updateComputer(computer);
		} catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon de la mise à jour");
		}
		finally {
			System.out.println();
			
		}
		return result;
	}
}
