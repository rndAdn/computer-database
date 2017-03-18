package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.dao.ComputerDao;
import com.excilys.computerdatabase.computerdb.dao.DaoException;

public class PagesListComputer extends Pages {

    @Override
    public List<Pageable> getItems() {
        List<Pageable> list = new ArrayList<>();
        try {
            if (StringUtils.isBlank(filter)) {
                long nbComputer = ComputerDao.INSTANCE.countComputers();
                setTotalNumberOfRow(nbComputer);
                if ((pageNumber - 1) * rowByPages > nbComputer) {
                    pageNumber = 1;
                }
                list = ComputerDao.INSTANCE.getComputers((pageNumber - 1) * rowByPages, rowByPages, orderBy);
                setPageIndex(pageNumber);
            } else {
                long nbComputer = ComputerDao.INSTANCE.countComputersWithName(filter);
                setTotalNumberOfRow(nbComputer);
                if ((pageNumber - 1) * rowByPages > nbComputer) {
                    pageNumber = 1;
                }
                list = ComputerDao.INSTANCE.getComputersByName(filter, (pageNumber - 1) * rowByPages, rowByPages, orderBy);
                setPageIndex(pageNumber);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return list;


    }
}
