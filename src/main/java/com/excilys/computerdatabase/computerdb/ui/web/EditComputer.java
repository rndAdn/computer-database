package com.excilys.computerdatabase.computerdb.ui.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTOMapper;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTOMapper;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListCompany;
import org.apache.commons.lang.StringUtils;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.service.pages.Pageable;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListComputer;

public class EditComputer extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("computerId");

        List<CompanyDTO> list = getCompanyList();
        request.setAttribute("companylist", list);

        long id = -1;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e){

        }
        Optional<Computer> computerOptional = ComputerService.INSTANCE.getComputerById(id);
        Computer computer = computerOptional.get();
        ComputerDTO computerDTO = ComputerDTOMapper.mapperComputerDTO(computer);
        long cId = computerDTO.getCompany().getId();
        request.setAttribute("computer", computerDTO);
        request.setAttribute("companyId", cId);
        this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
    }

    private List<CompanyDTO> getCompanyList() {
        List<CompanyDTO> dtoList = new ArrayList<>();
        PagesListCompany pagesListCompany = CompanyService.INSTANCE.getCompanys();
        List<Pageable> list = pagesListCompany.getCurrentPage().getList();

        for (Pageable company : list) {
            Company c = (Company) company;
            dtoList.add(CompanyDTOMapper.mapperCompanyDTO(c));
        }
        return dtoList;
    }
}
