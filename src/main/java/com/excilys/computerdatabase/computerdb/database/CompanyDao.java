package com.excilys.computerdatabase.computerdb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.model.Computer;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.mapper.MapperCompany;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public enum CompanyDao {
    INSTANCE;


    private static final Logger LOGGER = LoggerFactory
            .getLogger("com.excilys.computerdatabase.computerdb.database.CompanyDao");
    private String companyTable;
    private String companyId;
    private String companyName;
    private String countTotal;

    private final String SELECT_COMPANY_BY_ID;
    private final String SELECT_COMPANY_BY_NAME;
    private final String SELECT_ALL_COMPANY_WITH_LIMIT;
    private final String COUNT_COMPANY;
    private final String COUNT_COMPANY_BY_NAME;
    private final String DELETE_COMPANY;

    CompanyDao() {
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
        COUNT_COMPANY = "SELECT count(" + companyId + ") as " + countTotal + " FROM " + companyTable;
        COUNT_COMPANY_BY_NAME = "SELECT count(" + companyId + ") as " + countTotal + " FROM company WHERE UPPER("
                + companyName + ") LIKE UPPER(?)";
        DELETE_COMPANY = "DELETE FROM " + companyTable + " WHERE "+ companyId + "=?;";

    }

    /**
     * Get a Company from database by it's id.
     *
     * @param id Company id in Database.
     * @return A Optional<Company>. empty if the Company doesn't exist in the
     * database.
     * @throws DaoException .
     */
    public Optional<Company> getCompanyById(long id) throws DaoException {
        Optional<Company> optionalCompany = Optional.empty();
        PreparedStatement selectStatement;

        try (Connection connection = Database.INSTANCE.getConnection()) {
            selectStatement = connection.prepareStatement(SELECT_COMPANY_BY_ID);

            selectStatement.setLong(1, id);

            ResultSet rset = selectStatement.executeQuery();
            if (rset.next()) {
                optionalCompany = Optional.of(MapperCompany.mapCompany(rset));
            }

            rset.close();
            selectStatement.close();

        } catch (SQLException e) {
            LOGGER.error("getCompanyById : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        //LOGGER.info("getCompanyById result :" + optionalCompany);
        return optionalCompany;

    }

    /**
     * Find all Company from database by name.
     *
     * @param name       Of Company(s) to find
     * @param limitStart Start of first result.
     * @param size       Max list size
     * @return a List<Pageable>
     * @throws DaoException .
     */
    public List<Pageable> getCompanyByName(String name, long limitStart, long size) throws DaoException {
        List<Pageable> result = new ArrayList<>();
        PreparedStatement selectStatement;

        try (Connection connection = Database.INSTANCE.getConnection()) {
            selectStatement = connection.prepareStatement(SELECT_COMPANY_BY_NAME);

            selectStatement.setString(1, name);
            selectStatement.setLong(2, limitStart);
            selectStatement.setLong(3, size);

            ResultSet rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperCompany.mapCompany(rset));
            }
            rset.close();
            selectStatement.close();
        } catch (SQLException e) {
            LOGGER.error("getCompanyByName : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        //LOGGER.info("getCompanyByName result size : " + result.size());
        return result;
    }

    /**
     * Get all Company from database.
     *
     * @param limitStart Start of first result.
     * @param size       Max list size
     * @return a List<Pageable>
     * @throws DaoException .
     */
    public List<Pageable> getCompanys(long limitStart, long size) throws DaoException {

        List<Pageable> result = new ArrayList<>();
        PreparedStatement selectStatement;

        try (Connection connection = Database.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            selectStatement = connection.prepareStatement(SELECT_ALL_COMPANY_WITH_LIMIT);

            selectStatement.setLong(1, limitStart);
            selectStatement.setLong(2, size);

            ResultSet rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperCompany.mapCompany(rset));
            }
            connection.commit();
            rset.close();
            selectStatement.close();
        } catch (SQLException e) {
            LOGGER.error("getCompanys : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        //LOGGER.info("getCompanys result size : " + result.size());
        return result;
    }

    /**
     * Get number of company in database.
     *
     * @return Total number of company in the database.
     * @throws DaoException .
     */
    public long getNumberOfCompany() throws DaoException {
        long number = 0;

        try (Connection connection = Database.INSTANCE.getConnection()) {
            Statement st = connection.createStatement();

            ResultSet rset = null;
            rset = st.executeQuery(COUNT_COMPANY);
            if (rset.next()) {
                number = rset.getLong(countTotal);
            }
            rset.close();
            st.close();
        } catch (SQLException e1) {
            throw new DaoException(e1.getMessage());
        }
        //LOGGER.info("getNumberOfCompany result : " + number);
        return number;
    }

    /**
     * Get number of company in database.
     *
     * @return Total number of company in the database.
     * @throws DaoException .
     */
    public long getNumberOfCompanyByName(String name) throws DaoException {
        long number = 0;

        try (Connection connection = Database.INSTANCE.getConnection()) {
            PreparedStatement st = connection.prepareStatement(COUNT_COMPANY_BY_NAME);
            st.setString(1, "%" + name + "%");
            ResultSet rset = null;
            rset = st.executeQuery();
            if (rset.next()) {
                number = rset.getLong(countTotal);
            }
            rset.close();
            st.close();
        } catch (SQLException e1) {
            throw new DaoException(e1.getMessage());
        }
        //LOGGER.info("getNumberOfCompany result : " + number);
        return number;
    }


    /**
     * Delete a Computer in database given a Computer.
     *
     * @param company Representation of the computer to delete
     * @return true if computer is delete false otherwise
     * @throws DaoException .
     */
    public boolean deleteCompany(Company company) throws DaoException { // TODO : À tester
        int result = -1;
        Connection connection = Database.INSTANCE.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement deleteStatment = connection.prepareStatement(DELETE_COMPANY);

            deleteStatment.setLong(1, company.getId());
            result = deleteStatment.executeUpdate();
            connection.commit();
            deleteStatment.close();
        } catch (SQLException e) {

            LOGGER.error("deleteCompany : " + e.getMessage());
            Database.INSTANCE.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection();
        }
        //LOGGER.info("deleteCompany : " + (result == 1));
        return result == 1;
    }

}