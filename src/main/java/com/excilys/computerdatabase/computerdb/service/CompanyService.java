package com.excilys.computerdatabase.computerdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.mapper.CompanyDTOMapper;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListCompany;

public enum CompanyService {

    INSTANCE;
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
    public Optional<Company> getCompanyByid(long id) {
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
    public PagesListCompany getCompanys() {
        PagesListCompany pagesList = new PagesListCompany();
        try {
            long nbCompany = CompanyDao.INSTANCE.getNumberOfCompany();

            pagesList.setTotalNumberOfRow(nbCompany);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return pagesList;
    }


    public List<CompanyDTO> getCompanyDTOList() {
        List<CompanyDTO> dtoList = new ArrayList<>();
        PagesListCompany pagesListCompany = getCompanys();
        List<Pageable> list = pagesListCompany.getCurrentPage().getList();

        for (Pageable company : list) {
            Company c = (Company) company;
            dtoList.add(CompanyDTOMapper.mapperCompanyDTO(c));
        }
        return dtoList;
    }

}
