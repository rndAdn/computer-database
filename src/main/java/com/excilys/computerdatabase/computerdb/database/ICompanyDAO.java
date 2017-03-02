package com.excilys.computerdatabase.computerdb.database;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public interface ICompanyDAO {

    String SELECT_COMPANY_BY_ID = "SELECT * FROM company WHERE id = ?";
    String SELECT_COMPANY_BY_NAME = "SELECT * FROM company WHERE name = ? LIMIT ?, ?";
    String SELECT_ALL_COMPANY_WITH_LIMIT = "SELECT * FROM company LIMIT ?, ?";
    String COUNT_TOTAL_COLUMN_NAME = "total";
    String COUNT_COMPANY = "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM company";

    /**
     * Get a Company from database by it's id.
     *
     * @param id
     *            Company id in Database.
     * @return A Optional Company . empty if the Company doesn't exist in the
     *         database.
     * @throws DaoException
     *             .
     */
    Optional<Company> getCompanyById(long id) throws DaoException;

    /**
     * Find all Company from database by name.
     *
     * @param name
     *            Of Company(s) to find
     * @param limitStart
     *            Start of first result.
     * @param size
     *            Max list size
     * @return a List Pageable
     * @throws DaoException
     *             .
     */
    List<Pageable> getCompanyByName(String name, long limitStart, long size) throws DaoException;

    /**
     * Get all Company from database.
     *
     * @param limitStart
     *            Start of first result.
     * @param size
     *            Max list size
     * @return a List Pageable
     * @throws DaoException
     *             .
     */
    List<Pageable> getCompanys(long limitStart, long size) throws DaoException;

    /**
     * Get number of company in database.
     *
     * @return Total number of company in the database.
     * @throws DaoException
     *             .
     */
    long getNumberOfCompany() throws DaoException;

}
