package com.excilys.computerdatabase.computerdb.view.cli.action;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.view.SpringConfiguration;

public interface ActionMenu {
    
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    CompanyService companyService = context.getBean(CompanyService.class);
    ComputerService computerService = context.getBean(ComputerService.class);

    /**
     * Execute a Cli action (create computer, update, ... ).
     */
    void executeAction();

}
