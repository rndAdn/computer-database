package com.excilys.computerdatabase.computerdb.database;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.Company;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public class ComputerDao implements IComputerDAO {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("com.excilys.computerdatabase.computerdb.database.ComputerDao");

    @Override
    public Optional<Computer> getComputerById(long id) throws DaoException {
        PreparedStatement selectStatement;
        Optional<Computer> optionalComputer = Optional.empty();
        Connection connection = Database.INSTANCE.getConnection();

        try {
            selectStatement = connection.prepareStatement(SELECT_COMPUTER_BY_ID);

            selectStatement.setLong(1, id);

            ResultSet rset = selectStatement.executeQuery();

            if (rset.next()) {
                optionalComputer = Optional.of(mapComputer(rset));
            }

        } catch (SQLException e) {
            LOGGER.error("getComputerById : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection();
        }
        LOGGER.info("getComputerById : " + optionalComputer);
        return optionalComputer;
    }

    @Override
    public List<Computer> getComputersByName(String name, int limitStart, int size) throws DaoException {
        PreparedStatement selectStatement;
        List<Computer> result = new ArrayList<>();
        Connection connection = Database.INSTANCE.getConnection();

        try {
            selectStatement = connection.prepareStatement(SELECT_COMPUTER_BY_NAME);

            selectStatement.setString(1, name);
            selectStatement.setInt(2, limitStart);
            selectStatement.setInt(3, size);

            ResultSet rset = null;
            rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(mapComputer(rset));
            }

        } catch (SQLException e) {
            LOGGER.error("getComputersByName : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection();
        }
        LOGGER.info("getComputersByName result size : " + result.size());
        return result;
    }

    @Override
    public List<Pageable> getComputers(int limitStart, int size) throws DaoException {

        PreparedStatement selectStatement;
        List<Pageable> result = new ArrayList<>();
        Connection connection = Database.INSTANCE.getConnection();

        try {
            selectStatement = connection.prepareStatement(SELECT_ALL_COMPUTERS_WITH_LIMIT);

            selectStatement.setInt(1, limitStart);
            selectStatement.setInt(2, size);

            ResultSet rset = null;
            rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(mapComputer(rset));
            }

        } catch (SQLException e) {
            LOGGER.error("getComputers : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection();
        }
        LOGGER.info("getComputers result size : " + result.size());
        return result;
    }

    @Override
    public boolean deleteComputer(Computer computer) throws DaoException {
        int result = -1;
        Connection connection = Database.INSTANCE.getConnection();
        try {
            PreparedStatement deleteStatment = connection.prepareStatement(DELETE_COMPUTER);

            deleteStatment.setLong(1, computer.getId());
            deleteStatment.executeUpdate();
        } catch (SQLException e) {

            LOGGER.error("deleteComputer : " + e.getMessage());
            Database.INSTANCE.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection();
        }
        LOGGER.info("deleteComputer : " + (result == 1));
        return result == 1;
    }

    @Override
    public boolean updateComputer(Computer computer) throws DaoException {
        int result = -1;
        Connection connection = Database.INSTANCE.getConnection();
        try {
            PreparedStatement updateStatment = connection.prepareStatement(UPDATE_COMPUTER);
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

            updateStatment.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("updateComputer : " + e.getMessage());
            Database.INSTANCE.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection();
        }
        LOGGER.info("updateComputer : " + (result == 1));
        return result == 1;
    }

    @Override
    public boolean insertComputer(Computer computer) throws DaoException {
        int result = -1;
        Connection connection = Database.INSTANCE.getConnection();
        try {

            PreparedStatement insertStatment = connection.prepareStatement(INSERT_COMPUTER);

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
        } catch (SQLException e) {
            LOGGER.error("insertComputer : " + e.getMessage());
            Database.INSTANCE.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection();
        }
        LOGGER.info("insertComputer : " + (result == 1));
        return result == 1;
    }

    @Override
    public long countComputers() throws DaoException {
        long number = 0;
        Connection connection = Database.INSTANCE.getConnection();
        try {
            Statement st = connection.createStatement();

            ResultSet rset = null;
            rset = st.executeQuery(COUNT_COMPUTERS);
            if (rset.next()) {
                number = rset.getLong(COUNT_TOTAL_COLUMN_NAME);
            }
        } catch (SQLException e) {
            LOGGER.error("countComputers : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection();
        }
        LOGGER.info("countComputers : " + number);
        return number;
    }

    private Computer mapComputer(ResultSet rset) throws SQLException {
        Computer computer = new Computer();
        computer.setId(rset.getLong("id"));
        computer.setName(rset.getString("name"));
        try {
            computer.setDateIntroduced(rset.getObject("introduced", LocalDate.class));
            computer.setDateDiscontinued(rset.getObject("discontinued", LocalDate.class));
        } catch (NullPointerException e) {
            LOGGER.warn("mapComputer date null dans la bd get");
        }
        Company company = new Company();
        long companyId = rset.getLong("company_id");
        if (!rset.wasNull()) {
            company.setId(companyId);
            company.setName(rset.getString("company_name"));

        }
        computer.setCompagny(company);
        return computer;
    }
}
