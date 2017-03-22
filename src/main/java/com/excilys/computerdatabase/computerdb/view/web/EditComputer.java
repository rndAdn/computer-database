package com.excilys.computerdatabase.computerdb.view.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.computerdatabase.computerdb.model.controller.ControllerCompany;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerdatabase.computerdb.dao.SpringConfig;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.model.controller.ControllerComputer;

public class EditComputer extends Servlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
    
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    CompanyService companyService = context.getBean(CompanyService.class);
    ComputerService computerService = context.getBean(ComputerService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String idStr = request.getParameter("computerId");

        List<CompanyDTO> list = companyService.getCompanyDTOList();
        session.setAttribute("companylist", list);
        session.setAttribute("computerId", idStr);
        long id = -1;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            LOGGER.error("Edit Comp web doGet IdError " + idStr);
        }

        ComputerDTO computerDTO = computerService.getComputerDTOById(id);
        if (ControllerComputer.CONTROLLER_COMPUTER.checkId(computerDTO.getId())) {
            long cId = computerDTO.getCompany().getId();
            session.setAttribute("computer", computerDTO);
            session.setAttribute("companyId", cId);
            this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = (String) request.getSession().getAttribute("computerId");
        String name = request.getParameter("computerName");
        String dateIntro = request.getParameter("introduced");
        String dateFin = request.getParameter("discontinued");
        String company = request.getParameter("company");

        LOGGER.info("update WEB : " + idStr + " : " + name + " : " + dateIntro + " : " + dateFin + " : " + company);

        boolean update = updateComputer(idStr, name, dateIntro, dateFin, company);
        if (!update) {
            LOGGER.info("Web update computer False : " + idStr);
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private boolean updateComputer(String idStr, String name, String dateIntroStr, String dateFinStr, String company) {
        long id = -1;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            LOGGER.error("Edit Comp web doGet IdError " + idStr);
        }
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
