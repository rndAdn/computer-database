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

import javax.persistence.TypedQuery;

import com.excilys.computerdatabase.computerdb.HiberbateUtils.HibernateUtil;
import com.excilys.computerdatabase.computerdb.dao.controller.ControllerDAOComputer;
import com.excilys.computerdatabase.computerdb.mapper.MapperComputer;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.hibernate.query.Query;
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
import com.sun.org.apache.bcel.internal.generic.Type;
import com.excilys.computerdatabase.computerdb.model.entities.Page.BuilderPage;

@Repository
public class ComputerDao implements IComputerDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);


    private final String DELETE_COMPUTER;
    private final String DELETE_COMPUTERS;
    private final String INSERT_COMPUTER;
    private final String UPDATE_COMPUTER;
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

        DELETE_COMPUTER = "DELETE FROM " + computerTable + " WHERE id=?;";
        DELETE_COMPUTERS = "DELETE FROM " + computerTable + " WHERE " + computerCompanyId + "=?;";
        INSERT_COMPUTER = "INSERT into " + computerTable + " (" + computerName + "," + computerDateIntro + ","
                + computerDateFin + "," + computerCompanyId + ") values (?,?,?,?);";
        UPDATE_COMPUTER = "UPDATE " + computerTable + " SET " + computerName + "=?, " + computerDateIntro + "=?, "
                + computerDateFin + "=?, " + computerCompanyId + "=? WHERE " + computerId + "=?;";
    }

    @Override
    public Optional<Computer> getComputerById(long id) throws DaoException {
        Optional<Computer> optionalComputer = Optional.empty();
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.checkId(id)) {
            LOGGER.error("Id non valide : " + id);
            return optionalComputer;
        }
        
        TypedQuery<Computer> query = HibernateUtil.getSessionFactory().openSession().createQuery("select c from Computer c left join c.company where c.id LIKE :id or c.company.name LIKE :name", Computer.class);
        query.setParameter("id",  id);
        List<Computer> list = query.getResultList();
        LOGGER.info("HQL getComputerById: " + list);

        optionalComputer = Optional.ofNullable(list.get(0));
        return optionalComputer;
    }

    @Override
    public Optional<Page> getComputersByName(String name, long limitStart, long size, String orderby)
            throws DaoException {
        
        
        
        TypedQuery<Computer> query = HibernateUtil.getSessionFactory().openSession().createQuery("select c from Computer c left join c.company where c.name LIKE :name or c.company.name LIKE :name order by :order", Computer.class);
        query.setParameter("name",  name + "%");
        query.setParameter("order", orderby);
        query.setMaxResults((int) size);
        query.setFirstResult((int) ((limitStart -1) * size));
        List<Computer> list = query.getResultList();
        LOGGER.info("HQL getComputersByName: " + list);
        
        
        
        
        
        Optional<Page> optionalPage = Optional.empty();
        BuilderPage builderPage = new Page.BuilderPage(name, "name", limitStart, size);
        
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
        
        
        
        TypedQuery<Computer> query = HibernateUtil.getSessionFactory().openSession().createQuery("select c from Computer c left join c.company order by :order", Computer.class);
        query.setParameter("order", orderby);
        query.setMaxResults((int) size);
        query.setFirstResult((int) ((limitStart -1) * size));
        List<Computer> list = query.getResultList();
        LOGGER.info("HQL getComputers: " + list);
        
        
        
        
        
        List<Pageable> list2 = new ArrayList<>();
        for (Computer c : list) {
            list2.add(c);
        }
        builderPage.list(list2);
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
        result = jdbcTemplate.update(DELETE_COMPUTER, 
                id);

        return result == 1;
    }

    @Override
    public boolean updateComputer(Computer computer) throws DaoException {
        int result = -1;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValide(computer)) {
            LOGGER.error("Computer non valide Update : " + computer);
            return false;
        }
        Optional<LocalDate> dateIntro = computer.getDateIntroduced();
        Optional<LocalDate> dateFin = computer.getDateDiscontinued();
        
        Long companyId = computer.getCompanyId();
        
        result = jdbcTemplate.update(UPDATE_COMPUTER, 
                computer.getName(), 
                dateIntro.isPresent()?dateIntro.get():Types.NULL,
                dateFin.isPresent()?dateFin.get():Types.NULL,
                companyId!= null?companyId:java.sql.Types.INTEGER,
                computer.getId());
        return result == 1;
    }

    @Override
    public boolean insertComputer(Computer computer) throws DaoException {
        int result = -1;
        if (!ControllerDAOComputer.CONTROLLER_DAO_COMPUTER.isValideName(computer.getName())) {
            LOGGER.error("Computer non valide Insert: " + computer);
            return false;
        }
        
        Optional<LocalDate> dateIntro = computer.getDateIntroduced();
        Optional<LocalDate> dateFin = computer.getDateDiscontinued();
        
        Long companyId = computer.getCompanyId();
        
        result = jdbcTemplate.update(INSERT_COMPUTER, 
                computer.getName(), 
                dateIntro.isPresent()?dateIntro.get():Types.NULL,
                dateFin.isPresent()?dateFin.get():Types.NULL,
                companyId!= null?companyId:java.sql.Types.INTEGER);
        
        return result == 1;
    }

    @Override
    public long countComputers() throws DaoException {
        
        Query query = HibernateUtil.getSessionFactory().openSession().createQuery(
                "select count(*) from Computer");
        Long count = (Long)query.uniqueResult();
        LOGGER.info("HQL countComputers: " + count);
        return count;
    }

    @Override
    public long countComputersWithName(String name) throws DaoException {
        
        Query query = HibernateUtil.getSessionFactory().openSession().createQuery(
                "select count(*) from Computer c left join c.company where c.name LIKE :name or c.company.name LIKE :name");
        query.setParameter("name",  name + "%");
        Long count = (Long)query.uniqueResult();
        LOGGER.info("HQL countComputersWithName: " + count);
        return count;
    }

    @Override
    public long deleteComputersCompany(long companyId) throws DaoException {
        int result = -1;
        
        result = jdbcTemplate.update(DELETE_COMPUTERS, 
                companyId);

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
