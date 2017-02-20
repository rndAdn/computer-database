package com.excilys.computerdatabase.computerdb.ui;

public enum Menu {
	
	LIST (new Tuple<Character, String>('L', "List")),
	SHOW (new Tuple<Character, String>('S', "Show")),
	CREATE (new Tuple<Character, String>('C', "Create")),
	UPDATE (new Tuple<Character, String>('U', "Update")),
	DELETE (new Tuple<Character, String>('D', "Delete")),
	EXIT (new Tuple<Character, String>('E', "Exit"));

	private Tuple commande;
	
	Menu(Tuple commande){
		this.commande = commande;
	}
	
	public String toString(){
		return ""+commande.getSecond() + " (" + commande.getFirst() + ")";
	}
}
