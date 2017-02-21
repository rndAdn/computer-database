package com.excilys.computerdatabase.computerdb.ui.action;

import java.sql.Date;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.controller.ComputerController;
import com.excilys.computerdatabase.computerdb.controller.exception.ComputerException;
import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class UpdateComputerAction implements ActionMenu {

	public void doAction() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez l'id de l'ordinateur à modifier : ");
		String idString  = sc.nextLine();
		
		try{
			int id = ComputerController.stringToId(idString);
			
			
			Computer computer = Database.getComputerDao().getComputerById(id);
			if (computer == null) throw new ComputerException("Ordinateur introuvable dans la base de donnée");
			System.out.println(computer.getDetail());
			
			System.out.print("Entrez le nom de l'ordinateur : ");
			String name  = sc.nextLine();
			
			System.out.print("Entrez la date d'introdution (jj-mm-aaaa): ");
			String dateIntroString = sc.nextLine();
			
			System.out.print("Entrez la date de fin de service (jj-mm-aaaa) : ");
			String dateFinServiceString = sc.nextLine();
			
			System.out.print("Entrez l'id de la companie  : ");
			String companyIdString = sc.nextLine();
			
			//ComputerController.checkName(name);
			Date dateIntro = ComputerController.stringToDate(dateIntroString);
			if(dateIntro != null) computer.setDateIntroduced(dateIntro);
			
			Date dateFin = ComputerController.stringToDate(dateFinServiceString);
			if(dateFin != null) computer.setDateDiscontinued(dateFin);
			
			ComputerController.compareDate(computer.getDateIntroduced(), computer.getDateDiscontinued());
			
			
			
			
			if(name != null && !name.equals(""))computer.setName(name);
			
			Company company = null;
			if(companyIdString != null && !companyIdString.equals("")){
				int companyid = ComputerController.stringToId(companyIdString);
				company = Database.getCompanyDao().getCompanyById(companyid);
				ComputerController.checkCompany(company);
			}
			//int companyid = ComputerController.stringToId(companyIdString);
			
			//Company company = Database.getCompanyDao().getCompanyById(companyid);
			//ComputerController.checkCompany(company);
			
			if(company != null)computer.setCompagny(company);
			
			
			System.out.println(computer.getDetail());
			System.out.print("Mettre à jour ? [O/n]");
			String reponse  = sc.nextLine();
			System.out.println();
			if(reponse.equalsIgnoreCase("n")){
				System.out.print("Ordinateur non mis à jour");
				return; 
			}
			
			Database.getComputerDao().updateComputer(computer);
			System.out.print("Ordinateur mis à jour");
			computer = Database.getComputerDao().getComputerById(id);
			System.out.println(computer.getDetail());
			
		}
		catch (ComputerException e) {
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
