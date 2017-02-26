package com.excilys.computerdatabase.computerdb.ui.action;

import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.ui.pages.Page;
import com.excilys.computerdatabase.computerdb.ui.pages.PagesList;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListCompanyAction implements ActionMenu {
	
	private static Logger LOGGER;
	
	public ListCompanyAction(){
		LOGGER = LoggerFactory.getLogger("com.excilys.computerdatabase.computerdb.ui.action.ListCompanyAction");
	}

	public void doAction() {
		PagesList pagesList;
		CompanyService companyService = new CompanyService();
		pagesList = companyService.getCompanys(); 		
		
		
		Page page = pagesList.getPage();
		
		page.printContent();
		
		do {
			page.printContent();
			
			printFooter(pagesList);
			
		} while (readAction(pagesList));
	}
	
	public void printFooter(PagesList pagesList){
		System.out.print("page " + pagesList.getPageIndex() + "/"+ pagesList.getTotalPageNumber() + "Premiere page [first/f], Page Précédente [previous/p], Page Suivante  [next/n], Page Précédente [last/l], Retour [Back/B] : ");
	}
	
	private boolean readAction(PagesList pagesList){
		Scanner sc = new Scanner(System.in);
		
		String action = sc.nextLine();
		
		if (action.equalsIgnoreCase("next") || action.equalsIgnoreCase("n")){
			pagesList.nextPage();
		}
		else if (action.equalsIgnoreCase("previous") || action.equalsIgnoreCase("p")){
			pagesList.previousPage();
		}
		else if (action.equalsIgnoreCase("first") || action.equalsIgnoreCase("f")){
			pagesList.firstPage();
		}
		else if (action.equalsIgnoreCase("last") || action.equalsIgnoreCase("l")){
			pagesList.lastPage();
		}
		else{
			return false;
		}
		return true;
		
	}

}
