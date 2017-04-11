package com.excilys.computerdatabase.computerdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import com.excilys.computerdatabase.computerdb.model.entities.Page;
import com.excilys.computerdatabase.computerdb.model.entities.Pageable;

public interface IComputerDAO {


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
    Optional<Page> getComputersByName(String name, long limitStart, long size, String orderby) throws DaoException;

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
    Optional<Page> getComputers(long limitStart, long size, String orderby) throws DaoException;

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
    
    long countComputersWithName(String name) throws DaoException;
    
    long deleteComputersCompany(long companyId) throws DaoException;

}
