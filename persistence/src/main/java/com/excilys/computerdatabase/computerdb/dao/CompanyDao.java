package com.excilys.computerdatabase.computerdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.HiberbateUtils.HibernateUtil;
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
import com.excilys.computerdatabase.computerdb.model.entities.Page.BuilderPage;

@Repository
public class CompanyDao implements ICompanyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    private String companyTable;
    private String companyId;


    @Override
    public Optional<Company> getCompanyById(long id) throws DaoException {
        Optional<Company> optionalCompany = Optional.empty();
        if (!ControllerDAOCompany.CONTROLLER_DAO_COMPANY.checkId(id)) {
            LOGGER.error("Id non valide : " + id);
            return optionalCompany;
        }
        
        Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from Company where id = :id ");
        query.setParameter("id", id);
        List<Company> list = query.list();
        
        if (list.size() > 0) {
            optionalCompany = Optional.ofNullable(list.get(0));
        }
        return optionalCompany;

    }

    @Override
    public Optional<Page> getCompanyByName(String name, long limitStart, long size) throws DaoException {
        Query query = HibernateUtil.getSessionFactory().openSession().createQuery("from Company where name = :name ");
        BuilderPage builderPage = new Page.BuilderPage(name, "name", limitStart, size);
        
        query.setParameter("name", name);
        List<Company> list = query.list();
        Optional<Page> optionalPage = Optional.empty();
 
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
        
        Query query = HibernateUtil.getSessionFactory().openSession().createQuery("from Company");
        query.setMaxResults((int) size);
        query.setFirstResult((int) ((limitStart -1) * size));
        List<Company> list = query.list();
        
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

        Query query = HibernateUtil.getSessionFactory().openSession().createQuery("from Company");
        List<Company> list = query.list();
        
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
        Query query = HibernateUtil.getSessionFactory().openSession().createQuery(
                "select count(*) from Company");
        Long count = (Long)query.uniqueResult();
        LOGGER.info("HQL countCompany: " + count);
        return count;
    }

    @Override
    public long getNumberOfCompany(String name) throws DaoException {
        Query query = HibernateUtil.getSessionFactory().openSession().createQuery(
                "select count(*) from Company c name LIKE :name");
        query.setParameter("name",  name + "%");
        Long count = (Long)query.uniqueResult();
        LOGGER.info("HQL countCompanygsWithName: " + count);
        return count;

    }

    @Override
    public boolean deleteCompany(Company company) throws DaoException {
        if (!ControllerDAOCompany.CONTROLLER_DAO_COMPANY.isValide(company)) {
            LOGGER.error("Company non valide : '" + company + "'");
            return false;
        }
        
        Query query = HibernateUtil.getSessionFactory().openSession().createQuery("DELETE FROM " + companyTable + " WHERE " + companyId + "= :id");
        query.setParameter("id", company.getId());
        int result = query.executeUpdate();
        
        return result == 1;
    }

}