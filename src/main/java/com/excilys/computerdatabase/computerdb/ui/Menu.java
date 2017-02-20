package com.excilys.computerdatabase.computerdb.ui;

import com.excilys.computerdatabase.computerdb.ui.action.*;

public enum Menu {
	
	LISTCOMPUTER ('A', "List Computer", new ListComputerAction() ),
	LISTCOMPANY ('Z', "List Company", new ListCompanyAction()),
	SHOW ('S', "Show", new ShowComputerDetailsAction()),
	CREATE ('C', "Create", new CreateComputerAction()),
	UPDATE ('U', "Update", new UpdateComputerAction()),
	DELETE ('D', "Delete", new DeleteComputerAction()),
	EXIT ('E', "Exit", new ExitAction());

	private char commandeChar;
	private String commandeString;
	private ActionMenu commandeAction;
	
	Menu(char c, String s, ActionMenu ac){
		commandeChar = c;
		commandeString = s;
		commandeAction = ac;
	}
	
	public String toString(){
		return ""+commandeString + "\t (" + commandeChar + ").";
	}
	
	
	public boolean isEntry(String s){
		if(s.length() == 1) return new Character(s.toUpperCase().charAt(0)).equals(commandeChar);
		return s.equalsIgnoreCase(commandeString);		
	}
	
	public ActionMenu getAction(){
		return commandeAction;
	}
	
	
}
