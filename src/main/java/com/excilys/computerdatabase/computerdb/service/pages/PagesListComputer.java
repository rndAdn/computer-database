package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
public class PagesListComputer extends PagesList {

    @Override
    public List<Pageable> getList() {
        List<Pageable> list = new ArrayList<>();
        try {
            ComputerDao computerDao = new ComputerDao();
            list = computerDao.getComputers((pageNumber - 1) * rowByPages, rowByPages);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Pageable> getListFilterByName(String name) {
        List<Pageable> list = new ArrayList<>();
        try {
            ComputerDao computerDao = new ComputerDao();
            list = computerDao.getComputersByName(name, (pageNumber - 1) * rowByPages, rowByPages);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return list;
    }

}
