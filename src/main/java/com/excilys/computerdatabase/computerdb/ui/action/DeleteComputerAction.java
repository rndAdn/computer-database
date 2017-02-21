package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.model.Computer;

import database.Database;

public class DeleteComputerAction implements ActionMenu{

	public void doAction() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez l'id de l'ordinateur à supprimer : ");
		String s  = sc.nextLine();
		int id = Integer.parseInt(s); // TODO : Exception Number_Format & Not_A_Number
		
		Computer computer = Database.getComputerDao().getComputerById(id);
	    
		try{
			System.out.println(computer.getDetail());
			System.out.print("Supprimer ? [O/n]");
			String reponse  = sc.nextLine();
			System.out.println();
			if(reponse.equalsIgnoreCase("n")){
				System.out.print("Ordinateur non supprimé");
				return; 
			}
			Database.getComputerDao().deleteComputer(computer);
			System.out.print("Ordinateur supprimé");
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Aucun Ordinateur ne correspond à cette ID");
		}
		finally {
			System.out.println();
		}
		
	}

}
