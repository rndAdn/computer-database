package com.excilys.computerdatabase.computerdb.view.cli.action;

import java.util.Scanner;

public class ExitAction implements ActionMenu {

    @Override
    public void executeAction() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Quitter l'application ? [o/N] : ");
        String reponse = sc.nextLine();
        if (reponse.equalsIgnoreCase("o")) {
            System.out.print("Application Fermée");
            System.exit(0);
        }

    }

}
