package com.excilys.computerdatabase.computerdb.model;

import java.util.Calendar;
import java.util.Date;

public class Computer {

	
	
	private int id;
	private String name;
	private Date dateIntroduced;
	private Date dateDiscontinued;
	private Company company;
	
	
	public Computer(){}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateIntroduced() {
		return dateIntroduced;
	}
	public void setDateIntroduced(Date dateIntroduced) {
		this.dateIntroduced = dateIntroduced;
	}
	public Date getDateDiscontinued() {
		return dateDiscontinued;
	}
	public void setDateDiscontinued(Date dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}
	public Company getCompany() {
		return this.company;
	}
	public void setCompagny(Company company) {
		this.company = company;
	}
	
	public String toString(){
		return "" + id + " " + name;
	}
	
	public String getDetail(){
		return "Id : "+id + 
				" Nom : " + name +
				" DateI : " + ((dateIntroduced != null)?dateIntroduced:"NC") + 
				" Date fin : " + ((dateDiscontinued != null)?dateDiscontinued:"NC") +
				" Company : " + company.getName(); 
	}
	
	
	
	
}
