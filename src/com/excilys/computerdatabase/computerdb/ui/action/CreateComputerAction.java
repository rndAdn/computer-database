package com.excilys.computerdatabase.computerdb.ui.action;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.controller.CompanyController;
import com.excilys.computerdatabase.computerdb.controller.ComputerController;
import com.excilys.computerdatabase.computerdb.controller.exception.ComputerException;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class CreateComputerAction implements ActionMenu {

	public void doAction() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez le nom de l'ordinateur (Obligatoire): ");
		String name  = sc.nextLine();
		
		System.out.print("Entrez la date d'introdution (jj-mm-aaaa): ");
		String dateIntroString = sc.nextLine();
		
		System.out.print("Entrez la date de fin de service (jj-mm-aaaa) : ");
		String dateFinServiceString = sc.nextLine();
		
		System.out.print("Entrez l'id de la companie : ");
		String companyIdString = sc.nextLine();
		
		
		
		try{
			
			ComputerController.checkName(name);
			Date dateIntro = ComputerController.stringToDate(dateIntroString);
			Date dateFin = ComputerController.stringToDate(dateFinServiceString);
			ComputerController.compareDate(dateIntro, dateFin);
			
			
			Company company = null;
			if(companyIdString != null && !companyIdString.equals("")){
				int companyid = ComputerController.stringToId(companyIdString);
				company = Database.getCompanyDao().getCompanyById(companyid);
				ComputerController.checkCompany(company);
			}
			//
			
			Computer computer = new Computer();
			computer.setName(name);
			computer.setDateIntroduced(dateIntro);
			computer.setDateDiscontinued(dateFin);
			computer.setCompagny(company);
			
			
			
			System.out.println(computer.getDetail());
			
			System.out.print("Ajouter ? [O/n]");
			String reponse  = sc.nextLine();
			System.out.println();
			if(reponse.equalsIgnoreCase("n")){
				System.out.print("Ordinateur non ajouté");
				return; 
			}
			
			Database.getComputerDao().insertComputer(computer);
			System.out.print("Ordinateur ajouté");
			
		}
		catch (ComputerException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon");
			e.printStackTrace();
		}
		catch (DaoException e) {
			System.out.println();
			System.out.println();
			System.out.print(e.getMessage());
			System.out.println(" Abandon");
			e.printStackTrace();
		}
		finally {
			System.out.println();
		}

	}

}
