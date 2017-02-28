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

    Optional<Company> getCompanyById(long id) throws DaoException;

    List<Pageable> getCompanyByName(String name, long limitStart, long size) throws DaoException;

    List<Pageable> getCompanys(long limitStart, long size) throws DaoException;

    long getNumberOfCompany() throws DaoException;

}
