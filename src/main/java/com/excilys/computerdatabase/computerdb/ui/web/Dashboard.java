package com.excilys.computerdatabase.computerdb.ui.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesList;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListComputer;

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
        
        
        //System.out.println(pageNumber);
        PagesListComputer pagesList = new ComputerService().getComputers();
        System.out.println("pageSize " + pageSize);
        System.out.println("pageNumber " + pageNumber);
        System.out.println("totalPageNumber " + totalPageNumber);
        pagesList.setRowByPages(pageSize);
        pagesList.setPageIndex(pageNumber);
        totalPageNumber = pagesList.getTotalPageNumber();
        List<Pageable> list = pagesList.getList();

        request.setAttribute("totalRowNumber", pagesList.getTotalRow());
        request.setAttribute("computersList", list);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        

        

        this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

    }
}
