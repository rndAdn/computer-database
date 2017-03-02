package com.excilys.computerdatabase.computerdb.ui.cli;

import java.util.Scanner;

public class CliMenu {

    /**
     * Print CLI menu.
     *
     */
    public static void showMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("--------MENU-------------");
            for (MenuEnum menu : MenuEnum.values()) {
                System.out.println(menu);
            }
            System.out.println("-------------------------");
            System.out.print("Action : ");

            String s = sc.nextLine();
            System.out.println();
            for (MenuEnum menu : MenuEnum.values()) {
                if (menu.isEntry(s)) {
                    menu.getAction().executeAction();
                }
            }
        }
    }

}
