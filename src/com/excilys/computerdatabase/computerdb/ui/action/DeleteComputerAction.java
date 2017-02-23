package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.Optional;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;

public class DeleteComputerAction implements ActionMenu{

	public void doAction() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez l'id de l'ordinateur à supprimer : ");
		String idString  = sc.nextLine();
		
		try{
			long id = Utils.stringToId(idString);
			
			ComputerDao computerDao = new ComputerDao();
			Optional<Computer> optionalComputer = computerDao.getComputerById(id);
			if (! optionalComputer.isPresent()){
				System.out.println("Ordinateur introuvable dans la base de donnée");
				sc.close();
				return;
			}
			
			System.out.println(optionalComputer.get().getDetail());
			System.out.print("Supprimer ? [O/n]");
			
			String reponse  = sc.nextLine();
			System.out.println();
			
			if(reponse.equalsIgnoreCase("n")){
				System.out.println(" Abandon de la suppression");
				//sc.close();
				return;
			}
			
			computerDao.deleteComputer(optionalComputer.get());
			System.out.print("Ordinateur supprimé");
		}
		catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon de la suppression");
		}
		finally {
			//sc.close();
			System.out.println();
		}
		
	}

}
