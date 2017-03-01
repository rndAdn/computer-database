package com.excilys.computerdatabase.computerdb.ui.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.CompanyDTO;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListCompany;

public class AddComputer extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CompanyDTO> list = getCompanyList();
        request.setAttribute("companylist", list);

        this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("computerName");
        String dateIntro = request.getParameter("introduced");
        String dateFin = request.getParameter("discontinued");
        String company = request.getParameter("companyId");

        System.out.println("name " + name);
        System.out.println("dateIntro " + dateIntro);
        System.out.println("dateFin " + dateFin);
        System.out.println("company " + company);
        System.out.println(request.getParameter("addComputer"));
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

}