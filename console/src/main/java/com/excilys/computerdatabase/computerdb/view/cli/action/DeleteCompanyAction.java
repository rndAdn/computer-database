package com.excilys.computerdatabase.computerdb.view.cli.action;

import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.validator.ValidatorCompanyModel;
import com.excilys.computerdatabase.computerdb.service.CompanyService;

import java.util.Optional;
import java.util.Scanner;


public class DeleteCompanyAction implements ActionMenu {

    @Override
    public void executeAction() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez l'id de la company à supprimer : ");

        String idString = sc.nextLine();
        long id = Utils.stringToId(idString);

        boolean checkedId = ValidatorCompanyModel.CONTROLLER_COMPANY.checkId(id);

        if (!checkedId) {
            System.out.println("L'id n'est pas valide");
            return;
        }

        Optional<Company> optionalCompany = companyService.getCompanyByid(id);
        if (!optionalCompany.isPresent()) {
            System.out.println("Company introuvable dans la base de donnée");
            return;
        }

        Company company = optionalCompany.get();
        System.out.println(company.toString());

        System.out.print("Supprimer ? [O/n]");

        String reponse = sc.nextLine();
        System.out.println();

        if (reponse.equalsIgnoreCase("n")) {
            System.out.println(" Abandon de la suppression");
            return;
        }

        long result = companyService.removeCompany(company);
        System.out.println("Computer Supprimé :" + result);
        System.out.println("company supprimé");
    }

}
