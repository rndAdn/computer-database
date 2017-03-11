package com.excilys.computerdatabase.computerdb.dao.controller;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import org.apache.commons.lang.StringUtils;

public enum ControllerDAOComputer {
    CONTROLLER_DAO_COMPUTER;

    public boolean checkId(long id) {
        return id > 0;
    }

    public boolean isValideName(String name) {
        return !StringUtils.isBlank(name);
    }

    public boolean isValide(long id, String name) {
        return checkId(id) && isValideName(name);
    }

    public boolean isValide(Computer computer) {
        return isValide(computer.getId(), computer.getName());
    }
}
