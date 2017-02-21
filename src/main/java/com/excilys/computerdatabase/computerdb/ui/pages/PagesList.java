package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class PagesList {
	
	private int rowByPages = 10;
	private int pageNumber = 1;
	private int totalNumberOfpages;
	
	
	public PagesList(int totalRow){
		this.totalNumberOfpages = (int)Math.ceil(totalRow/ rowByPages);
	}
	
	
	public void showPage(){
		
		List<Pageable> computers = Database.getComputerDao().getAllComputers((pageNumber-1) * rowByPages, rowByPages);
	    
		/*System.out.println("--------ComputerList-------------");
		for (Computer c : computers)
	    	System.out.println(c);
		System.out.println("--------EndComputerList----------");*/
		Page page = new Page(computers);
		page.printContent();
		printFooter();
		if(!readAction())return;
		else showPage();
	}
	
	
	
	
	public void printFooter(){
		System.out.print("page " + pageNumber + "/"+totalNumberOfpages + " Page Suivante  [next/n],  Page Précédente [previous/p], Retour [Back/B] : ");
	}
	
	private void next(){
		pageNumber = Math.min(totalNumberOfpages, pageNumber+1);
	}
	
	private void previous(){
		pageNumber = Math.max(1, pageNumber-1);
	}
	
	private boolean readAction(){
		Scanner sc = new Scanner(System.in);
		
		String action = sc.nextLine();
		
		if (action.equalsIgnoreCase("next") || action.equalsIgnoreCase("n")){
			next();
		}
		else if (action.equalsIgnoreCase("previous") || action.equalsIgnoreCase("p")){
			previous();
		}
		else{
			return false;
		}
		return true;
		
		
		
		
		
	}
	
	

}
