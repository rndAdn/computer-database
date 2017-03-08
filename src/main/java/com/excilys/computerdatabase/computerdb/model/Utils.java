package com.excilys.computerdatabase.computerdb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    /**
     * Transform a date represented by a String to a LocalDate given a pattern
     * (dd-MM-yyyy).
     *
     * @param dateString
     *            .
     * @return LocalDate or a optional.empty
     */
    public static Optional<LocalDate> stringToDate(String dateString) {
        Optional<LocalDate> optionalDate = Optional.empty();
        try {
            optionalDate = Optional.of(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        } catch (DateTimeParseException e) {
            try {
                optionalDate = Optional.of(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } catch (DateTimeParseException e1) {
                //LOGGER.error("stringToDate Invalide : " + dateString);
            }
        } catch (NullPointerException e) {
            //LOGGER.error("stringToDate null : " + dateString);
        }

        return optionalDate;
    }

    /**
     * Transform a id represented by a String to a long. dosen't check id the id
     * is valid.
     *
     * @param idString
     *            string to transform
     * @return a long id.
     */
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
