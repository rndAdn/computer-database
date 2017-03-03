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

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public enum ComputerDao {

    INSTANCE;

    private static final String SELECT_COMPUTER_BY_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE c.id = ?";
    private static final String SELECT_COMPUTER_BY_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE UPPER(c.name) LIKE UPPER(?) LIMIT ?, ?";
    private static final String SELECT_ALL_COMPUTERS_WITH_LIMIT = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id LIMIT ?, ?";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
    private static final String INSERT_COMPUTER = "INSERT into computer (name,introduced,discontinued,company_id) values (?,?,?,?);";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
    private static final String COUNT_TOTAL_COLUMN_NAME = "total";
    private static final String COUNT_COMPUTERS = "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM computer";
    private static final String COUNT_COMPUTERS_BY_NAME = "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM computer WHERE UPPER(name) LIKE UPPER(?)";
    private static final Logger LOGGER = LoggerFactory
            .getLogger("com.excilys.computerdatabase.computerdb.database.ComputerDao");

    /**
     * Get a Computer from database by it's id.
     *
     * @param id
     *            Computer id in Database.
     * @return A Optional Computer. empty if the Computer doesn't exist in the
     *         database.
     * @throws DaoException
     *             .
     */
    public Optional<Computer> getComputerById(long id) throws DaoException {
        PreparedStatement selectStatement;
        Optional<Computer> optionalComputer = Optional.empty();
        Connection connection = Database.INSTANCE.getConnection();

        try {
            selectStatement = connection.prepareStatement(SELECT_COMPUTER_BY_ID);

            selectStatement.setLong(1, id);

            ResultSet rset = selectStatement.executeQuery();

            if (rset.next()) {
                optionalComputer = Optional.of(MapperComputer.mapComputer(rset));
            }
            rset.close();
            selectStatement.close();
        } catch (SQLException e) {
            LOGGER.error("getComputerById : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection(connection);
        }
        LOGGER.info("getComputerById : " + optionalComputer);
        return optionalComputer;
    }

    /**
     * Find all Computer from database by name.
     *
     * @param name
     *            Of Computer(s) to find
     * @param limitStart
     *            Start of first result.
     * @param size
     *            Max list size
     * @return a List Pageable
     * @throws DaoException
     *             .
     */
    public List<Pageable> getComputersByName(String name, long limitStart, long size) throws DaoException {
        PreparedStatement selectStatement;
        List<Pageable> result = new ArrayList<>();
        Connection connection = Database.INSTANCE.getConnection();

        try {
            selectStatement = connection.prepareStatement(SELECT_COMPUTER_BY_NAME);

            selectStatement.setString(1, "%" + name + "%");
            selectStatement.setLong(2, limitStart);
            selectStatement.setLong(3, size);

            ResultSet rset = null;
            rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperComputer.mapComputer(rset));
            }
            rset.close();
            selectStatement.close();
        } catch (SQLException e) {
            LOGGER.error("getComputersByName : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection(connection);
        }
        LOGGER.info("getComputersByName result size : " + result.size());
        return result;
    }

    /**
     * Get all Computer from database.
     *
     * @param limitStart
     *            Start of first result.
     * @param size
     *            Max list size
     * @return a List Pageable
     * @throws DaoException
     *             .
     */
    public List<Pageable> getComputers(long limitStart, long size) throws DaoException {

        PreparedStatement selectStatement;
        List<Pageable> result = new ArrayList<>();
        Connection connection = Database.INSTANCE.getConnection();

        try {
            selectStatement = connection.prepareStatement(SELECT_ALL_COMPUTERS_WITH_LIMIT);

            selectStatement.setLong(1, limitStart);
            selectStatement.setLong(2, size);

            ResultSet rset = null;
            rset = selectStatement.executeQuery();

            while (rset.next()) {
                result.add(MapperComputer.mapComputer(rset));
            }
            rset.close();
            selectStatement.close();
        } catch (SQLException e) {
            LOGGER.error("getComputers : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection(connection);
        }
        LOGGER.info("getComputers result size : " + result.size());
        return result;
    }

    /**
     * Delete a Computer in database given a Computer.
     *
     * @param computer
     *            Representation of the computer to delete
     * @return true if computer is delete false otherwise
     * @throws DaoException
     *             .
     */
    public boolean deleteComputer(Computer computer) throws DaoException {
        int result = -1;
        Connection connection = Database.INSTANCE.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement deleteStatment = connection.prepareStatement(DELETE_COMPUTER);

            deleteStatment.setLong(1, computer.getId());
            deleteStatment.executeUpdate();
            connection.commit();
            deleteStatment.close();
        } catch (SQLException e) {

            LOGGER.error("deleteComputer : " + e.getMessage());
            Database.INSTANCE.rollback(connection);
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection(connection);
        }
        LOGGER.info("deleteComputer : " + (result == 1));
        return result == 1;
    }

    /**
     * Update a Computer in database given a Computer.
     *
     * @param computer
     *            Representation of the computer to update
     * @return true if the computer is update in database
     * @throws DaoException
     *             .
     */
    public boolean updateComputer(Computer computer) throws DaoException {
        int result = -1;
        Connection connection = Database.INSTANCE.getConnection();
        try {
            connection.setAutoCommit(false);
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

            String SELECT_COMPUTER_BY_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE c.id = ?";
            String SELECT_COMPUTER_BY_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id WHERE UPPER(c.name) LIKE UPPER(?) LIMIT ?, ?";
            String SELECT_ALL_COMPUTERS_WITH_LIMIT = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, comp.name as company_name FROM computer AS c LEFT JOIN company as comp ON c.company_id = comp.id LIMIT ?, ?";
            String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
            String INSERT_COMPUTER = "INSERT into computer (name,introduced,discontinued,company_id) values (?,?,?,?);";
            String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
            String COUNT_TOTAL_COLUMN_NAME = "total";
            String COUNT_COMPUTERS = "SELECT count(id) as " + COUNT_TOTAL_COLUMN_NAME + " FROM computer";
            if (companyId == null) {
                updateStatment.setNull(4, java.sql.Types.INTEGER);
            } else {
                updateStatment.setLong(4, companyId);
            }

            updateStatment.setLong(5, computer.getId());

            updateStatment.executeUpdate();
            connection.commit();
            updateStatment.close();
        } catch (SQLException e) {
            LOGGER.error("updateComputer : " + e.getMessage());
            Database.INSTANCE.rollback(connection);
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection(connection);
        }
        LOGGER.info("updateComputer : " + (result == 1));
        return result == 1;
    }

    /**
     * Insert a Computer in database given a Computer.
     *
     * @param computer
     *            Representation of the computer to create
     * @return true if the computer is created in database
     * @throws DaoException
     *             .
     */
    public boolean insertComputer(Computer computer) throws DaoException {
        int result = -1;
        Connection connection = Database.INSTANCE.getConnection();
        try {
            connection.setAutoCommit(false);
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
            connection.commit();
            insertStatment.close();
        } catch (SQLException e) {
            LOGGER.error("insertComputer : " + e.getMessage());
            Database.INSTANCE.rollback(connection);
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection(connection);
        }
        LOGGER.info("insertComputer : " + (result == 1));
        return result == 1;
    }

    /**
     * Get number of Computer in database.
     *
     * @return Total number of Computer in the database.
     * @throws DaoException
     *             .
     */
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
            rset.close();
            st.close();
        } catch (SQLException e) {
            LOGGER.error("countComputers : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection(connection);
        }
        LOGGER.info("countComputers : " + number);
        return number;
    }
    
    public long countComputersWithName(String name) throws DaoException {
        long number = 0;
        Connection connection = Database.INSTANCE.getConnection();
        try {
            PreparedStatement st = connection.prepareStatement(COUNT_COMPUTERS_BY_NAME);
            st.setString(1, "%" + name + "%");
            ResultSet rset = null;
            rset = st.executeQuery();
            if (rset.next()) {
                number = rset.getLong(COUNT_TOTAL_COLUMN_NAME);
            }
            rset.close();
            st.close();
        } catch (SQLException e) {
            LOGGER.error("countComputers : " + e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            Database.INSTANCE.closeConnection(connection);
        }
        LOGGER.info("countComputers : " + number);
        return number;
    }

}
