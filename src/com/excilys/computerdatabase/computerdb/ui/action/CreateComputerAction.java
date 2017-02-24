package com.excilys.computerdatabase.computerdb.ui.action;

import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;


import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.ComputerValidator;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

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
		
		
		
		boolean checkName = ComputerValidator.checkName(name);
		if(!checkName){
			System.out.println("Le nom de l'ordinateur n'est pas valide");
			return;
			
		}
		Date dateIntro = Utils.stringToDate(dateIntroString);
		Date dateFin = Utils.stringToDate(dateFinServiceString);
		
		
		boolean checkIntervalDate = ComputerValidator.compareDate(dateIntro, dateFin);
		
		if(! checkIntervalDate){
			System.out.println("Les dates ne sont pas valides");
			return;
		}
		
		Optional<Company> optionalCompany = Optional.empty();
		
		if(companyIdString != null && !companyIdString.equals("")){
			long companyid = Utils.stringToId(companyIdString);
			
			optionalCompany = CompanyService.getCompanyByid(companyid);
		}
		
		Computer computer = new Computer();
		computer.setName(name);
		computer.setDateIntroduced(dateIntro);
		computer.setDateDiscontinued(dateFin);
		computer.setCompagny(optionalCompany.orElse(null));
		
		
		
		System.out.println(computer.getDetail());
		
		System.out.print("Ajouter ? [O/n]");
		String reponse  = sc.nextLine();
		System.out.println();
		if(reponse.equalsIgnoreCase("n")){
			System.out.print("Ordinateur non ajouté");
			//sc.close();
			return; 
		}
		ComputerService.ajoutComputer(computer);
		System.out.print("Ordinateur ajouté");
		

	}

}
