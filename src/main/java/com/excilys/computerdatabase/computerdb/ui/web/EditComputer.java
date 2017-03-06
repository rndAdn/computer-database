package com.excilys.computerdatabase.computerdb.ui.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTOMapper;
import com.excilys.computerdatabase.computerdb.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.service.ComputerService;
import com.excilys.computerdatabase.computerdb.ui.controller.ControllerComputer;

public class EditComputer extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
    long id = -1;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("computerId");

        List<CompanyDTO> list = CompanyService.INSTANCE.getCompanyDTOList();
        request.setAttribute("companylist", list);

        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {

        }
        Optional<Computer> computerOptional = ComputerService.INSTANCE.getComputerById(id);
        Computer computer = computerOptional.get();
        ComputerDTO computerDTO = ComputerDTOMapper.mapperComputerDTO(computer);
        long cId = computerDTO.getCompany().getId();
        request.setAttribute("computer", computerDTO);
        request.setAttribute("companyId", cId);
        this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("computerName");
        String dateIntro = request.getParameter("computerIntroduced");
        String dateFin = request.getParameter("computerDiscontinued");
        String company = request.getParameter("company");

        LOGGER.info("Computer Demande Edit : " + name);
        LOGGER.info("Computer Demande Edit : " + dateIntro);
        LOGGER.info("Computer Demande Edit : " + dateFin);
        LOGGER.info("Computer Demande Edit : " + company);

        boolean update = updateComputer(name, dateIntro, dateFin, company);

        LOGGER.info("Web update computer : " + update);

        //this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private boolean updateComputer(String name, String dateIntroStr, String dateFinStr, String company) {

        if (!ControllerComputer.checkComputer(name, dateIntroStr, dateFinStr)) {
            return false;
        }
        String companyId, companyName;
        String[] companyInfo = company.split(":");
        companyId = companyInfo[0];
        companyName = companyInfo[1];

        CompanyDTO.CompanyDTOBuilder companyDTOBuilder = new CompanyDTO.CompanyDTOBuilder();
        CompanyDTO companyDTO;
        if (ControllerComputer.checkCompanyId(companyId)) {
            companyDTOBuilder = companyDTOBuilder
                    .id(Utils.stringToId(companyId))
                    .name(companyName);
        }
        companyDTO = companyDTOBuilder.build();

        ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder(name)
                .id(id)
                .dateIntroduced(dateIntroStr)
                .dateDiscontinued(dateFinStr)
                .company(companyDTO)
                .build();

        Computer computer = ComputerDTOMapper.mapperComputerDTO(computerDTO);

        ComputerService.INSTANCE.updateComputer(computer);
        return true;
    }
}
