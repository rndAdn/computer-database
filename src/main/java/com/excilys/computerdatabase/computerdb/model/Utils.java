package com.excilys.computerdatabase.computerdb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static Optional<LocalDate> stringToDate(String dateString) {
        Optional<LocalDate> optionalDate = Optional.empty();
        try {
            optionalDate = Optional.of(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        } catch (DateTimeParseException e) {
            // LOGGER.error("stringToDate Invalide : " + dateString);
        } catch (NullPointerException e) {
            // LOGGER.error("stringToDate null : " + dateString);
        }

        return optionalDate;
    }

    public static long stringToId(String idString) {
        long id = -1;
        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            LOGGER.warn("stringToId   NumberFormatExeption : " + idString);
        }
        return id;
    }

}
