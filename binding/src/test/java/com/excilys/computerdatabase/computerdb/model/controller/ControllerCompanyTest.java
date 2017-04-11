package com.excilys.computerdatabase.computerdb.model.controller;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.validator.ValidatorCompanyModel;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by renaud on 12/03/17.
 */
public class ControllerCompanyTest {

    @Test
    public void testCheckCompany_Null(){
        assertFalse(ValidatorCompanyModel.CONTROLLER_COMPANY.isValide(null));
    }

    @Test
    public void testCheckCompany_Empty(){
        Company company = new Company.CompanyBuilder("").build();
        assertFalse(ValidatorCompanyModel.CONTROLLER_COMPANY.isValide(company));
    }

    @Test
    public void testCheckCompany_Full(){
        Company company = new Company.CompanyBuilder("FABLER BJÖRN")
                .id(42)
                .build();

        assertTrue(ValidatorCompanyModel.CONTROLLER_COMPANY.isValide(company));
    }
}
