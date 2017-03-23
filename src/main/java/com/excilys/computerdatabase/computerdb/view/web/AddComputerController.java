package com.excilys.computerdatabase.computerdb.view.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
    
    @Autowired
    ComputerService computerService;
    
    @Autowired
    CompanyService companyService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerController.class);
    
    
    @RequestMapping(method = RequestMethod.GET)
    public String get( ModelMap model) {
        
        List<CompanyDTO> list = companyService.getCompanyDTOList();
        model.addAttribute("companylist", list);
        
        return "addComputer";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView post(ModelMap model, 
            @RequestParam(value = "computerName", defaultValue = "") final String name,
            @RequestParam(value = "introduced", defaultValue = "") final String introduced,
            @RequestParam(value = "discontinued", defaultValue = "") final String discontinued,
            @RequestParam(value = "company", defaultValue = "") final String company) {

        LOGGER.info("Computer Demande Add : " + company);

        boolean add = addComputer(name, introduced, discontinued, company);
        if (!add) {
            LOGGER.info("Computer Add " + add);
        }

        return new ModelAndView("redirect:/");
    }


    private boolean addComputer(String name, String dateIntroStr, String dateFinStr, String company) {

        String companyId, companyName;
        String[] companyInfo = company.split(":");
        companyId = companyInfo[0];
        companyName = companyInfo[1];
        computerService.ajoutComputer(name, dateIntroStr, dateFinStr, companyId, companyName);
        return true;
    }

}
