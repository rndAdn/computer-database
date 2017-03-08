package com.excilys.computerdatabase.computerdb.view.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

public class AddComputer extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CompanyDTO> list = CompanyService.INSTANCE.getCompanyDTOList();
        request.setAttribute("companylist", list);

        this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("computerName");
        String dateIntro = request.getParameter("introduced");
        String dateFin = request.getParameter("discontinued");
        String company = request.getParameter("company");
        //LOGGER.info("Computer Demande Add : " + company);

        boolean add = addComputer(name, dateIntro, dateFin, company);

        if (add) {
            LOGGER.info("Computer Add OK");
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
        //this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }


    private boolean addComputer(String name, String dateIntroStr, String dateFinStr, String company) {

        String companyId, companyName;
        String[] companyInfo = company.split(":");
        companyId = companyInfo[0];
        companyName = companyInfo[1];
        ComputerService.INSTANCE.ajoutComputer(name, dateIntroStr, dateFinStr, companyId, companyName);
        return true;
    }

}