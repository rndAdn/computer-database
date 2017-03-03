package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;

public class PagesListCompany extends Pages {

    @Override
    public List<Pageable> getItems() {

        List<Pageable> list = new ArrayList<>();
        try {
            if (StringUtils.isBlank(filter)) {
                list = CompanyDao.INSTANCE.getCompanys((pageNumber - 1) * rowByPages, rowByPages);
                totalRow = CompanyDao.INSTANCE.getNumberOfCompany();
            } else {
                list = CompanyDao.INSTANCE.getCompanyByName(filter, (pageNumber - 1) * rowByPages, rowByPages);
                totalRow = CompanyDao.INSTANCE.getNumberOfCompany();
                // TODO : ADD requete totalRow =
                // CompanyDao.INSTANCE.getNumberOfCompany();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return list;
    }
}
