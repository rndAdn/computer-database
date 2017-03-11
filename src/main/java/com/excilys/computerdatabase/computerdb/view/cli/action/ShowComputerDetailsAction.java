package com.excilys.computerdatabase.computerdb.view.cli.action;

import java.util.Optional;
import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.dao.ComputerDao;
import com.excilys.computerdatabase.computerdb.dao.DaoException;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;

public class ShowComputerDetailsAction implements ActionMenu {

    @Override
    public void executeAction() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez l'id de l'ordinateur : ");
        String idString = sc.nextLine();

        try {
            long id = Utils.stringToId(idString);

            Optional<Computer> optionalComputer = ComputerDao.INSTANCE.getComputerById(id);
            if (!optionalComputer.isPresent()) {
                System.out.println("Ordinateur introuvable dans la base de donnée");
                return;
            }

            System.out.println(optionalComputer.get().getDetail());
        } catch (DaoException e) {
            System.out.println();
            System.out.println();
            System.out.print(e.getMessage());
            System.out.println(" Abandon");
        } finally {
            System.out.println();
        }

    }

}
