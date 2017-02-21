package com.excilys.computerdatabase.computerdb.model;

import java.sql.Date;

import com.excilys.computerdatabase.computerdb.ui.pages.Pageable;

public class Computer implements Pageable {

	
	
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
	
	public Integer getCompanyId() {
		return ((company!= null)?company.getId():null);
	}
	
	public void setCompagny(Company company) {
		this.company = company;
	}
	
	public String toString(){
		return "" + id + 
				"\t" + name;
	}
	
	public String getDetail(){
		return "Id : "+id + 
				"\tNom : " + name +
				"\tDate Introduction : " + ((dateIntroduced != null)?dateIntroduced:"NC") + 
				"\tDate fin : " + ((dateDiscontinued != null)?dateDiscontinued:"NC") +
				"\tCompany : " + ((company!= null)?company.getName():"NC"); 
	}
	
	
	public void checkComputer(){
		
	}
	
}
