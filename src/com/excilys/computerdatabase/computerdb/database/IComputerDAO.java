package com.excilys.computerdatabase.computerdb.database;

import java.util.List;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.ui.pages.Pageable;

public interface IComputerDAO {
	
	public static final String SELECT_COMPUTER_BY_ID 			= "SELECT * FROM computer WHERE id = ?";	
	public static final String SELECT_COMPUTER_BY_NAME 			= "SELECT * FROM computer WHERE name = ? LIMIT ?, ?";
	public static final String SELECT_ALL_COMPUTERS_WITH_LIMIT 	= "SELECT * FROM computer LIMIT ?, ?";
	public static final String DELETE_COMPUTER 					= "DELETE FROM computer WHERE id=?;";
	public static final String INSERT_COMPUTER 					= "INSERT into computer (name,introduced,discontinued,company_id) values (?,?,?,?);";
	public static final String UPDATE_COMPUTER 					= "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	public static final String COUNT_TOTAL_COLUMN_NAME 			= "total";
	public static final String COUNT_COMPUTERS 					= "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM computer";
		
	
	public Computer getComputerById(long id);
	
	public List<Computer> getComputersByName(String name, int limitStart, int size);
	
	public List<Pageable> getComputers(int limitStart, int size);
	
	public boolean deleteComputer(Computer computer);
	
	public boolean updateComputer(Computer computer);
	
	public boolean insertComputer(Computer computer);
	
	public long countComputers();

}
