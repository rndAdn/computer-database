package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.controller.ComputerController;
import com.excilys.computerdatabase.computerdb.controller.exception.ComputerException;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class ShowComputerDetailsAction implements ActionMenu {

	public void doAction() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez l'id de l'ordinateur : ");
		String idString  = sc.nextLine();
		
		try{
			long id = ComputerController.stringToId(idString);
			
			Computer computer = Database.getComputerDao().getComputerById(id);
			if (computer == null) throw new ComputerException("Ordinateur introuvable dans la base de donnée");
			
			System.out.println(computer.getDetail());
		}
		catch (ComputerException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon");
		}
		catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon");
		}
		finally {
			System.out.println();
		}
		

	}

}
