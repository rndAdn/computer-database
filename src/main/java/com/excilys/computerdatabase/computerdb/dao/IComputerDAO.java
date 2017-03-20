package com.excilys.computerdatabase.computerdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public interface IComputerDAO {

    String SELECT_COMPUTER_BY_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE c.id = ?";
    String SELECT_COMPUTER_BY_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE UPPER(c.name) LIKE UPPER(?) LIMIT ?, ?";
    String SELECT_ALL_COMPUTERS_WITH_LIMIT = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id LIMIT ?, ?";
    String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
    String INSERT_COMPUTER = "INSERT into computer (name,introduced,discontinued,company_id) values (?,?,?,?);";
    String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
    String COUNT_TOTAL_COLUMN_NAME = "total";
    String COUNT_COMPUTERS = "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM computer";

    /**
     * Get a Computer from database by it's id.
     *
     * @param id
     *            Computer id in DatabaseManager.
     * @return A Optional<Computer>. empty if the Computer doesn't exist in the
     *         database.
     * @throws DaoException
     *             .
     */
    Optional<Computer> getComputerById(long id) throws DaoException;

    /**
     * Find all Computer from database by name.
     *
     * @param name
     *            Of Computer(s) to find
     * @param limitStart
     *            Start of first result.
     * @param size
     *            Max list size
     * @return a List<Pageable>
     * @throws DaoException
     *             .
     */
    List<Pageable> getComputersByName(String name, long limitStart, long size) throws DaoException;

    /**
     * Get all Computer from database.
     *
     * @param limitStart
     *            Start of first result.
     * @param size
     *            Max list size
     * @return a List<Pageable>
     * @throws DaoException
     *             .
     */
    List<Pageable> getComputers(long limitStart, long size) throws DaoException;

    /**
     * Delete a Computer in database given a Computer.
     *
     * @param computer
     *            Representation of the computer to delete
     * @return true if computer is delete false otherwise
     * @throws DaoException
     *             .
     */
    boolean deleteComputer(Computer computer) throws DaoException;

    /**
     * Update a Computer in database given a Computer.
     *
     * @param computer
     *            Representation of the computer to update
     * @return true if the computer is update in database
     * @throws DaoException
     *             .
     */
    boolean updateComputer(Computer computer) throws DaoException;

    /**
     * Insert a Computer in database given a Computer.
     *
     * @param computer
     *            Representation of the computer to create
     * @return true if the computer is created in database
     * @throws DaoException
     *             .
     */
    boolean insertComputer(Computer computer) throws DaoException;

    /**
     * Get number of Computer in database.
     *
     * @return Total number of Computer in the database.
     * @throws DaoException
     *             .
     */
    long countComputers() throws DaoException;

}
