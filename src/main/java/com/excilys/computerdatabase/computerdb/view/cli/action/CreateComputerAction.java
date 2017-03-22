package com.excilys.computerdatabase.computerdb.view.cli.action;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.model.controller.ControllerComputer;
import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

public class CreateComputerAction implements ActionMenu {
    

    @Override
    public void executeAction() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez le nom de l'ordinateur (Obligatoire): ");
        String name = sc.nextLine();

        boolean blankName = StringUtils.isBlank(name);
        if (blankName) {
            System.out.println("Le nom de l'ordinateur n'est pas valide");
            return;
        }

        System.out.print("Entrez la date d'introdution (jj-mm-aaaa): ");
        String dateIntroString = sc.nextLine();

        System.out.print("Entrez la date de fin de service (jj-mm-aaaa) : ");
        String dateFinServiceString = sc.nextLine();

        Optional<LocalDate> dateIntro = Utils.stringToDate(dateIntroString);
        Optional<LocalDate> dateFin = Utils.stringToDate(dateFinServiceString);

        boolean checkIntervalDate = ControllerComputer.CONTROLLER_COMPUTER.compareDate(dateIntro, dateFin);

        if (!checkIntervalDate) {
            System.out.println("Les dates ne sont pas valides");
            return;
        }

        System.out.print("Entrez l'id de la companie : ");
        String companyIdString = sc.nextLine();

        Optional<Company> optionalCompany = Optional.empty();
        if (!StringUtils.isBlank(companyIdString)) {
            long companyid = Utils.stringToId(companyIdString);
            optionalCompany = companyService.getCompanyByid(companyid);
        }

        Computer computer;
        computer = new Computer.ComputerBuilder(name).dateIntroduced(dateIntro.orElse(null))
                .dateDiscontinued(dateFin.orElse(null)).company(optionalCompany.orElse(null)).build();

        System.out.println(computer.getDetail());

        System.out.print("Ajouter ? [O/n]");
        String reponse = sc.nextLine();
        System.out.println();

        if (reponse.equalsIgnoreCase("n")) {
            System.out.print("Ordinateur non ajouté");
            return;
        }
        ComputerService.INSTANCE.ajoutComputer(computer);
        System.out.println("Ordinateur ajouté");

    }

}
