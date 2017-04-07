package com.excilys.computerdatabase.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.computerdb.dao.ComputerDao;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;

public class MapperComputer implements RowMapper{

    private final Logger LOGGER = LoggerFactory.getLogger(MapperComputer.class);

    /**
     * Get a Computer from a ResultSet.
     *
     * @param rset ResultSset of Computer from database.
     * @return A Computer
     * @throws SQLException Bad info in ResultSet
     */
    public static Computer mapComputer(ResultSet rset) throws SQLException {
        long id = rset.getLong("id");
        String name = rset.getString("name");
        LocalDate intro = null;
        LocalDate fin = null;
        Company company = null;

        try {
            intro = rset.getObject("introduced", LocalDate.class);
            fin = rset.getObject("discontinued", LocalDate.class);
        } catch (NullPointerException e) {
            // TODO : catch vide
        }
        long companyId = rset.getLong("company_id");
        if (!rset.wasNull()) {
            String nameComapny = rset.getString("company_name");
            company = new Company.CompanyBuilder(nameComapny).id(companyId).build();
        }
        Computer computer = new Computer.ComputerBuilder(name).id(id).dateIntroduced(intro).dateDiscontinued(fin)
                .company(company).build();
        return computer;
    }

    @Override
    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
        long companyId = rs.getLong("company_id");
        Company company = null;
        if (!rs.wasNull()) {
            String nameComapny = rs.getString("company_name");
            company = new Company.CompanyBuilder(nameComapny).id(companyId).build();
        }
        Optional<LocalDate> intro = Optional.empty();
        Optional<LocalDate> fin = Optional.empty();

        try {
            intro = Optional.ofNullable(rs.getObject("introduced", LocalDate.class));
        } catch (NullPointerException e) {
            intro = Optional.empty();
        }
        try {
            fin = Optional.ofNullable(rs.getObject("discontinued", LocalDate.class));
        } catch (NullPointerException e) {
            fin = Optional.empty();
        }
        Computer computer = new Computer.ComputerBuilder(rs.getString("name"))
                .id(rs.getLong("id"))
                .dateIntroduced(intro)
                .dateDiscontinued(fin)
                .company(company)
                .build();
        return computer;
    }


}
