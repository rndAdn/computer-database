package com.excilys.computerdatabase.computerdb.database;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.ui.pages.Pageable;

public interface IComputerDAO {
	
	public static final String SELECT_COMPUTER_BY_ID 			= "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE c.id = ?";	
	public static final String SELECT_COMPUTER_BY_NAME 			= "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE c.name = ? LIMIT ?, ?";
	public static final String SELECT_ALL_COMPUTERS_WITH_LIMIT 	= "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id LIMIT ?, ?";
	public static final String DELETE_COMPUTER 					= "DELETE FROM computer WHERE id=?;";
	public static final String INSERT_COMPUTER 					= "INSERT into computer (name,introduced,discontinued,company_id) values (?,?,?,?);";
	public static final String UPDATE_COMPUTER 					= "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	public static final String COUNT_TOTAL_COLUMN_NAME 			= "total";
	public static final String COUNT_COMPUTERS 					= "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM computer";
		
	
	public Optional<Computer> getComputerById(long id) throws DaoException;
	
	public List<Computer> getComputersByName(String name, int limitStart, int size)  throws DaoException;
	
	public List<Pageable> getComputers(int limitStart, int size) throws DaoException;
	
	public boolean deleteComputer(Computer computer) throws DaoException;
	
	public boolean updateComputer(Computer computer) throws DaoException;
	
	public boolean insertComputer(Computer computer) throws DaoException;
	
	public long countComputers() throws DaoException;

}
