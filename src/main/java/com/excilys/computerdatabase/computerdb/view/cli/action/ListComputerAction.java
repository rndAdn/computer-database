package com.excilys.computerdatabase.computerdb.view.cli.action;

import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.Page;
import com.excilys.computerdatabase.computerdb.service.pages.Pages;

public class ListComputerAction implements ActionMenu {

    @Override
    public void executeAction() {
        Pages pagesList;
        pagesList = ComputerService.INSTANCE.getComputers();

        Page page;

        do {
            pagesList.setOrderBy("name");
            page = pagesList.getCurrentPage();
            page.printContent();

            printFooter(pagesList);

        } while (readAction(pagesList));
    }

    /**
     * Show footer of pagesList in CLI.
     *
     * @param pagesList
     *            .
     */
    public void printFooter(Pages pages) {
        System.out.print("page " + pages.getPageIndex() + "/" + pages.getTotalNumberOfPage()
                + "Premiere page [first/f], Page Précédente [previous/p], Page Suivante  [next/n], Page Précédente [last/l], Retour [Back/B] : ");
    }

    /**
     * Read user input in pageList (next page, ...).
     *
     * @param pagesList
     *            .
     * @return true if input is correct
     */
    private boolean readAction(Pages pagesList) {
        Scanner sc = new Scanner(System.in);

        String action = sc.nextLine();

        if (action.equalsIgnoreCase("next") || action.equalsIgnoreCase("n")) {
            pagesList.nextPage();
        } else if (action.equalsIgnoreCase("previous") || action.equalsIgnoreCase("p")) {
            pagesList.previousPage();
        } else if (action.equalsIgnoreCase("first") || action.equalsIgnoreCase("f")) {
            pagesList.firstPage();
        } else if (action.equalsIgnoreCase("last") || action.equalsIgnoreCase("l")) {
            pagesList.lastPage();
        } else {
            return false;
        }
        return true;

    }

}
