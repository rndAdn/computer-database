package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class DeleteComputerAction implements ActionMenu{

	public void doAction() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez l'id de l'ordinateur à supprimer : ");
		String s  = sc.nextLine();
		
	    
		try{
			int id = Integer.parseInt(s);
			
			Computer computer = Database.getComputerDao().getComputerById(id);
			
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
			//e.printStackTrace();
			System.out.println("Aucun Ordinateur ne correspond à cette ID");
		}
		catch (NumberFormatException e) {
			//e.printStackTrace();
			System.out.println("id Incorrect");
		}
		finally {
			System.out.println();
		}
		
	}

}
