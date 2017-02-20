package com.excilys.computerdatabase.computerdb.model;

import java.util.Calendar;

public class Computer {

	
	
	private int id;
	private String name;
	private Calendar dateIntroduced;
	private Calendar dateDiscontinued;
	private int compagnyId;
	
	
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
	public Calendar getDateIntroduced() {
		return dateIntroduced;
	}
	public void setDateIntroduced(Calendar dateIntroduced) {
		this.dateIntroduced = dateIntroduced;
	}
	public Calendar getDateDiscontinued() {
		return dateDiscontinued;
	}
	public void setDateDiscontinued(Calendar dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}
	public int getCompagnyId() {
		return compagnyId;
	}
	public void setCompagnyId(int compagnyId) {
		this.compagnyId = compagnyId;
	}
	
	
	
	
}
