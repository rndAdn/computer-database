package com.excilys.computerdatabase.computerdb.model;


import java.time.LocalDate;
import java.util.Optional;


public class ComputerValidator {
	
	public static boolean checkCompany(Company company){
		return (company != null);
	}
	
	public static boolean compareDate(Optional<LocalDate> dateIntro, Optional<LocalDate> dateFin){
		return !(dateIntro.isPresent() && dateFin.isPresent() &&  dateIntro.get().compareTo(dateFin.get()) > 0);
	}
	
	public static boolean compareDate(LocalDate dateIntro, LocalDate dateFin){
		return !(dateIntro != null && dateFin != null &&  dateIntro.compareTo(dateFin) > 0);
	}
	
	public static boolean checkID(long id){
		return (id >= 0) ;
	}
	

}
