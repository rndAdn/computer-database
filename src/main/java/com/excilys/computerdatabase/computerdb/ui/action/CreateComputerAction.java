package com.excilys.computerdatabase.computerdb.ui.action;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.database.Database;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class CreateComputerAction implements ActionMenu {

	public void doAction() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez le nom de l'ordinateur (Obligatoire): ");
		String name  = sc.nextLine();
		
		System.out.print("Entrez la date d'introdution (jj-mm-aaaa): ");
		String dateIntro = sc.nextLine();
		
		System.out.print("Entrez la date de fin de service (jj-mm-aaaa) : ");
		String dateFinService = sc.nextLine();
		
		System.out.print("Entrez l'id de la companie (Obligatoire) : ");
		String company = sc.nextLine();
		
		
		
		try{
			
			DateFormat sourceFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date intro = (!dateIntro.equals(""))?new java.sql.Date(sourceFormat.parse(dateIntro).getTime()):null;
			Date datefin = (!dateFinService.equals(""))?new java.sql.Date(sourceFormat.parse(dateFinService).getTime()):null;
			int companyid = Integer.parseInt(company);
			
			Computer computer = new Computer();
			computer.setName(name);
			computer.setDateIntroduced(intro);
			computer.setDateDiscontinued(datefin);
			Company company1 = Database.getCompanyDao().getCompanyById(companyid);
			computer.setCompagny(company1);
			
			if(intro != null && datefin != null &&  intro.compareTo(datefin) >=0){
				System.out.println("La date de fin de service ne peut pas être inferieur à la date de mise en service");
			}
			
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
		catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Aucun Ordinateur ne correspond à cette ID");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			System.out.println();
		}

	}

}
