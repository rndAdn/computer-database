package com.excilys.computerdatabase.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.excilys.computerdatabase.computerdb.dao.controller.ControllerDAOCompany;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Page;
import com.excilys.computerdatabase.computerdb.model.entities.Pageable;
import com.sun.tracing.dtrace.Attributes;
import com.excilys.computerdatabase.computerdb.model.entities.Page.BuilderPage;
import com.excilys.computerdatabase.computerdb.dao.mapper.MapperCompany;

@Repository
public class CompanyDao implements ICompanyDAO {
    


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
    DatabaseManager databaseManager;
    
    public CompanyDao() {
        
        
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

        COUNT_COMPANY = "SELECT count(" + companyId + ") as " + countTotal + " FROM " + companyTable;

        COUNT_COMPANY_BY_NAME = "SELECT count(" + companyId + ") as " + countTotal + " FROM company WHERE UPPER("
                + companyName + ") LIKE UPPER(?)";

        DELETE_COMPANY = "DELETE FROM " + companyTable + " WHERE " + companyId + "=?;";

    }

    @Override
    public Optional<Company> getCompanyById(long id) throws DaoException {
        Optional<Company> optionalCompany = Optional.empty();
        if (!ControllerDAOCompany.CONTROLLER_DAO_COMPANY.checkId(id)) {
            LOGGER.error("Id non valide : " + id);
            return optionalCompany;
        }
        //Company company = (Company) jdbcTemplate.queryForObject(SELECT_COMPANY_BY_ID, new Object[] { id }, new BeanPropertyRowMapper(Company.class));
        //optionalCompany = Optional.ofNullable(company);
        try (
                
                PreparedStatement selectStatement = databaseManager.getConnection().prepareStatement(SELECT_COMPANY_BY_ID)
        ) {
            selectStatement.setLong(1, id);

            ResultSet rset = selectStatement.executeQuery();
            if (rset.next()) {
                optionalCompany = Optional.of(MapperCompany.mapCompany(rset));
            }

            
            rset.close();
            
            
        } catch (SQLException e) {
            LOGGER.error("getCompanyById : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        return optionalCompany;

    }

    @Override
    public Optional<Page> getCompanyByName(Connection connection, String name, long limitStart, long size) throws DaoException {
        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage(name, "name", limitStart, size);
        List<Pageable> result = new ArrayList<>();

        if (ControllerDAOCompany.CONTROLLER_DAO_COMPANY.isValideName(name)) {
            LOGGER.error("Name non valide : '" + name + "'");
            return optionalPage;
        }

        try (
                PreparedStatement selectStatement = connection.prepareStatement(SELECT_COMPANY_BY_NAME)
        ) {
            selectStatement.setString(1, name + "%");
            selectStatement.setLong(2, limitStart);
            selectStatement.setLong(3, size);

            ResultSet rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperCompany.mapCompany(rset));
            }
            rset.close();
        } catch (SQLException e) {
            LOGGER.error("getCompanyByName : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        builderPage.list(result);
        builderPage.totalRow(getNumberOfCompany(connection, name));
        optionalPage = Optional.of(builderPage.build());
        return optionalPage;
    }

    @Override
    public Optional<Page> getCompanys(Connection connection, long limitStart, long size) throws DaoException {
        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage("", "name", limitStart, size);
        List<Pageable> result = new ArrayList<>();
        try (
                PreparedStatement selectStatement = connection.prepareStatement(SELECT_ALL_COMPANY_WITH_LIMIT)
        ) {
            selectStatement.setLong(1, limitStart);
            selectStatement.setLong(2, size);

            ResultSet rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperCompany.mapCompany(rset));
            }
            connection.commit();
            rset.close();
        } catch (SQLException e) {
            LOGGER.error("getCompanys : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        builderPage.list(result);
        builderPage.totalRow(getNumberOfCompany(connection));
        optionalPage = Optional.of(builderPage.build());
        return optionalPage;
    }
    
    @Override
    public Optional<Page> getCompanys(Connection connection) throws DaoException {
        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage("", "name", -1, -1);
        List<Pageable> result = new ArrayList<>();
        try (
                PreparedStatement selectStatement = connection.prepareStatement(SELECT_ALL_COMPANY)
        ) {

            ResultSet rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperCompany.mapCompany(rset));
            }
            connection.commit();
            rset.close();
        } catch (SQLException e) {
            LOGGER.error("getCompanys : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        builderPage.list(result);
        builderPage.totalRow(getNumberOfCompany(connection));
        optionalPage = Optional.of(builderPage.build());
        return optionalPage;
    }

    @Override
    public long getNumberOfCompany(Connection connection) throws DaoException {
        long number = 0;
        try (
                Statement st = connection.createStatement()
        ) {

            ResultSet rset;
            rset = st.executeQuery(COUNT_COMPANY);
            if (rset.next()) {
                number = rset.getLong(countTotal);
            }
            connection.commit();
            rset.close();
        } catch (SQLException e1) {
            throw new DaoException(e1.getMessage());
        }
        return number;
    }

    @Override
    public long getNumberOfCompany(Connection connection, String name) throws DaoException {
        long number = 0;
        if (ControllerDAOCompany.CONTROLLER_DAO_COMPANY.isValideName(name)) {
            LOGGER.error("Name non valide : '" + name + "'");
            return number;
        }

        try (
                PreparedStatement st = connection.prepareStatement(COUNT_COMPANY_BY_NAME)
        ) {
            st.setString(1, name + "%");
            ResultSet rset = null;
            rset = st.executeQuery();
            if (rset.next()) {
                number = rset.getLong(countTotal);
            }
            connection.commit();
            rset.close();
        } catch (SQLException e1) {
            throw new DaoException(e1.getMessage());
        }
        return number;
    }


    @Override
    public boolean deleteCompany(Connection connection, Company company) throws DaoException {
        int result;
        if (!ControllerDAOCompany.CONTROLLER_DAO_COMPANY.isValide(company)) {
            LOGGER.error("Company non valide : '" + company + "'");
            return false;
        }
        try (
                PreparedStatement deleteStatment = connection.prepareStatement(DELETE_COMPANY);
        ) {
            deleteStatment.setLong(1, company.getId());
            result = deleteStatment.executeUpdate();
            connection.commit();
        } catch (SQLException e) {

            LOGGER.error("deleteCompany : " + e.getMessage());
            //databaseManager.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            //databaseManager.closeConnection();
        }
        return result == 1;
    }

}