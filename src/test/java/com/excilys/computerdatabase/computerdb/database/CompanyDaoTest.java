package com.excilys.computerdatabase.computerdb.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.computerdb.model.mapper.MapperCompany;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import com.excilys.computerdatabase.computerdb.model.Company;


public class CompanyDaoTest {
    
    private ResultSet resultSet;
    
    @Before
    public void executerAvantChaqueTest() {
        resultSet = Mockito.mock(ResultSet.class);
    }

    @After
    public void executerApresChaqueTest() {
        resultSet = null;
    }

    /*
    @Test
    public void testGetCompanyByIdNegatif() {
        
        long id = -1;
        Optional<Company> result = Optional.empty();
        try {
            result = companyDao.getCompanyById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(Optional.empty(), result );
    }
    
    @Test
    public void testGetCompanyByIdZero() {
        long id = 0;
        Optional<Company> result = Optional.empty();
        try {
            result = companyDao.getCompanyById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(Optional.empty(), result );
    }
    
    @Test
    public void testGetCompanyByIdHasResult() {
        long id = 1;
        Optional<Company> result = Optional.empty();
        try {
            result = companyDao.getCompanyById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertTrue(result.isPresent());
    }
    
    @Test
    public void testGetCompanyByIdResult() {
        long id = 1;
        Optional<Company> result = Optional.empty();
        try {
            result = companyDao.getCompanyById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(id, result.get().getId());
    }
    
    @Test
    public void testgetCompanyByNameEmpty(){
        List<Pageable> result;
        String name = "";
        try {
            result = companyDao.getCompanyByName(name, 0, 100);
            assertEquals(0, result.size());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test    
    public void testgetCompanyByNameInvalide(){
        List<Pageable> result;
        String name = "blabla";
        try {
            result = companyDao.getCompanyByName(name, 0, 100);
            assertEquals(0, result.size());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testgetCompanyByNameHasResult(){
        List<Pageable> result;
        String name = "ASUS";
        try {
            result = companyDao.getCompanyByName(name, 0, 100);
            assertEquals(1, result.size());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testgetCompanyByNameResult(){
        List<Pageable> result;
        String name = "ASUS";
        try {
            result = companyDao.getCompanyByName(name, 0, 100);
            assertEquals(name, ((Company)result.get(0)).getName());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test(expected = DaoException.class, timeout = 1000)
    public void _testgetCompanysNoLimit(){
        List<Pageable> result;
        try {
            result = companyDao.getCompanys(0, -1);
            assertEquals(42, result.size());
        } catch (DaoException e) {
            //e.printStackTrace();
        }
    }
    */
    
    @Test
    public void testMapCompany() throws SQLException{
        String name = "blabla";
        long id = 42;
        
        Mockito.when(resultSet.getLong("id")).thenReturn(id);
        Mockito.when(resultSet.getString("name")).thenReturn(name);
        
        Company company = MapperCompany.mapCompany(resultSet);
        
        assertEquals(name, company.getName());
        assertEquals(id, company.getId());
        
        
        
        
    }
    
}
