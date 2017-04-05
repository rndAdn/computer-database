package com.excilys.computerdatabase.computerdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.dao.controller.ControllerDAOCompany;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.hibernate.SessionFactory;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Page;
import com.excilys.computerdatabase.computerdb.model.entities.Pageable;
import com.excilys.computerdatabase.computerdb.view.HibernateUtil;
import com.excilys.computerdatabase.computerdb.model.entities.Page.BuilderPage;

@Repository
public class CompanyDao implements ICompanyDAO {
    
    
    /*private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
     
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    private String companyTable;
    private String companyId;
    private String companyName;
    private String countTotal;

    private final String SELECT_COMPANY_BY_ID;
    private final String SELECT_COMPANY_BY_NAME;
    private final String SELECT_ALL_COMPANY_WITH_LIMIT;
    private final String SELECT_ALL_COMPANY;
    private final String COUNT_COMPANY;
    private final String COUNT_COMPANY_BY_NAME;
    private final String DELETE_COMPANY;

    @Autowired
    MyDataSource dataSource;

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(MyDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    
    public CompanyDao() {
        jdbcTemplate = new JdbcTemplate();

        try {
            Configuration config = new PropertiesConfiguration("query.properties");
            companyTable = config.getString("CompanyTable");
            companyId = config.getString("CompanyId");
            companyName = config.getString("CompanyName");
            countTotal = config.getString("CountTotal");
        } catch (ConfigurationException ce) {
            ce.printStackTrace();
        }

        SELECT_COMPANY_BY_ID = "SELECT * FROM " + companyTable + " WHERE " + companyId + " = ?";

        SELECT_COMPANY_BY_NAME = "SELECT * FROM " + companyTable + " WHERE " + companyName + " = ? LIMIT ?, ?";

        SELECT_ALL_COMPANY_WITH_LIMIT = "SELECT * FROM " + companyTable + " LIMIT ?, ?";

        SELECT_ALL_COMPANY = "SELECT * FROM " + companyTable;

        COUNT_COMPANY = "SELECT count(*) as " + countTotal + " FROM " + companyTable;

        COUNT_COMPANY_BY_NAME = "SELECT count(*) as " + countTotal + " FROM company WHERE UPPER(" + companyName
                + ") LIKE UPPER(?)";

        DELETE_COMPANY = "DELETE FROM " + companyTable + " WHERE " + companyId + "=?;";

    }

    @Override
    public Optional<Company> getCompanyById(long id) throws DaoException {
        Optional<Company> optionalCompany = Optional.empty();
        if (!ControllerDAOCompany.CONTROLLER_DAO_COMPANY.checkId(id)) {
            LOGGER.error("Id non valide : " + id);
            return optionalCompany;
        }
        Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from company where id = :id ");
        
        
        
        
        Company company = jdbcTemplate.queryForObject(SELECT_COMPANY_BY_ID, new Object[] { id },
                new RowMapper<Company>() {
                    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Company company = new Company.CompanyBuilder(rs.getString("name")).id(rs.getLong("id")).build();
                        return company;
                    }
                });
        optionalCompany = Optional.ofNullable(company);
        return optionalCompany;

    }

    @Override
    public Optional<Page> getCompanyByName(String name, long limitStart, long size) throws DaoException {

        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage(name, "name", limitStart, size);

        List<Company> list = jdbcTemplate.query(SELECT_COMPANY_BY_NAME, new Object[] { name, limitStart, size },
                new RowMapper<Company>() {
                    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Company company = new Company.CompanyBuilder(rs.getString("name")).id(rs.getLong("id")).build();
                        return company;
                    }
                });
        List<Pageable> list2 = new ArrayList<>();
        for (Company c : list) {
            list2.add(c);
        }
        builderPage.list(list2);
        builderPage.totalRow(getNumberOfCompany());
        optionalPage = Optional.of(builderPage.build());

        return optionalPage;
    }

    @Override
    public Optional<Page> getCompanys(long limitStart, long size) throws DaoException {

        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage("", "name", limitStart, size);

        List<Company> list = jdbcTemplate.query(SELECT_ALL_COMPANY_WITH_LIMIT, new Object[] { limitStart, size },
                new RowMapper<Company>() {
                    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Company company = new Company.CompanyBuilder(rs.getString("name")).id(rs.getLong("id")).build();
                        return company;
                    }
                });
        List<Pageable> list2 = new ArrayList<>();
        for (Company c : list) {
            list2.add(c);
        }
        builderPage.list(list2);
        builderPage.totalRow(getNumberOfCompany());
        optionalPage = Optional.of(builderPage.build());

        return optionalPage;
    }

    @Override
    public Optional<Page> getCompanys() throws DaoException {

        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage("", "name", -1, -1);

        List<Company> list = jdbcTemplate.query(SELECT_ALL_COMPANY, new RowMapper<Company>() {
            public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
                Company company = new Company.CompanyBuilder(rs.getString("name")).id(rs.getLong("id")).build();
                return company;
            }
        });
        List<Pageable> list2 = new ArrayList<>();
        for (Company c : list) {
            list2.add(c);
        }
        builderPage.list(list2);
        builderPage.totalRow(getNumberOfCompany());
        optionalPage = Optional.of(builderPage.build());

        return optionalPage;
    }

    @Override
    public long getNumberOfCompany() throws DaoException {
        return jdbcTemplate.queryForObject(COUNT_COMPANY, Long.class);
    }

    @Override
    public long getNumberOfCompany(String name) throws DaoException {
        long number = 0;
        if (ControllerDAOCompany.CONTROLLER_DAO_COMPANY.isValideName(name)) {
            LOGGER.error("Name non valide : '" + name + "'");
            return number;
        }
        return jdbcTemplate.queryForObject(COUNT_COMPANY_BY_NAME, Long.class, name);

    }

    @Override
    public boolean deleteCompany(Company company) throws DaoException {
        int result;
        if (!ControllerDAOCompany.CONTROLLER_DAO_COMPANY.isValide(company)) {
            LOGGER.error("Company non valide : '" + company + "'");
            return false;
        }

        result = jdbcTemplate.queryForObject(DELETE_COMPANY, Integer.class);
        /*
         * try (PreparedStatement deleteStatment =
         * connection.prepareStatement(DELETE_COMPANY);) {
         * deleteStatment.setLong(1, company.getId()); result =
         * deleteStatment.executeUpdate(); connection.commit(); } catch
         * (SQLException e) {
         * 
         * LOGGER.error("deleteCompany : " + e.getMessage()); //
         * databaseManager.rollback(); throw new DaoException(e.getMessage()); }
         * finally { // databaseManager.closeConnection(); }
         */
        return result == 1;
    }

}