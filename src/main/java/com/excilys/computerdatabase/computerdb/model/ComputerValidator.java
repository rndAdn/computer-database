package com.excilys.computerdatabase.computerdb.model;

import java.sql.Date;
import java.util.Optional;

public class ComputerValidator {
	
	//DONE
	public static boolean checkName(String name){
		return !(name == null || name.equals(""));
	}
	
	// DONE 
	public static boolean checkCompany(Company company){
		return (company != null);
	}
	
	
	//done
	public static boolean compareDate(Optional<Date> dateIntro, Optional<Date> dateFin){
		return !(dateIntro.isPresent() && dateFin.isPresent() &&  dateIntro.get().compareTo(dateFin.get()) > 0);
	}
	
	public static boolean compareDate(Date dateIntro, Date dateFin){
		return !(dateIntro != null && dateFin != null &&  dateIntro.compareTo(dateFin) > 0);
	}
	

}
