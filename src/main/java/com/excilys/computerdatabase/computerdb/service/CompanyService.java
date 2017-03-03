package com.excilys.computerdatabase.computerdb.service;

import java.util.Optional;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListCompany;

public class CompanyService {

    /**
     * Get a Company from DAO by it's id.
     *
     * @param id
     *            Company id in Database.
     * @return A Optional Company. empty if the Company doesn't exist in the
     *         database.
     * @throws DaoException
     *             .
     */
    public static Optional<Company> getCompanyByid(long id) {
        try {
            return CompanyDao.INSTANCE.getCompanyById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Get all Company from database.
     *
     * @return a PagesList
     * @throws DaoException
     *             .
     */
    public static PagesListCompany getCompanys() {
        PagesListCompany pagesList = new PagesListCompany();
        try {
            long nbCompany = CompanyDao.INSTANCE.getNumberOfCompany();

            pagesList.setTotalNumberOfRow(nbCompany);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return pagesList;
    }

}
