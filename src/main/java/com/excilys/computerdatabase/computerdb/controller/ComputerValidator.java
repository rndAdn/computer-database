package com.excilys.computerdatabase.computerdb.controller;

import com.excilys.computerdatabase.computerdb.model.Company;

import java.time.LocalDate;
import java.util.Optional;

public class ComputerValidator {

    /**
     * Check if the company is not null.
     *
     * @param company to check
     * @return true if the company is not null
     */
    public static boolean checkCompany(Company company) {
        return (company != null);
    }

    /**
     * Compare if dateIntro if before dateFin.
     *
     * @param dateIntro .
     * @param dateFin   .
     * @return true if dateIntro is before dateFin.
     */
    public static boolean compareDate(Optional<LocalDate> dateIntro, Optional<LocalDate> dateFin) {
        return !(dateIntro.isPresent() && dateFin.isPresent() && !dateIntro.get().isBefore(dateFin.get()));
    }

    /**
     * Check if id is valid non-null positive number.
     *
     * @param id to check
     * @return true if id is valid non-null positive number.
     */
    public static boolean checkID(long id) {
        return (id > 0);
    }

    public static boolean isValideComputer(String id, String dateIntro, String dateFin, String idCompany) {
        return false;
    }
}
