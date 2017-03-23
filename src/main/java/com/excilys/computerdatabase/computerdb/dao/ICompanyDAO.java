package com.excilys.computerdatabase.computerdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Page;
import java.sql.Connection;

public interface ICompanyDAO {

    /**
     * Get a Company from database by it's id.
     *
     * @param id
     *            Company id in DatabaseManager.
     * @return A Optional<Company>. empty if the Company doesn't exist in the
     *         database.
     * @throws DaoException
     *             .
     */
    Optional<Company> getCompanyById(Connection connection, long id) throws DaoException;

    /**
     * Find all Company from database by name.
     *
     * @param name
     *            Of Company(s) to find
     * @param limitStart
     *            Start of first result.
     * @param size
     *            Max list size
     * @return a List<Pageable>
     * @throws DaoException
     *             .
     */
    Optional<Page> getCompanyByName(Connection connection, String name, long limitStart, long size) throws DaoException;

    /**
     * Get all Company from database.
     *
     * @param limitStart
     *            Start of first result.
     * @param size
     *            Max list size
     * @return a List<Pageable>
     * @throws DaoException
     *             .
     */
    Optional<Page> getCompanys(Connection connection, long limitStart, long size) throws DaoException;

    /**
     * Get number of company in database.
     *
     * @return Total number of company in the database.
     * @throws DaoException
     *             .
     */
    long getNumberOfCompany(Connection connection) throws DaoException;
    
    /**
     * Get number of company in database.
     * @param name .
     * @return Total number of company in the database.
     * @throws DaoException .
     */
    long getNumberOfCompany(Connection connection, String name) throws DaoException;
    
    /**
     * Delete a Computer in database given a Computer.
     *
     * @param company Representation of the computer to delete
     * @return true if computer is delete false otherwise
     * @throws DaoException .
     */
    boolean deleteCompany(Connection connection, Company company) throws DaoException;

    Optional<Page> getCompanys(Connection connection) throws DaoException;

}
