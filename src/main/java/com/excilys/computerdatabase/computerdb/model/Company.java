package com.excilys.computerdatabase.computerdb.model;

import com.excilys.computerdatabase.computerdb.ui.pages.Pageable;

public class Company implements Pageable{

	
	
	private int id;
	private String name;
	
	public Company(){
		
	}

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
	
	public String toString(){
		return "" + id + " " + name;  
	}
	
	
	
}
