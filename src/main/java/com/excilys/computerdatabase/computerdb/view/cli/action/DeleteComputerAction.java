package com.excilys.computerdatabase.computerdb.view.cli.action;

import java.util.Optional;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.model.controller.ControllerComputer;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

public class DeleteComputerAction implements ActionMenu {

    @Override
    public void executeAction() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez l'id de l'ordinateur à supprimer : ");

        String idString = sc.nextLine();
        long id = Utils.stringToId(idString);

        boolean checkedId = ControllerComputer.CONTROLLER_COMPUTER.checkId(id);

        if (!checkedId) {
            System.out.println("L'id n'est pas valide");
            return;
        }

        Optional<Computer> optionalComputer = computerService.getComputerById(id);
        if (!optionalComputer.isPresent()) {
            System.out.println("Ordinateur introuvable dans la base de donnée");
            return;
        }

        Computer computer = optionalComputer.get();
        System.out.println(computer);

        System.out.print("Supprimer ? [O/n]");

        String reponse = sc.nextLine();
        System.out.println();

        if (reponse.equalsIgnoreCase("n")) {
            System.out.println(" Abandon de la suppression");
            return;
        }

        computerService.deleteComputer(computer);
        System.out.println("Ordinateur supprimé");
    }

}
