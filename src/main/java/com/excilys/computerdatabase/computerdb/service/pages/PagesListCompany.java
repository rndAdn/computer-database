/*package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.dao.CompanyDao;
import com.excilys.computerdatabase.computerdb.dao.DaoException;
import com.excilys.computerdatabase.computerdb.model.entities.Company;

public class PagesListCompany extends Pages {

    @Override
    public List<Pageable> getItems() {
        CompanyDao companyDao = new CompanyDao();

        List<Pageable> list = new ArrayList<>();
        try {
            if (StringUtils.isBlank(filter)) {
                list = companyDao.getCompanys((pageNumber - 1) * rowByPages, rowByPages);
                totalRow = companyDao.getNumberOfCompany();
            } else {
                list = companyDao.getCompanyByName(filter, (pageNumber - 1) * rowByPages, rowByPages);
                totalRow = companyDao.getNumberOfCompany();
                // TODO : ADD requete totalRow =
                // CompanyDao.INSTANCE.getNumberOfCompany();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return list;
    }
}
*/