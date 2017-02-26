package com.excilys.computerdatabase.computerdb.ui.pages;

import java.util.List;
import java.util.Scanner;

public abstract class PagesList {
	
	protected int rowByPages = 30;
	protected int pageNumber = 1;
	protected int totalNumberOfpages;
	

	protected abstract List<Pageable> getList();

	public void nextPage(){
		pageNumber = Math.min(totalNumberOfpages, pageNumber+1);
	}
	
	public void previousPage(){
		pageNumber = Math.max(1, pageNumber-1);
	}
	
	public void firstPage(){
		pageNumber = 1;
	}
	
	public void lastPage(){
		pageNumber = totalNumberOfpages;
	}
	
	public Page getPage(){
		List<Pageable> computers = getList();
		return new Page(computers); 
	}
	
	public long getPageIndex(){
		return pageNumber;
	}
	
	public long getTotalPageNumber(){
		return totalNumberOfpages;
	}
	
}
