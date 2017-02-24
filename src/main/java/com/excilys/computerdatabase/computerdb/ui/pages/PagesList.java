package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.List;
import java.util.Scanner;

public abstract class PagesList {
	
	protected int rowByPages = 30;
	protected int pageNumber = 1;
	private int totalNumberOfpages;
	
	
	public PagesList(long l){
		this.totalNumberOfpages = (int)Math.ceil(l/ (double)rowByPages);
	}
	
	public abstract List<Pageable> getList();
	
	public void showPage(){
		
		List<Pageable> computers = getList();

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
