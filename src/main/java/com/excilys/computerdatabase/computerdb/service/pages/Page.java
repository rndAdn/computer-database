package com.excilys.computerdatabase.computerdb.service.pages;

import java.util.List;

public class Page {
	
	private List<Pageable> computerList;
	
	
	public Page(List<Pageable> list){
		this.computerList = list;
		
	}
	
	public void printContent(){
		System.out.println("\n----------------");
		for(Pageable p : computerList){
			System.out.println(p);
		}
		System.out.println("\n");
	}
	
	

}
