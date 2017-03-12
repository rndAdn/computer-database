package com.excilys.computerdatabase.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.dao.controller.ControllerDAOComputer;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import com.excilys.computerdatabase.computerdb.dao.mapper.MapperComputer;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public enum ComputerDao {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory
            .getLogger(ComputerDao.class);

    private final String SELECT_COMPUTER_BY_ID;
    private final String SELECT_COMPUTER_BY_NAME;
    private final String SELECT_ALL_COMPUTERS_WITH_LIMIT;
    private final String DELETE_COMPUTER;
    private final String DELETE_COMPUTERS;
    private final String INSERT_COMPUTER;
    private final String UPDATE_COMPUTER;
    private final String COUNT_COMPUTERS;
    private final String COUNT_COMPUTERS_BY_NAME;

    private String computerTable;
    private String computerId;
    private String computerName;
    private String computerDateIntro;
    private String computerDateFin;
    private String computerCompanyId;
    private String computerCompanyName;
    private String countTotal;
    private String companyTable;
    private String companyId;
    private String companyName;

    /**
     *
     */
    ComputerDao() {
        try {
            Configuration config = new PropertiesConfiguration("query.properties");
            computerTable = config.getString("ComputerTable");
            computerId = config.getString("ComputerId");
            computerName = config.getString("ComputerName");
            computerDateIntro = config.getString("ComputerDateIntro");
            computerDateFin = config.getString("ComputerDateFin");
            computerCompanyId = config.getString("ComputerCompanyId");
            computerCompanyName = config.getString("ComputerCompanyName");
            countTotal = config.getString("CountTotal");

            companyTable = config.getString("CompanyTable");
            companyId = config.getString("CompanyId");
            companyName = config.getString("CompanyName");
        } catch (ConfigurationException ce) {
            ce.printStackTrace();
        }

        SELECT_COMPUTER_BY_ID = "SELECT " + computerId + ", " + computerName + ", " + computerDateIntro + ", "
                + computerDateFin + ", " + computerCompanyId + ", " + companyName + " as " + computerCompanyName
                + " FROM " + computerTable + " LEFT JOIN " + companyTable + " ON " + computerCompanyId + " = "
                + companyId + " WHERE " + computerId + " = ? ORDER BY " + computerName;


        SELECT_COMPUTER_BY_NAME = "SELECT " + computerId + ", " + computerName + ", " + computerDateIntro + ", "
                + computerDateFin + ", " + computerCompanyId + ", " + companyName + " as " + computerCompanyName
                + " FROM " + computerTable + " LEFT JOIN " + companyTable + " ON " + computerCompanyId + " = "
                + companyId + " WHERE UPPER(" + computerName + ") LIKE UPPER(?) OR UPPER(" + companyName
                + ") LIKE UPPER(?) ORDER BY " + computerName + " LIMIT ?, ? ";


        SELECT_ALL_COMPUTERS_WITH_LIMIT = "SELECT " + computerId + ", " + computerName + ", " + computerDateIntro + ", "
                + computerDateFin + ", " + computerCompanyId + ", " + companyName + " as " + computerCompanyName
                + " FROM " + computerTable + " LEFT JOIN " + companyTable + " ON " + computerCompanyId + " = "
                + companyId + " ORDER BY " + computerName + " LIMIT ?, ? ";
        DELETE_COMPUTER = "DELETE FROM " + computerTable + " WHERE id=?;";
        DELETE_COMPUTERS = "DELETE FROM " + computerTable + " WHERE " + computerCompanyId + "=?;";
        INSERT_COMPUTER = "INSERT into " + computerTable + " (" + computerName + "," + computerDateIntro + ","
                + computerDateFin + "," + computerCompanyId + ") values (?,?,?,?);";
        UPDATE_COMPUTER = "UPDATE " + computerTable + " SET " + computerName + "=?, " + computerDateIntro + "=?, "
                + computerDateFin + "=?, " + computerCompanyId + "=? WHERE " + computerId + "=?;";
        COUNT_COMPUTERS = "SELECT count(" + computerId + ") as " + countTotal + " FROM " + computerTable;
        COUNT_COMPUTERS_BY_NAME = "SELECT count(" + computerId + ") as " + countTotal + " FROM " + computerTable
                + " LEFT JOIN " + companyTable + " ON " + computerCompanyId + " = "
                + companyId + " WHERE UPPER(" + computerName + ") LIKE UPPER(?) OR UPPER(" + companyName + ") LIKE UPPER(?)";

    }

    /**
     * Get a Computer from database by it's id.
     *
     * @param id Computer id in DatabaseManager.
     * @return A Optional Computer. empty if the Computer doesn't exist in the
     * database.
     * @throws DaoException .
     */
    public Optional<Computer> getComputerById(long id) throws DaoException {
        Optional<Computer> optionalComputer = Optional.empty();
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.checkId(id)) {
            LOGGER.error("Id non valide : " + id);
            return optionalComputer;
        }
        try (
                Connection connection = DatabaseManager.INSTANCE.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(SELECT_COMPUTER_BY_ID)
        ) {

            selectStatement.setLong(1, id);

            ResultSet rset = selectStatement.executeQuery();

            if (rset.next()) {
                optionalComputer = Optional.of(MapperComputer.mapComputer(rset));
            }
            connection.commit();
            rset.close();
        } catch (SQLException e) {
            LOGGER.error("getComputerById : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        return optionalComputer;
    }

    /**
     * Find all Computer from database by name.
     *
     * @param name       Of Computer(s) to find
     * @param limitStart Start of first result.
     * @param size       Max list size
     * @return a List Pageable
     * @throws DaoException .
     */
    public List<Pageable> getComputersByName(String name, long limitStart, long size) throws DaoException {
        List<Pageable> result = new ArrayList<>();
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValideName(name)) {
            LOGGER.error("Name non valide : " + name);
            return result;
        }
        try (
                Connection connection = DatabaseManager.INSTANCE.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(SELECT_COMPUTER_BY_NAME)
        ) {
            selectStatement.setString(1, name + "%");
            selectStatement.setString(2, name + "%");
            selectStatement.setLong(3, limitStart);
            selectStatement.setLong(4, size);

            ResultSet rset;
            rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperComputer.mapComputer(rset));
            }
            connection.commit();
            rset.close();
        } catch (SQLException e) {
            LOGGER.error("getComputersByName : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    /**
     * Get all Computer from database.
     *
     * @param limitStart Start of first result.
     * @param size       Max list size
     * @return a List Pageable
     * @throws DaoException .
     */
    public List<Pageable> getComputers(long limitStart, long size) throws DaoException {
        List<Pageable> result = new ArrayList<>();
        try (
                Connection connection = DatabaseManager.INSTANCE.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(SELECT_ALL_COMPUTERS_WITH_LIMIT)
        ) {
            selectStatement.setLong(1, limitStart);
            selectStatement.setLong(2, size);
            ResultSet rset;
            rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperComputer.mapComputer(rset));
            }
            connection.commit();
            rset.close();
        } catch (SQLException e) {
            LOGGER.error("getComputers : " + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    /**
     * Delete a Computer in database given a Computer.
     *
     * @param computer Representation of the computer to delete
     * @return true if computer is delete false otherwise
     * @throws DaoException .
     */
    public boolean deleteComputer(Computer computer) throws DaoException {
        int result = -1;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValide(computer)) {
            LOGGER.error("Computer non valide : " + computer);
            return false;
        }
        long id = computer.getId();
        try (
                Connection connection = DatabaseManager.INSTANCE.getConnection();
                PreparedStatement deleteStatment = connection.prepareStatement(DELETE_COMPUTER)
        ) {
            deleteStatment.setLong(1, id);
            result = deleteStatment.executeUpdate();

            connection.commit();
        } catch (SQLException e) {

            LOGGER.error("deleteComputer : " + e.getMessage());
            DatabaseManager.INSTANCE.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            DatabaseManager.INSTANCE.closeConnection();
        }

        if (result != 1) {
            LOGGER.info("deleteComputer False id : " + id + " result : " + result);
        }
        return result == 1;
    }

    /**
     * Update a Computer in database given a Computer.
     *
     * @param computer Representation of the computer to update
     * @return true if the computer is update in database
     * @throws DaoException .
     */
    public boolean updateComputer(Computer computer) throws DaoException {
        int result = -1;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValide(computer)) {
            LOGGER.error("Computer non valide : " + computer);
            return false;
        }
        try (
                Connection connection = DatabaseManager.INSTANCE.getConnection();
                PreparedStatement updateStatment = connection.prepareStatement(UPDATE_COMPUTER)
        ) {
            updateStatment.setString(1, computer.getName());

            Optional<LocalDate> dateIntro = computer.getDateIntroduced();
            if (dateIntro.isPresent()) {
                updateStatment.setObject(2, dateIntro.get());
            } else {
                updateStatment.setObject(2, Types.NULL);
            }

            Optional<LocalDate> dateFin = computer.getDateDiscontinued();
            if (dateFin.isPresent()) {
                updateStatment.setObject(3, dateIntro.get());
            } else {
                updateStatment.setNull(3, Types.NULL);
            }

            Long companyId = computer.getCompanyId();

            if (companyId == null) {
                updateStatment.setNull(4, java.sql.Types.INTEGER);
            } else {
                updateStatment.setLong(4, companyId);
            }

            updateStatment.setLong(5, computer.getId());

            result = updateStatment.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("updateComputer : " + e.getMessage());
            DatabaseManager.INSTANCE.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            DatabaseManager.INSTANCE.closeConnection();
        }
        if (result != 1) {
            LOGGER.info("updateComputer False id : " + computer.getId() + " result : " + result);
        }
        return result == 1;
    }

    /**
     * Insert a Computer in database given a Computer.
     *
     * @param computer Representation of the computer to create
     * @return true if the computer is created in database
     * @throws DaoException .
     */
    public boolean insertComputer(Computer computer) throws DaoException {
        int result = -1;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValide(computer)) {
            LOGGER.error("Computer non valide : " + computer);
            return false;
        }
        try (
                Connection connection = DatabaseManager.INSTANCE.getConnection();
                PreparedStatement insertStatment = connection.prepareStatement(INSERT_COMPUTER)
        ) {
            insertStatment.setString(1, computer.getName());

            Optional<LocalDate> dateIntro = computer.getDateIntroduced();
            if (dateIntro.isPresent()) {
                insertStatment.setObject(2, dateIntro.get());
            } else {
                insertStatment.setNull(2, Types.NULL);
            }

            Optional<LocalDate> dateFin = computer.getDateDiscontinued();
            if (dateFin.isPresent()) {
                insertStatment.setObject(3, dateIntro.get());
            } else {
                insertStatment.setNull(3, Types.NULL);
            }

            Long companyId = computer.getCompanyId();

            if (companyId == null) {
                insertStatment.setNull(4, java.sql.Types.INTEGER);
            } else {
                insertStatment.setLong(4, companyId);
            }

            result = insertStatment.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("insertComputer : " + e.getMessage());
            DatabaseManager.INSTANCE.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            DatabaseManager.INSTANCE.closeConnection();
        }
        if (result != 1) {
            LOGGER.info("insertComputer False id : " + computer.getId() + " result : " + result);
        }
        return result == 1;
    }

    /**
     * Get number of Computer in database.
     *
     * @return Total number of Computer in the database.
     * @throws DaoException .
     */
    public long countComputers() throws DaoException {
        long number = 0;
        try (
                Connection connection = DatabaseManager.INSTANCE.getConnection();
                Statement st = connection.createStatement()
        ) {
            ResultSet rset;
            rset = st.executeQuery(COUNT_COMPUTERS);
            if (rset.next()) {
                number = rset.getLong(countTotal);
            }
            connection.commit();
            st.close();
        } catch (SQLException e) {
            LOGGER.error("countComputers : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            DatabaseManager.INSTANCE.closeConnection();
        }
        return number;
    }


    public long countComputersWithName(String name) throws DaoException {
        long number = 0;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValideName(name)) {
            LOGGER.error("Name non valide : " + name);
            return 0;
        }
        try (
                Connection connection = DatabaseManager.INSTANCE.getConnection();
                PreparedStatement st = connection.prepareStatement(COUNT_COMPUTERS_BY_NAME)
        ) {
            st.setString(1, name + "%");
            st.setString(2, name + "%");
            ResultSet rset;
            rset = st.executeQuery();
            if (rset.next()) {
                number = rset.getLong(countTotal);
            }
            connection.commit();
            rset.close();
        } catch (SQLException e) {
            LOGGER.error("countComputers : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            DatabaseManager.INSTANCE.closeConnection();
        }
        return number;
    }

}