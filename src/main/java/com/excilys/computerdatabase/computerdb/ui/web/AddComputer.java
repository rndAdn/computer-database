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

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.ComputerValidator;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListCompany;

public class AddComputer extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CompanyDTO> list = getCompanyList();
        request.setAttribute("companylist", list);

        this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("computerName");
        String dateIntro = request.getParameter("introduced");
        String dateFin = request.getParameter("discontinued");
        String company = request.getParameter("companyId");

        addComputer(name, dateIntro, dateFin, company);

        doGet(request, response);
    }

    private List<CompanyDTO> getCompanyList() {
        List<CompanyDTO> dtoList = new ArrayList<>();
        CompanyService companyService = new CompanyService();
        PagesListCompany pagesListCompany = companyService.getCompanys();
        List<Pageable> list = pagesListCompany.getList();

        for (Pageable company : list) {
            Company c = (Company) company;
            dtoList.add(new CompanyDTO(c));
        }
        return dtoList;
    }

    private boolean addComputer(String name, String dateIntroStr, String dateFinStr, String companyIdStr) {

        if (StringUtils.isBlank(name)) {
            return false;
        }

        Optional<LocalDate> dateIntro = Utils.stringToDate(dateIntroStr);
        Optional<LocalDate> dateFin = Utils.stringToDate(dateFinStr);

        if (!ComputerValidator.compareDate(dateIntro, dateFin)) {
            return false;
        }

        Optional<Company> optionalCompany = Optional.empty();
        if (!StringUtils.isBlank(companyIdStr)) {
            long companyid = Utils.stringToId(companyIdStr);
            optionalCompany = CompanyService.getCompanyByid(companyid);
        }

        Computer computer;
        computer = new Computer.ComputerBuilder(name).dateIntroduced(dateIntro.orElse(null))
                .dateDiscontinued(dateFin.orElse(null)).company(optionalCompany.orElse(null)).build();

        ComputerService.ajoutComputer(computer);
        return true;
    }

}