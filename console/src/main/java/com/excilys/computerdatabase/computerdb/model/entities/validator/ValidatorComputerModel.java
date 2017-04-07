package com.excilys.computerdatabase.computerdb.model.entities.validator;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.model.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ValidatorComputerModel {

    CONTROLLER_COMPUTER;

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorComputerModel.class);

    /**
     * Compare if dateIntro if before dateFin.
     *
     * @param dateIntro .
     * @param dateFin   .
     * @return true if dateIntro is before dateFin.
     */
    public boolean compareDate(Optional<LocalDate> dateIntro, Optional<LocalDate> dateFin) {
        return !(dateIntro.isPresent() && dateFin.isPresent() && !(dateIntro.get().isBefore(dateFin.get()) || dateIntro.get().isEqual(dateFin.get())));
    }

    /**
     * Check if id is valid non-null positive number.
     *
     * @param id to check
     * @return true if id is valid non-null positive number.
     */
    public boolean checkId(long id) {
        return (id > 0);
    }

    public boolean isValideComputer(String id, String name, String dateIntro, String dateFin, String idCompany) {
        return checkId(Utils.stringToId(id)) && isValideComputer(name, dateIntro, dateFin) && ValidatorCompanyModel.CONTROLLER_COMPANY.checkId(Utils.stringToId(idCompany));
    }

    public boolean isValideComputer(String name, String dateIntroStr, String dateFinStr) {
        if (StringUtils.isBlank(name)) {
            LOGGER.info("BlankName " + name);
            return false;
        }

        Optional<LocalDate> intro = Utils.stringToDate(dateIntroStr);
        Optional<LocalDate> fin = Utils.stringToDate(dateFinStr);

        return compareDate(intro, fin);
    }




}
