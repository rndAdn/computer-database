package com.excilys.computerdatabase.computerdb.view.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.controller.ControllerCompany;
import com.excilys.computerdatabase.computerdb.model.controller.ControllerComputer;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.view.ComputerFormValidator;


@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
    
    @Autowired
    ComputerService computerService;
    
    @Autowired
    CompanyService companyService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);
    
    
    ComputerFormValidator computerFormValidator;
    
    @Autowired
    public EditComputerController(ComputerFormValidator computerFormValidator) {
        this.computerFormValidator = computerFormValidator;
    }
    
    
  //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(computerFormValidator);
    }
    

    @RequestMapping(method = RequestMethod.GET)
    public Object get( ModelMap model, 
            @RequestParam(value = "computerId", defaultValue = "-1") final long id) {
        
        
        

        List<CompanyDTO> list = companyService.getCompanyDTOList();
        model.addAttribute("companylist", list);
        model.addAttribute("computerId", id);
        
        LOGGER.debug("computerId : '{}'", id);

        ComputerDTO computerDTO = computerService.getComputerDTOById(id);
        model.addAttribute("computerForm", computerDTO);
        if (ControllerComputer.CONTROLLER_COMPUTER.checkId(computerDTO.getId())) {
            return "editComputer";
            
        } else {
            return new ModelAndView("redirect:/");
        }
    }
    
    @RequestMapping(method = RequestMethod.POST)
    protected String post(ModelMap model, @ModelAttribute("computerForm") @Validated ComputerDTO computerDTO, BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {

        LOGGER.info("update WEB : " + computerDTO);
        boolean update = computerService.updateComputer(computerDTO);
        if (!update) {
            LOGGER.info("Web update computer False : " + computerDTO.getId());
            
        }
        if (bindingResult.hasErrors()) {
            return "/editComputer";
        }
        redirectAttributes.addFlashAttribute("css", "success");
        return "redirect:/";
        
    }
   

}
