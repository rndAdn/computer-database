package com.excilys.computerdatabase.computerdb.database;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public interface IComputerDAO {

    String SELECT_COMPUTER_BY_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE c.id = ?";
    String SELECT_COMPUTER_BY_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE c.name = ? LIMIT ?, ?";
    String SELECT_ALL_COMPUTERS_WITH_LIMIT = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id LIMIT ?, ?";
    String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
    String INSERT_COMPUTER = "INSERT into computer (name,introduced,discontinued,company_id) values (?,?,?,?);";
    String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
    String COUNT_TOTAL_COLUMN_NAME = "total";
    String COUNT_COMPUTERS = "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM computer";

    Optional<Computer> getComputerById(long id) throws DaoException;

    List<Computer> getComputersByName(String name, int limitStart, int size) throws DaoException;

    List<Pageable> getComputers(int limitStart, int size) throws DaoException;

    boolean deleteComputer(Computer computer) throws DaoException;

    boolean updateComputer(Computer computer) throws DaoException;

    boolean insertComputer(Computer computer) throws DaoException;

    long countComputers() throws DaoException;

}
