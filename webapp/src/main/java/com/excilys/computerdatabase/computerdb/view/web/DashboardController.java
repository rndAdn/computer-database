package com.excilys.computerdatabase.computerdb.view.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.computerdb.model.dto.PageListComputerDTO;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

@Controller
@RequestMapping("/")
public class DashboardController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
    
    @Autowired
    ComputerService computerService;
    

    
    @RequestMapping(method = RequestMethod.GET)
    public String get(
            ModelMap model, 
            @RequestParam(value = "pageSize", defaultValue = "10")final long pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "1")final long pageNumber,
            @RequestParam(value = "orderBy", defaultValue = "id")final String orderBy,
            @RequestParam(value = "search", defaultValue = "")final String search
            ) {
        LOGGER.debug("Dashboard Get:");
        PageListComputerDTO dtolistComputer = computerService.getComputerDTOList(search, pageSize, pageNumber, orderBy);
        this.setJspAttribute(model, dtolistComputer);
        
        return "dashboard";
    }
    
    @RequestMapping(value="/dashboard", method = RequestMethod.GET)
    public ModelAndView getRedirect(ModelMap model) {
        return new ModelAndView("redirect:/");
    }
    

    @RequestMapping(method = RequestMethod.POST)
    public String post(
            ModelMap model, 
            @RequestParam(value = "selection", defaultValue = "")final String selected
            ) {
        LOGGER.debug("Dashboard delete selection:" + selected);
        boolean result = deleteSelection(selected);
        
        if (!result) {
            LOGGER.info("remove computers : " + result);
        }

        return "dashboard";
    }
    
    
  //Spring Security see this :
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

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
    
    private boolean deleteSelection(String selected) {
        String[] idsStr = selected.split(",");
        

        boolean result = computerService.removeComputers(idsStr);

        return result;
    }
}
