package com.excilys.computerdatabase.computerdb.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class Utils {
	

	
	public static Date stringToDate(String dateString){
		Date date = null;
		DateFormat sourceFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			date = new Date(sourceFormat.parse(dateString).getTime());
		} catch (ParseException e) {
		}		
		return date;
	}

	
	public static long stringToId(String idString){
		long id = -1;
		try {
			id = Long.parseLong(idString);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return id;
	}

}
