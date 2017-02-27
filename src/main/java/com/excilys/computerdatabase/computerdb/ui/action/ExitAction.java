package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.Scanner;

public class ExitAction implements ActionMenu {

    public void doAction() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Quitter l'application ? [o/N] : ");
        String reponse = sc.nextLine();
        if (reponse.equalsIgnoreCase("o")) {
            System.out.print("Application Fermée");
            System.exit(0);
        }

    }

}
