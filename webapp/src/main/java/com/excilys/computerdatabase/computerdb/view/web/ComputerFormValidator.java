package com.excilys.computerdatabase.computerdb.view.web;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;

@Component
public class ComputerFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return ComputerDTO.class.equals(arg0);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.EmptyName");
        
        ComputerDTO computerDTO = (ComputerDTO) object;
        
        Optional<LocalDate> intro = Utils.stringToDate(computerDTO.getDateIntroduced());
        Optional<LocalDate> fin = Utils.stringToDate(computerDTO.getDateDiscontinued());
        if (intro.isPresent() && fin.isPresent() && !(intro.get().isBefore(fin.get()))) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateIntroduced", "validation.EmptyName");
        }
        
    }

}
