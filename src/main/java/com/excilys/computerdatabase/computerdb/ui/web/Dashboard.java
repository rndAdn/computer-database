package com.excilys.computerdatabase.computerdb.ui.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.ComputerDTO;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListComputer;

public class Dashboard extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);
    long pageSize = 10;
    long pageNumber = 1;
    long totalPageNumber = 1;
    long nbItem = -1;
    String search;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        search = "";
        String pageSizeString = request.getParameter("pageSize");
        String pageNumberString = request.getParameter("pageNumber");
        search = request.getParameter("search");

        try {
            pageSize = Long.parseLong(pageSizeString);
            pageNumber = Long.parseLong(pageNumberString);
        } catch (NumberFormatException e) {

        }

        List<ComputerDTO> dtoList = getComputerList();

        request.setAttribute("totalRowNumber", nbItem);
        request.setAttribute("computersList", dtoList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

    }

    private List<ComputerDTO> getComputerList() {
        List<ComputerDTO> dtoList = new ArrayList<>();
        ComputerService computerService = new ComputerService();
        PagesListComputer pagesListComputer = computerService.getComputers();
        pagesListComputer.setRowByPages(pageSize);
        pagesListComputer.setPageIndex(pageNumber);
        nbItem = pagesListComputer.getTotalRow();
        totalPageNumber = pagesListComputer.getTotalPageNumber();
        List<Pageable> list = pagesListComputer.getList();

        for (Pageable computer : list) {
            Computer c = (Computer) computer;
            dtoList.add(new ComputerDTO(c));
        }
        return dtoList;
    }

}
