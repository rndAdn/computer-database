package com.excilys.computerdatabase.computerdb.ui.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.ComputerDTO;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListComputer;

public class EditComputer extends HttpServlet {
    long pageSize = 10;
    long pageNumber = 1;
    long totalPageNumber = 1;
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
            totalPageNumber = Long.parseLong(pageNumberString);
        } catch (NumberFormatException e) {

        }

        // System.out.println(pageNumber);
        PagesListComputer pagesList = new ComputerService().getComputers();
        System.out.println("pageSize " + pageSize);
        System.out.println("pageNumber " + pageNumber);
        System.out.println("totalPageNumber " + totalPageNumber);
        pagesList.setRowByPages(pageSize);
        pagesList.setPageIndex(pageNumber);
        totalPageNumber = pagesList.getTotalPageNumber();
        List<Pageable> list;
        if (!StringUtils.isBlank(search)) {
            list = pagesList.getListFilterByName(search);
        } else {
            list = pagesList.getList();
        }

        List<ComputerDTO> dtoList = pageableListToComputerDTOList(list);

        request.setAttribute("totalRowNumber", pagesList.getTotalRow());
        request.setAttribute("computersList", dtoList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);

        this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);

    }

    private List<ComputerDTO> pageableListToComputerDTOList(List<Pageable> list) {
        List<ComputerDTO> dtoList = new ArrayList<>();
        for (Pageable computer : list) {
            Computer c = (Computer) computer;
            dtoList.add(new ComputerDTO(c));
        }

        return dtoList;
    }

}
