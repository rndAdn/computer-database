package com.excilys.computerdatabase.computerdb.dao.controller;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import org.apache.commons.lang.StringUtils;

public enum ControllerDAOCompany {
    CONTROLLER_DAO_COMPANY;


    public boolean checkId(long id) {
        return id > 0;
    }

    public boolean isValideName(String name) {
        return !StringUtils.isBlank(name);
    }

    public boolean isValide(long id, String name) {
        return checkId(id) && isValideName(name);
    }

    public boolean isValide(Company company) {
        return isValide(company.getId(), company.getName());
    }

}
