package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;

public class PagesListComputer extends Pages {

    @Override
    public List<Pageable> getItems() {
        List<Pageable> list = new ArrayList<>();
        try {
            if (StringUtils.isBlank(filter)) {
                list = ComputerDao.INSTANCE.getComputers((pageNumber - 1) * rowByPages, rowByPages);
                setTotalNumberOfRow(ComputerDao.INSTANCE.countComputers());
                setPageIndex(pageNumber);
            } else {
                list = ComputerDao.INSTANCE.getComputersByName(filter, (pageNumber - 1) * rowByPages, rowByPages);
                
                setTotalNumberOfRow(ComputerDao.INSTANCE.countComputersWithName(filter));
                setPageIndex(pageNumber);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return list;


    }
}
