package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;

public class PagesListCompany extends PagesList {

    @Override
    public List<Pageable> getList() {

        List<Pageable> list = new ArrayList<>();
        try {
            list = CompanyDao.INSTANCE.getCompanys((pageNumber - 1) * rowByPages, rowByPages);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Pageable> getListFilterByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }
}
