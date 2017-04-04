package com.excilys.computerdatabase.computerdb.view.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.controller.ControllerCompany;
import com.excilys.computerdatabase.computerdb.model.controller.ControllerComputer;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;


@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
    
    @Autowired
    ComputerService computerService;
    
    @Autowired
    CompanyService companyService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);
    
    
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
            //long cId = computerDTO.getCompany().getId();
            //model.addAttribute("computer", computerDTO);
            //model.addAttribute("companyId", cId);
            return "editComputer";
            
        } else {
            return new ModelAndView("redirect:/");
        }
    }
    
    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView post(ModelMap model, @ModelAttribute("computerForm") @Validated ComputerDTO computerDTO/*, 
            @RequestParam(value = "computerId", defaultValue = "") final long computerId,
            @RequestParam(value = "computerName", defaultValue = "") final String computerName,
            @RequestParam(value = "introduced", defaultValue = "") final String introduced,
            @RequestParam(value = "discontinued", defaultValue = "") final String discontinued,
            @RequestParam(value = "company", defaultValue = "") final String company*/) {


        //LOGGER.info("update WEB : " + computerId + " : " + computerName + " : " + introduced + " : " + discontinued + " : " + company);
        LOGGER.info("update WEB : " + computerDTO);
        //boolean update = updateComputer(computerDTO);
        boolean update = computerService.updateComputer(computerDTO);
        if (!update) {
            LOGGER.info("Web update computer False : " + computerDTO.getId());
        }

        return new ModelAndView("redirect:/");
        
    }
    
    
    private boolean updateComputer(long id, String name, String dateIntroStr, String dateFinStr, String company) {
        if (!ControllerComputer.CONTROLLER_COMPUTER.isValideComputer(name, dateIntroStr, dateFinStr)) {
            LOGGER.info("Web update computer Check False : ");
            return false;
        }
        String companyId, companyName;
        if (company == null) {
            company = "0:--";
        }
        String[] companyInfo = company.split(":");
        companyId = companyInfo[0];
        companyName = companyInfo[1];
        CompanyDTO.CompanyDTOBuilder companyDTOBuilder = new CompanyDTO.CompanyDTOBuilder();
        CompanyDTO companyDTO;
        if (ControllerCompany.CONTROLLER_COMPANY.checkId(Utils.stringToId(companyId))) {
            companyDTOBuilder = companyDTOBuilder
                    .id(Utils.stringToId(companyId))
                    .name(companyName);
        }
        companyDTO = companyDTOBuilder.build();
        ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder(name)
                .id(id)
                .dateIntroduced(dateIntroStr)
                .dateDiscontinued(dateFinStr)
                .company(companyDTO)
                .build();

        return computerService.updateComputer(computerDTO);
    }

}
