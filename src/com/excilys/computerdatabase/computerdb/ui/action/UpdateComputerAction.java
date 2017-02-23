package com.excilys.computerdatabase.computerdb.ui.action;

import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.database.CompanyDao;
import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.ComputerValidator;
import com.excilys.computerdatabase.computerdb.model.Utils;

public class UpdateComputerAction implements ActionMenu {

	public void doAction() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez l'id de l'ordinateur à modifier : ");
		String idString  = sc.nextLine();
		
		try{
			long id = Utils.stringToId(idString);
			

			ComputerDao computerDao = new ComputerDao();
			Optional<Computer> optionalComputer = computerDao.getComputerById(id);
			if (! optionalComputer.isPresent()){
				System.out.println("Ordinateur introuvable dans la base de donnée");
				//sc.close();
				return;
			}
			Computer computer = optionalComputer.get();
			
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
			Date dateIntro = Utils.stringToDate(dateIntroString);
			if(dateIntro != null) computer.setDateIntroduced(dateIntro);
			
			Date dateFin = Utils.stringToDate(dateFinServiceString);
			if(dateFin != null) computer.setDateDiscontinued(dateFin);
			
			ComputerValidator.compareDate(computer.getDateIntroduced(), computer.getDateDiscontinued());//
			
			
			
			
			if(name != null && !name.equals(""))computer.setName(name);
			
			Optional<Company> optionalCompany = Optional.empty();
			if(companyIdString != null && !companyIdString.equals("")){
				long companyid = Utils.stringToId(companyIdString);
				CompanyDao companyDao = new CompanyDao();
				optionalCompany = companyDao.getCompanyById(companyid);
			}
			computer.setCompagny(optionalCompany.orElse(null));
			
			
			System.out.println(computer.getDetail());
			System.out.print("Mettre à jour ? [O/n]");
			String reponse  = sc.nextLine();
			System.out.println();
			if(reponse.equalsIgnoreCase("n")){
				System.out.print("Ordinateur non mis à jour");
				return; 
			}
			
			computerDao.updateComputer(computer);
			System.out.print("Ordinateur mis à jour");
			optionalComputer = computerDao.getComputerById(id);
			if (! optionalComputer.isPresent()){
				System.out.println("Ordinateur introuvable dans la base de donnée");
				sc.close();
				return;
			}
			computer = optionalComputer.get();
			
			System.out.println(computer.getDetail());
			System.out.println(computer.getDetail());
			
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
