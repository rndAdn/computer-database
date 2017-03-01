package com.excilys.computerdatabase.computerdb;

import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.ui.cli.MenuEnum;

/**
 * @author renaud
 *
 */
/**
 * @author renaud
 *
 */
public class App {

    /**
     * Main method for Command Line Interface.
     *
     * @param args Main Args.
     */
    public static void main(String[] args) {
        showMenu();
    }

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
                    menu.getAction().doAction();
                }
            }
        }
    }
}
