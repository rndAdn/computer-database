package com.excilys.computerdatabase.computerdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.computerdb.dao.CompanyDao;
import com.excilys.computerdatabase.computerdb.dao.ComputerDao;
import com.excilys.computerdatabase.computerdb.dao.DaoException;
import com.excilys.computerdatabase.computerdb.dao.DatabaseManager;
import com.excilys.computerdatabase.computerdb.dao.SpringConfig;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Page;
import com.excilys.computerdatabase.computerdb.model.entities.Pageable;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.dao.mapper.CompanyDTOMapper;

@Repository
public class CompanyService {

    
    //ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    //DatabaseManager databaseManager;// = context.getBean(DatabaseManager.class);
    @Autowired
    CompanyDao companyDao;// = context.getBean(CompanyDao.class);
    

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
    public Optional<Page> getCompanys() {
        Optional<Page> page = Optional.empty();
        try {
            page = companyDao.getCompanys();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return page;
    }


    public List<CompanyDTO> getCompanyDTOList() {
        List<CompanyDTO> dtoList = new ArrayList<>();
        Optional<Page> page = getCompanys();
        List<Pageable> list = new ArrayList<>();
        if(page.isPresent()) {
            list = page.get().getListe();
        }

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
