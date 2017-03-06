package com.excilys.computerdatabase.computerdb.ui.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.model.ComputerValidator;
import com.excilys.computerdatabase.computerdb.model.Utils;

public class ControllerComputer {


    public static boolean checkComputer(String name, String dateIntroStr, String dateFinStr) {
        if (StringUtils.isBlank(name)) {
            return false;
        }

        Optional<LocalDate> intro = Utils.stringToDate(dateIntroStr);
        Optional<LocalDate> fin = Utils.stringToDate(dateFinStr);

        return ComputerValidator.compareDate(intro, fin);
    }


    public static boolean checkCompanyId(String idCompany) {
        long id = Utils.stringToId(idCompany);
        return ComputerValidator.checkID(id);
    }

}
