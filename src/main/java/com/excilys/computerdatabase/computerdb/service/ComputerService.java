package com.excilys.computerdatabase.computerdb.service;

import java.util.Optional;

import com.excilys.computerdatabase.computerdb.database.ComputerDao;
import com.excilys.computerdatabase.computerdb.database.DaoException;
import com.excilys.computerdatabase.computerdb.model.Computer;
import com.excilys.computerdatabase.computerdb.model.Utils;
import com.excilys.computerdatabase.computerdb.model.dto.CompanyDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTOMapper;
import com.excilys.computerdatabase.computerdb.service.pages.PagesListComputer;
import com.excilys.computerdatabase.computerdb.ui.controller.ControllerComputer;

public enum ComputerService {
    
    INSTANCE;
    /**
     * Insert a Computer in database given a Computer.
     *
     * @param computer
     *            Representation of the computer to create
     * @return true if the computer is created in database
     * @throws DaoException
     *             .
     */
    public boolean ajoutComputer(Computer computer) {
        boolean result = false;
        try {
            result = ComputerDao.INSTANCE.insertComputer(computer);
        } catch (DaoException e) {
            System.out.println();
            System.out.println();
            System.out.print(e.getMessage());
            System.out.println(" Abandon de la création");
        } finally {
            System.out.println();

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
     * @param computer
     *            Representation of the computer to delete
     * @return true if computer is delete false otherwise
     * @throws DaoException
     *             .
     */
    public boolean deleteComputer(Computer computer) {
        boolean result = false;
        try {
            result = ComputerDao.INSTANCE.deleteComputer(computer);
        } catch (DaoException e) {
            System.out.println();
            System.out.println();
            System.out.print(e.getMessage());
            System.out.println(" Abandon de la suppression");
        } finally {
            System.out.println();

        }
        return result;

    }

    /**
     * Get a Computer from database by it's id.
     *
     * @param id
     *            Computer id in Database.
     * @return A Optional Computer. empty if the Computer doesn't exist in the
     *         database.
     * @throws DaoException
     *             .
     */
    public Optional<Computer> getComputerById(long id) {
        Optional<Computer> optionalComputer = Optional.empty();
        try {
            optionalComputer = ComputerDao.INSTANCE.getComputerById(id);
        } catch (DaoException e) {
            System.out.println();
            System.out.println();
            System.out.print(e.getMessage());
            System.out.println(" Abandon de la supression");
        } finally {
            System.out.println();
        }
        return optionalComputer;
    }

    /**
     * Get a Computer from database by it's name.
     *
     * @param name
     *            of computer in Database.
     * @return PagesListComputer.
     * @throws DaoException
     *             .
     */
    public PagesListComputer getComputerByName(String name) {
        PagesListComputer pagesList = new PagesListComputer();
        try {
            long nbComputer = ComputerDao.INSTANCE.countComputers();

            pagesList.setTotalNumberOfRow(nbComputer);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return pagesList;
    }

    /**
     * Update a Computer in database given a Computer.
     *
     * @param computer
     *            Representation of the computer to update
     * @return true if the computer is update in database
     * @throws DaoException
     *             .
     */
    public boolean updateComputer(Computer computer) {
        boolean result = false;
        try {
            result = ComputerDao.INSTANCE.updateComputer(computer);
        } catch (DaoException e) {
            System.out.println();
            System.out.println();
            System.out.print(e.getMessage());
            System.out.println(" Abandon de la mise à jour");
        } finally {
            System.out.println();

        }
        return result;
    }

    /**
     * Get all Computer from database.
     *
     * @return a PagesListComputer
     * @throws DaoException
     *             .
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
}
