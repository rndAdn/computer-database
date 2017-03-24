package com.excilys.computerdatabase.computerdb.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.computerdb.dao.CompanyDao;
import com.excilys.computerdatabase.computerdb.dao.DaoException;
import com.excilys.computerdatabase.computerdb.dao.DatabaseManager;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Page;
import com.excilys.computerdatabase.computerdb.model.entities.Pageable;
import com.excilys.computerdatabase.computerdb.view.web.DashboardController;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.dao.mapper.CompanyDTOMapper;

@Repository
@Transactional
public class CompanyService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    CompanyDao companyDao;
    @Autowired
    ComputerService computerService;
    
    @Autowired
    DatabaseManager databaseManager;
    

    
    /**
     * Get a Company from DAO by it's id.
     *
     * @param id Company id in DatabaseManager.
     * @return A Optional Company. empty if the Company doesn't exist in the
     * database.
     * @throws DaoException .
     */
    @Transactional(readOnly=true)
    public Optional<Company> getCompanyByid(long id) {
        try /*(Connection connection = databaseManager.getConnection())*/ {
            Optional<Company> company =  companyDao.getCompanyById(id);
            //connection.commit();
            
            return company;
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
    @Transactional(readOnly=true)
    public Optional<Page> getCompanys() {
        Optional<Page> page = Optional.empty();
        try (Connection connection = databaseManager.getConnection()) {
            page = companyDao.getCompanys();
            
            connection.commit();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return page;
    }

    @Transactional(readOnly=true)
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
        long nbSuppr = computerService.removeComputersCompany(company.getId());
        try (Connection connection = databaseManager.getConnection()) {

            boolean result = companyDao.deleteCompany(company);
            connection.commit();
            if (!result) {
                return -1;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return nbSuppr;
    }
}
