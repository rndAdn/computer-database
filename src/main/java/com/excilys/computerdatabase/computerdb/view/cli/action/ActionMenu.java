package com.excilys.computerdatabase.computerdb.view.cli.action;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerdatabase.computerdb.dao.SpringConfig;
import com.excilys.computerdatabase.computerdb.service.CompanyService;

public interface ActionMenu {
    
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    CompanyService companyService = context.getBean(CompanyService.class);

    /**
     * Execute a Cli action (create computer, update, ... ).
     */
    void executeAction();

}
