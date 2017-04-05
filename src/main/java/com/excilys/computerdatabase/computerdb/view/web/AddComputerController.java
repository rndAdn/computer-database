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

import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.view.ComputerFormValidator;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
    
    @Autowired
    ComputerService computerService;
    
    @Autowired
    CompanyService companyService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerController.class);
    
    ComputerFormValidator computerFormValidator;
    
    @Autowired
    public AddComputerController(ComputerFormValidator computerFormValidator) {
        this.computerFormValidator = computerFormValidator;
    }
    
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(computerFormValidator);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String get( ModelMap model) {
        
        List<CompanyDTO> list = companyService.getCompanyDTOList();
        model.addAttribute("companylist", list);
        
        ComputerDTO computerDTO = new ComputerDTO();
        model.addAttribute("computerFormAdd", computerDTO);
        
        return "addComputer";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    protected String post(ModelMap model, @ModelAttribute("computerFormAdd") @Validated ComputerDTO computerDTO, BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            LOGGER.info("hasError : " + computerDTO);
            return "addComputer";
        }
        LOGGER.info("Computer Demande Add : " + computerDTO);

        boolean add = computerService.ajoutComputer(computerDTO);
        if (!add) {
            LOGGER.info("Computer Add " + add);
        }
        redirectAttributes.addFlashAttribute("css", "success");
        return "redirect:/";
    }


}
