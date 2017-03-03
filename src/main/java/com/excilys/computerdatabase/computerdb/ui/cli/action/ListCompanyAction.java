package com.excilys.computerdatabase.computerdb.ui.cli.action;

import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.pages.Page;
import com.excilys.computerdatabase.computerdb.service.pages.Pages;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListCompanyAction implements ActionMenu {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListCompanyAction.class);

    @Override
    public void executeAction() {
        Pages pages;
        CompanyService companyService = new CompanyService();
        pages = companyService.getCompanys();

        Page page = pages.getCurrentPage();

        page.printContent();

        do {
            page.printContent();

            printFooter(pages);

        } while (readAction(pages));
    }

    /**
     * Show footer of pagesList in CLI.
     *
     * @param pagesList
     *            .
     */
    public void printFooter(Pages pagesList) {
        System.out.print("page " + pagesList.getPageIndex() + "/" + pagesList.getTotalNumberOfPage()
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
