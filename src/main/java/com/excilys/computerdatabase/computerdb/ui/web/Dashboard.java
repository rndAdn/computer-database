package com.excilys.computerdatabase.computerdb.ui.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.PagesList;

public class Dashboard extends HttpServlet {

    long pageSize = 10;
    long pageNumber = 1;
    long totalPageNumber = 1;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pageSizeString = request.getParameter("pageSize");
        String pageNumberString = request.getParameter("pageNumber");

        try {
            pageSize = Long.parseLong(pageSizeString);
            pageNumber = Long.parseLong(pageNumberString);
            totalPageNumber = Long.parseLong(pageNumberString);
        } catch (NumberFormatException e) {

        }

        PagesList pagesList = new ComputerService().getComputers();
        totalPageNumber = pagesList.getTotalPageNumber();

        request.setAttribute("totalRowNumber", pagesList.getTotalRow());
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        pagesList.setPageIndex(pageNumber);

        System.out.println("pageSize " + pageSize);
        System.out.println("pageNumber " + pageNumber);
        System.out.println("totalPageNumber " + totalPageNumber);

        this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

    }
}
