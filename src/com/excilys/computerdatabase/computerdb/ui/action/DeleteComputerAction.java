package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.controller.ComputerController;
import com.excilys.computerdatabase.computerdb.controller.exception.ComputerException;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class DeleteComputerAction implements ActionMenu{

	public void doAction() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez l'id de l'ordinateur à supprimer : ");
		String idString  = sc.nextLine();
		
		try{
			long id = ComputerController.stringToId(idString);
			
			
			Computer computer = Database.getComputerDao().getComputerById(id);
			if (computer == null) throw new ComputerException("Ordinateur introuvable dans la base de donnée");
			
			System.out.println(computer.getDetail());
			System.out.print("Supprimer ? [O/n]");
			
			String reponse  = sc.nextLine();
			System.out.println();
			
			if(reponse.equalsIgnoreCase("n")){
				throw new ComputerException("");
			}
			
			Database.getComputerDao().deleteComputer(computer);
			System.out.print("Ordinateur supprimé");
		} catch (ComputerException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon de la suppression");
		}
		catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon de la suppression");
		}
		finally {
			System.out.println();
		}
		
	}

}
