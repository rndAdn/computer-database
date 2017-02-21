package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class ShowComputerDetailsAction implements ActionMenu {

	public void doAction() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez l'id de l'ordinateur : ");
		String s  = sc.nextLine();
		int id = Integer.parseInt(s); // TODO : Exception Number_Format & Not_A_Number
		
		Computer computer = Database.getComputerDao().getComputerById(id);
	    
		try{
			System.out.println(computer.getDetail());
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
