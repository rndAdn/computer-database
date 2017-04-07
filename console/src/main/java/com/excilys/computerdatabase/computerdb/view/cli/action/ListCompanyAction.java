package com.excilys.computerdatabase.computerdb.view.cli.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.entities.Page;
import com.excilys.computerdatabase.computerdb.model.entities.Pageable;
import com.excilys.computerdatabase.computerdb.service.CompanyService;

public class ListCompanyAction implements ActionMenu {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListCompanyAction.class);

    @Override
    public void executeAction() {
        Optional<Page> page;
        page = companyService.getCompanys();

        List<Pageable> list = new ArrayList<>();
        
        if(page.isPresent()) {
            list = page.get().getListe();
            do {
                //page.printContent();
                for (Pageable p : list) {
                    System.out.println(p);
                }
                printFooter(page.get());

            } while (readAction(page.get()));
        }

        
    }

    /**
     * Show footer of pagesList in CLI.
     *
     * @param pagesList
     *            .
     */
    public void printFooter(Page pagesList) {
        System.out.print("page " + pagesList.getPageNumber() + "/" + pagesList.getTotalNumberOfPage()
                + "Premiere page [first/f], Page Précédente [previous/p], Page Suivante  [next/n], Page Précédente [last/l], Retour [Back/B] : ");
    }

    /**
     * Read user input in pageList (next page, ...).
     *
     * @param pagesList
     *            .
     * @return true if input is correct
     */
    private boolean readAction(Page pagesList) {
        Scanner sc = new Scanner(System.in);

        String action = sc.nextLine();

        /*if (action.equalsIgnoreCase("next") || action.equalsIgnoreCase("n")) {
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
        return true;*/
        return false;

    }

}
