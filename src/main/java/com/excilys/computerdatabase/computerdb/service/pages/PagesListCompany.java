package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;

public class PagesListCompany extends PagesList {

    public PagesListCompany() {
        super();
    }

    @Override
    public List<Pageable> getList() {

        List<Pageable> list = new ArrayList<>();
        try {
            CompanyDao companyDao = new CompanyDao();
            list = companyDao.getCompanys((pageNumber - 1) * rowByPages, rowByPages);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void setNumberOfCompany(long nbCompany) {
        this.totalNumberOfpages = (int) Math.ceil(nbCompany / (double) rowByPages);
    }
}
