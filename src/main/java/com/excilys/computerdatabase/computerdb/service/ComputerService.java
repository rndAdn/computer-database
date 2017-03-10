package com.excilys.computerdatabase.computerdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.model.mapper.ComputerDTOMapper;
import com.excilys.computerdatabase.computerdb.model.dto.PageListComputerDTO;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListComputer;
import com.excilys.computerdatabase.computerdb.controller.ControllerComputer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ComputerService {

    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    /**
     * Insert a Computer in database given a Computer.
     *
     * @param computer Representation of the computer to create
     * @return true if the computer is created in database
     * @throws DaoException .
     */
    public boolean ajoutComputer(Computer computer) {
        boolean result = false;
        try {
            result = ComputerDao.INSTANCE.insertComputer(computer);
        } catch (DaoException e) {
            LOGGER.error("ajout Computer");
        }
        return result;
    }

    public boolean ajoutComputer(String name, String dateIntroStr, String dateFinStr, String companyId, String companyName) {
        if (!ControllerComputer.checkComputer(name, dateIntroStr, dateFinStr)) {
            return false;
        }

        CompanyDTO.CompanyDTOBuilder companyDTOBuilder = new CompanyDTO.CompanyDTOBuilder();
        CompanyDTO companyDTO;
        if (ControllerComputer.checkCompanyId(companyId)) {
            companyDTOBuilder = companyDTOBuilder
                    .id(Utils.stringToId(companyId))
                    .name(companyName);
        }
        companyDTO = companyDTOBuilder.build();

        ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder(name)
                .dateIntroduced(dateIntroStr)
                .dateDiscontinued(dateFinStr)
                .company(companyDTO)
                .build();

        Computer computer = ComputerDTOMapper.mapperComputerDTO(computerDTO);

        return ajoutComputer(computer);
    }


    /**
     * Delete a Computer in database given a Computer.
     *
     * @param computer Representation of the computer to delete
     * @return true if computer is delete false otherwise
     * @throws DaoException .
     */
    public boolean deleteComputer(Computer computer) {
        boolean result = false;
        try {
            result = ComputerDao.INSTANCE.deleteComputer(computer);
        } catch (DaoException e) {
            LOGGER.error("delete Computer");
        }
        return result;

    }

    /**
     * Get a Computer from database by it's id.
     *
     * @param id Computer id in Database.
     * @return A Optional Computer. empty if the Computer doesn't exist in the
     * database.
     * @throws DaoException .
     */
    public Optional<Computer> getComputerById(long id) {
        Optional<Computer> optionalComputer = Optional.empty();
        try {
            optionalComputer = ComputerDao.INSTANCE.getComputerById(id);
        } catch (DaoException e) {
            LOGGER.error("getComputerById");
        }
        return optionalComputer;
    }

    /**
     * Get a Computer from database by it's name.
     *
     * @param name of computer in Database.
     * @return PagesListComputer.
     * @throws DaoException .
     */
    public PagesListComputer getComputerByName(String name) {
        PagesListComputer pagesList = new PagesListComputer();
        try {
            long nbComputer = ComputerDao.INSTANCE.countComputers();

            pagesList.setTotalNumberOfRow(nbComputer);
        } catch (DaoException e) {
            LOGGER.error("getComputerByName");
        }

        return pagesList;
    }

    /**
     * Update a Computer in database given a Computer.
     *
     * @param computer Representation of the computer to update
     * @return true if the computer is update in database
     * @throws DaoException .
     */
    public boolean updateComputer(Computer computer) {
        boolean result = false;
        try {
            result = ComputerDao.INSTANCE.updateComputer(computer);
        } catch (DaoException e) {
            LOGGER.error("updateComputer");
        }
        return result;
    }

    /**
     * Get all Computer from database.
     *
     * @return a PagesListComputer
     * @throws DaoException .
     */
    public PagesListComputer getComputers() {
        PagesListComputer pagesList = new PagesListComputer();
        try {
            long nbComputer = ComputerDao.INSTANCE.countComputers();
            pagesList.setTotalNumberOfRow(nbComputer);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return pagesList;
    }

    public PageListComputerDTO getComputerDTOList(String search, long pageSize, long pageNumber) {
        PagesListComputer pagesListComputer = ComputerService.INSTANCE.getComputers();
        pagesListComputer.setRowByPages(pageSize);
        pagesListComputer.setPageIndex(pageNumber);
        if (!StringUtils.isBlank(search)) {
            pagesListComputer.setFilter(search);
        }
        List<ComputerDTO> dtoList = ComputerDTOMapper.mapperPagelistComputerToDTO(pagesListComputer);
        PageListComputerDTO listComputerDTO = new PageListComputerDTO(pagesListComputer.getTotalNumberOfPage(), pagesListComputer.getTotalRow(), pagesListComputer.getPageIndex(), dtoList);
        return listComputerDTO;
    }

    public boolean removeComputers(String[] idsStr){ // TODO : create function in DAO to use rollBack if delete error
        for (String idStr : idsStr) {
            long id = Long.parseLong(idStr);
            Optional<Computer> computerOptional = getComputerById(id);
            if (!computerOptional.isPresent()) {
                LOGGER.error("delete computer not present : " + id);
                return false;
            }
            Computer computer = computerOptional.get();
            boolean deleted = deleteComputer(computer);
            if (!deleted) {
                LOGGER.error("delete computer : " + computer.getDetail());
                return false;
            }
        }

        return true;
    }


}
