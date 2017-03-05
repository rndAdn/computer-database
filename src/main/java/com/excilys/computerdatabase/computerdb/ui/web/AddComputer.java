package com.excilys.computerdatabase.computerdb.ui.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.ComputerValidator;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTOMapper;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTOMapper;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListCompany;
import com.excilys.computerdatabase.computerdb.ui.controller.ControllerComputer;

public class AddComputer extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CompanyDTO> list = getCompanyList();
        request.setAttribute("companylist", list);

        this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("computerName");
        String dateIntro = request.getParameter("introduced");
        String dateFin = request.getParameter("discontinued");
        String company = request.getParameter("company");
        LOGGER.info("Computer Demande Add : " + company);
        
        boolean add = addComputer(name, dateIntro, dateFin, company);
        
        if (add) {
            LOGGER.info("Computer Add OK");
        }
    }

    private List<CompanyDTO> getCompanyList() {
        List<CompanyDTO> dtoList = new ArrayList<>();
        PagesListCompany pagesListCompany = CompanyService.INSTANCE.getCompanys();
        List<Pageable> list = pagesListCompany.getCurrentPage().getList();

        for (Pageable company : list) {
            Company c = (Company) company;
            dtoList.add(CompanyDTOMapper.mapperCompanyDTO(c));
        }
        return dtoList;
    }

    private boolean addComputer(String name, String dateIntroStr, String dateFinStr, String company) {

        if (! ControllerComputer.checkComputer(name, dateIntroStr, dateFinStr)) {
            return false;
        }
        String companyId, CompanyName;
        String [] companyInfo = company.split(":");
        companyId = companyInfo[0];
        CompanyName = companyInfo[1];
        
        CompanyDTO.CompanyDTOBuilder companyDTOBuilder = new CompanyDTO.CompanyDTOBuilder();
        CompanyDTO companyDTO;
        if (ControllerComputer.checkCompanyId(companyId)) {
            companyDTOBuilder = companyDTOBuilder
                    .id(Utils.stringToId(companyId))
                    .name(CompanyName);
        }
        companyDTO = companyDTOBuilder.build();
        
        ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder(name)
                .dateIntroduced(dateIntroStr)
                .dateDiscontinued(dateFinStr)
                .company(companyDTO)
                .build();

        Computer computer = ComputerDTOMapper.mapperComputerDTO(computerDTO);
        
        ComputerService.INSTANCE.ajoutComputer(computer);
        return true;
    }

}