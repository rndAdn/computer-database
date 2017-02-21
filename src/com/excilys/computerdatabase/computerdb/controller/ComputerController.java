package com.excilys.computerdatabase.computerdb.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.excilys.computerdatabase.computerdb.controller.exception.ComputerException;
import com.excilys.computerdatabase.computerdb.model.Company;

public class ComputerController {
	
	public static void checkName(String name) throws ComputerException{
		if(name == null || name.equals("")) throw new ComputerException("Nom de l'ordinateur obligatoire");
	}
	
	public static void checkCompany(Company company) throws ComputerException{
		if(company == null) throw new ComputerException("Company absente ou introuvable");
	}
	
	public static Date stringToDate(String date) throws ComputerException {
		DateFormat sourceFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return (!date.equals(""))?new Date(sourceFormat.parse(date).getTime()):null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new ComputerException("Format date incorrect (jj-mm-aaaa)");
		}
	}
	
	public static void compareDate(Date dateIntro, Date dateFin) throws ComputerException{
		if(dateIntro != null && dateFin != null &&  dateIntro.compareTo(dateFin) > 0){
			throw new ComputerException("La date de fin de service ne peut pas être inferieur à la date de mise en service");
		}
	}
	
	public static int stringToId(String idString) throws ComputerException{
		int id;
		try {
			id = Integer.parseInt(idString);
		}
		catch (NumberFormatException e) {
			throw new ComputerException("Erreur l'id n'est pas correct ou est introuvable");
		}
		return id;
	}

}
