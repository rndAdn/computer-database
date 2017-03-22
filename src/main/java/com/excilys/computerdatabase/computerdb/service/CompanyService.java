package com.excilys.computerdatabase.computerdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.computerdb.dao.CompanyDao;
import com.excilys.computerdatabase.computerdb.dao.ComputerDao;
import com.excilys.computerdatabase.computerdb.dao.DaoException;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.dao.mapper.CompanyDTOMapper;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListCompany;


public enum CompanyService {

    INSTANCE;
    
    CompanyDao companyDao;
    
    CompanyService() {
        companyDao = new CompanyDao();
    }

    /**
     * Get a Company from DAO by it's id.
     *
     * @param id Company id in DatabaseManager.
     * @return A Optional Company. empty if the Company doesn't exist in the
     * database.
     * @throws DaoException .
     */
    public Optional<Company> getCompanyByid(long id) {
        try {
            return companyDao.getCompanyById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Get all Company from database.
     *
     * @return a PagesList
     * @throws DaoException .
     */
    public PagesListCompany getCompanys() {
        PagesListCompany pagesList = new PagesListCompany();
        try {
            long nbCompany = companyDao.getNumberOfCompany();

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
            dtoList.add(CompanyDTOMapper.mapperCompanyToDTO(c));
        }
        return dtoList;
    }

    public long removeCompany(Company company) {
        long nbSuppr = ComputerService.INSTANCE.removeComputersCompany(company.getId());
        try {

            boolean result = companyDao.deleteCompany(company);
            if (!result) {
                return -1;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return nbSuppr;
    }
}
