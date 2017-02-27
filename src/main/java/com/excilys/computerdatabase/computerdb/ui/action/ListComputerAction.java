package com.excilys.computerdatabase.computerdb.ui.action;

import java.util.Scanner;

import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.Page;
import com.excilys.computerdatabase.computerdb.service.pages.PagesList;

public class ListComputerAction implements ActionMenu {

    public void doAction() {
        PagesList pagesList;
        ComputerService computerService = new ComputerService();
        pagesList = computerService.getComputers();

        Page page;

        do {
            page = pagesList.getPage();
            page.printContent();

            printFooter(pagesList);

        } while (readAction(pagesList));
    }

    public void printFooter(PagesList pagesList) {
        System.out.print("page " + pagesList.getPageIndex() + "/" + pagesList.getTotalPageNumber()
                + "Premiere page [first/f], Page Précédente [previous/p], Page Suivante  [next/n], Page Précédente [last/l], Retour [Back/B] : ");
    }

    private boolean readAction(PagesList pagesList) {
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
