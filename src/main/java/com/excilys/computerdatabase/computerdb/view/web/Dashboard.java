package com.excilys.computerdatabase.computerdb.view.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.computerdatabase.computerdb.model.dto.PageListComputerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.service.ComputerService;

public class Dashboard extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        PageListComputerDTO dtolistComputer = this.getJspAttribute(request, response);

        this.setJspAttribute(session, dtolistComputer);
        this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String selected = request.getParameter("selection");
        boolean result = deleteSelection(selected);


        if (!result )
            LOGGER.info("remove computers : " + result);
        doGet(request, response);
    }


    private PageListComputerDTO getJspAttribute(HttpServletRequest request, HttpServletResponse response) {
        long pageSize = 10;
        long pageNumber = 1;
        String pageSizeString = request.getParameter("pageSize");
        String pageNumberString = request.getParameter("pageNumber");
        String search = request.getParameter("search");

        try {
            pageSize = Long.parseLong(pageSizeString);
            pageNumber = Long.parseLong(pageNumberString);
        } catch (NumberFormatException e) {
            //LOGGER.error("error page parse size :" + pageSizeString + " number :" + pageNumberString    );
        }

        return ComputerService.INSTANCE.getComputerDTOList(search, pageSize, pageNumber);
    }

    private void setJspAttribute(HttpSession session, PageListComputerDTO dtolistComputer) {
        session.setAttribute("computersList", dtolistComputer.getComputerDTOList());
        session.setAttribute("pageSize", dtolistComputer.getPageSize());
        session.setAttribute("pageNumber", dtolistComputer.getPageNumber());
        session.setAttribute("totalPageNumber", dtolistComputer.getTotalPage());
        session.setAttribute("search", dtolistComputer.getFilter());
        session.setAttribute("totalRowNumber", dtolistComputer.getTotalRow());
    }

    private boolean deleteSelection(String selected){
        String[] idsStr = selected.split(",");
        /*if (idsStr.length > 1) {
            LOGGER.info("rm computers length: " + idsStr.length);
        }*/

        boolean result = ComputerService.INSTANCE.removeComputers(idsStr);

        return result;
    }

}
