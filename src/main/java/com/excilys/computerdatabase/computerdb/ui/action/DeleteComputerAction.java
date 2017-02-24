package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.Optional;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

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
			
			Computer computer = optionalComputer.get();
			
			System.out.println(computer.getDetail());
			System.out.print("Supprimer ? [O/n]");
			
			String reponse  = sc.nextLine();
			System.out.println();
			
			if(reponse.equalsIgnoreCase("n")){
				System.out.println(" Abandon de la suppression");
				return;
			}
			
			ComputerService computerService = new ComputerService();
			computerService.deleteComputer(computer);
			
			
			System.out.print("Ordinateur supprimé");
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
