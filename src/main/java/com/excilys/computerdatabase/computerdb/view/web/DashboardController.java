package com.excilys.computerdatabase.computerdb.view.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.computerdb.model.dto.PageListComputerDTO;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

@Controller
@RequestMapping("/")
public class DashboardController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
    
    @Autowired
    ComputerService computerService;
    

    
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(
            ModelMap model, 
            @RequestParam(value = "pageSize", defaultValue = "10")final long pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "1")final long pageNumber,
            @RequestParam(value = "orderBy", defaultValue = "id")final String orderBy,
            @RequestParam(value = "search", defaultValue = "")final String search
            ) {
        
        PageListComputerDTO dtolistComputer = computerService.getComputerDTOList(search, pageSize, pageNumber, orderBy);
        this.setJspAttribute(model, dtolistComputer);
        
        return "dashboard";
    }
    
    
    private void setJspAttribute(ModelMap model, PageListComputerDTO dtolistComputer) {
        model.addAttribute("computersList", dtolistComputer.getComputerDTOList());
        model.addAttribute("pageSize", dtolistComputer.getPageSize());
        model.addAttribute("pageNumber", dtolistComputer.getPageNumber());
        model.addAttribute("totalPageNumber", dtolistComputer.getTotalPage());
        model.addAttribute("search", dtolistComputer.getFilter());
        model.addAttribute("totalRowNumber", dtolistComputer.getTotalRow());
        model.addAttribute("orderBy", dtolistComputer.getOrderBy());
    }
}
