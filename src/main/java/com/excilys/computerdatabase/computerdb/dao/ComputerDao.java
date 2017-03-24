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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import com.excilys.computerdatabase.computerdb.model.entities.Computer.ComputerBuilder;
import com.excilys.computerdatabase.computerdb.model.entities.Page;
import com.excilys.computerdatabase.computerdb.model.entities.Pageable;
import com.excilys.computerdatabase.computerdb.model.entities.Page.BuilderPage;
import com.excilys.computerdatabase.computerdb.dao.mapper.MapperComputer;

@Repository
public class ComputerDao implements IComputerDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

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

    @Autowired
    DatabaseManager databaseManager;

    @Autowired
    MyDataSource dataSource;

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(MyDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ComputerDao() {
        jdbcTemplate = new JdbcTemplate();

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
                + ") LIKE UPPER(?) ORDER BY %s LIMIT ?, ? ";

        SELECT_ALL_COMPUTERS_WITH_LIMIT = "SELECT " + computerId + ", " + computerName + ", " + computerDateIntro + ", "
                + computerDateFin + ", " + computerCompanyId + ", " + companyName + " as " + computerCompanyName
                + " FROM " + computerTable + " LEFT JOIN " + companyTable + " ON " + computerCompanyId + " = "
                + companyId + " ORDER BY %s LIMIT ?, ? ";
        DELETE_COMPUTER = "DELETE FROM " + computerTable + " WHERE id=?;";
        DELETE_COMPUTERS = "DELETE FROM " + computerTable + " WHERE " + computerCompanyId + "=?;";
        INSERT_COMPUTER = "INSERT into " + computerTable + " (" + computerName + "," + computerDateIntro + ","
                + computerDateFin + "," + computerCompanyId + ") values (?,?,?,?);";
        UPDATE_COMPUTER = "UPDATE " + computerTable + " SET " + computerName + "=?, " + computerDateIntro + "=?, "
                + computerDateFin + "=?, " + computerCompanyId + "=? WHERE " + computerId + "=?;";
        COUNT_COMPUTERS = "SELECT count(" + computerId + ") as " + countTotal + " FROM " + computerTable;
        COUNT_COMPUTERS_BY_NAME = "SELECT count(" + computerId + ") as " + countTotal + " FROM " + computerTable
                + " LEFT JOIN " + companyTable + " ON " + computerCompanyId + " = " + companyId + " WHERE UPPER("
                + computerName + ") LIKE UPPER(?) OR UPPER(" + companyName + ") LIKE UPPER(?)";

    }

    @Override
    public Optional<Computer> getComputerById(long id) throws DaoException {
        Optional<Computer> optionalComputer = Optional.empty();
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.checkId(id)) {
            LOGGER.error("Id non valide : " + id);
            return optionalComputer;
        }

        Computer computer = jdbcTemplate.queryForObject(SELECT_COMPUTER_BY_ID, new Object[] { id },
                new RowMapper<Computer>() {
                    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Computer computer = new Computer.ComputerBuilder(rs.getString(computerName))
                                .id(rs.getLong(computerId)).dateIntroduced(rs.getString(computerDateIntro))
                                .dateDiscontinued(rs.getString(computerDateFin)).build();
                        return computer;
                    }
                });
        optionalComputer = Optional.ofNullable(computer);
        return optionalComputer;
    }

    @Override
    public Optional<Page> getComputersByName(String name, long limitStart, long size, String orderby)
            throws DaoException {
        
        
        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage(name, "name", limitStart, size);

        List<Computer> list = jdbcTemplate.query(SELECT_COMPUTER_BY_NAME, new Object[] { name, limitStart, size },
                new RowMapper<Computer>() {
                    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Computer computer = new Computer.ComputerBuilder(rs.getString(computerName))
                                .id(rs.getLong(computerId)).dateIntroduced(rs.getString(computerDateIntro))
                                .dateDiscontinued(rs.getString(computerDateFin)).build();
                        return computer;
                    }
                });
        List<Pageable> list2 = new ArrayList<>();
        for (Computer c : list) {
            list2.add(c);
        }
        builderPage.list(list2);
        builderPage.totalRow(countComputersWithName(name));
        optionalPage = Optional.of(builderPage.build());

        return optionalPage;
    }

    @Override
    public Optional<Page> getComputers(long limitStart, long size, String orderby) throws DaoException {
        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage("", orderby, limitStart, size);
        List<Pageable> result = new ArrayList<>();
        String col = mapColumnOrderBy(orderby);

        String query = String.format(SELECT_ALL_COMPUTERS_WITH_LIMIT, col);
        try (Connection connection = databaseManager.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(query)) {

            selectStatement.setLong(1, (limitStart - 1) * size);
            selectStatement.setLong(2, size);
            LOGGER.error("selectStatement : " + selectStatement.toString());
            ResultSet rset;
            LOGGER.info(selectStatement.toString());
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
        builderPage.list(result);
        builderPage.totalRow(countComputers());
        optionalPage = Optional.of(builderPage.build());
        return optionalPage;
    }

    @Override
    public boolean deleteComputer(Computer computer) throws DaoException {
        int result = -1;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValide(computer)) {
            LOGGER.error("Computer non valide Delete: " + computer);
            return false;
        }
        long id = computer.getId();
        try (Connection connection = databaseManager.getConnection();
                PreparedStatement deleteStatment = connection.prepareStatement(DELETE_COMPUTER)) {
            deleteStatment.setLong(1, id);
            result = deleteStatment.executeUpdate();

            connection.commit();
        } catch (SQLException e) {

            LOGGER.error("deleteComputer : " + e.getMessage());
            databaseManager.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            databaseManager.closeConnection();
        }

        if (result != 1) {
            LOGGER.info("deleteComputer False id : " + id + " result : " + result);
        }
        return result == 1;
    }

    @Override
    public boolean updateComputer(Computer computer) throws DaoException {
        int result = -1;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValide(computer)) {
            LOGGER.error("Computer non valide Update : " + computer.toString2());
            return false;
        }
        try (Connection connection = databaseManager.getConnection();
                PreparedStatement updateStatment = connection.prepareStatement(UPDATE_COMPUTER)) {
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
            databaseManager.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            databaseManager.closeConnection();
        }
        if (result != 1) {
            LOGGER.info("updateComputer False id : " + computer.getId() + " result : " + result);
        }
        return result == 1;
    }

    @Override
    public boolean insertComputer(Computer computer) throws DaoException {
        int result = -1;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValideName(computer.getName())) {
            LOGGER.error("Computer non valide Insert: " + computer);
            return false;
        }
        try (Connection connection = databaseManager.getConnection();
                PreparedStatement insertStatment = connection.prepareStatement(INSERT_COMPUTER)) {
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
            databaseManager.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            databaseManager.closeConnection();
        }
        if (result != 1) {
            LOGGER.info("insertComputer False id : " + computer.getId() + " result : " + result);
        }
        return result == 1;
    }

    @Override
    public long countComputers() throws DaoException {
        long number = 0;
        try (Connection connection = databaseManager.getConnection(); Statement st = connection.createStatement()) {
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
            databaseManager.closeConnection();
        }
        return number;
    }

    @Override
    public long countComputersWithName(String name) throws DaoException {
        long number = 0;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValideName(name)) {
            LOGGER.error("Name non valide : " + name);
            return 0;
        }
        try (Connection connection = databaseManager.getConnection();
                PreparedStatement st = connection.prepareStatement(COUNT_COMPUTERS_BY_NAME)) {
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
            databaseManager.closeConnection();
        }
        return number;
    }

    @Override
    public long deleteComputersCompany(long companyId) throws DaoException {
        int result = -1;

        try (Connection connection = databaseManager.getConnection();
                PreparedStatement deleteStatment = connection.prepareStatement(DELETE_COMPUTERS)) {
            deleteStatment.setLong(1, companyId);
            result = deleteStatment.executeUpdate();

            connection.commit();
        } catch (SQLException e) {

            LOGGER.error("deleteComputer : " + e.getMessage());
            databaseManager.rollback();
            throw new DaoException(e.getMessage());
        } finally {
            databaseManager.closeConnection();
        }

        if (result == 0) {
            LOGGER.info("deleteComputersCompany False id : " + companyId + " result : " + result);
        }
        return result;
    }

    private String mapColumnOrderBy(String orderBy) {
        switch (orderBy) {
        case "name":
            return computerName;
        case "dateIntro":
            return computerDateIntro;
        case "dateFin":
            return computerDateFin;
        case "company":
            return computerCompanyName;
        default:
            return computerName;
        }
    }
}
