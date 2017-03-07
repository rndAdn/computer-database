package com.excilys.computerdatabase.computerdb.view.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.computerdb.model.dto.PageListComputerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.service.ComputerService;

public class Dashboard extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);
    long pageSize = 10;
    long pageNumber = 1;
    long totalPageNumber = 1;
    long nbItem = -1;
    String search;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getJspAttribute(request, response);
        PageListComputerDTO dtolistComputer = ComputerService.INSTANCE.getComputerDTOList(search, pageSize, pageNumber);
        totalPageNumber = dtolistComputer.getTotalPage();
        nbItem = dtolistComputer.getTotalRow();
        this.setJspAttribute(request, response, dtolistComputer);
        this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        String selected = request.getParameter("selection");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selected = request.getParameter("selection");
        String[] idsStr = selected.split(",");
        boolean result = ComputerService.INSTANCE.removeComputers(idsStr);
        LOGGER.info("remove computers : " + result);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }


    private void getJspAttribute(HttpServletRequest request, HttpServletResponse response) {
        String pageSizeString = request.getParameter("pageSize");
        String pageNumberString = request.getParameter("pageNumber");
        search = request.getParameter("search");

        try {
            pageSize = Long.parseLong(pageSizeString);
            pageNumber = Long.parseLong(pageNumberString);
        } catch (NumberFormatException e) {

        }
    }

    private void setJspAttribute(HttpServletRequest request, HttpServletResponse response, PageListComputerDTO dtolistComputer) {
        request.getSession().setAttribute("totalRowNumber", nbItem);
        request.getSession().setAttribute("computersList", dtolistComputer.getComputerDTOList());
        request.getSession().setAttribute("pageSize", pageSize);
        request.getSession().setAttribute("pageNumber", pageNumber);
        request.getSession().setAttribute("totalPageNumber", totalPageNumber);
        request.getSession().setAttribute("search", search);
    }


}
