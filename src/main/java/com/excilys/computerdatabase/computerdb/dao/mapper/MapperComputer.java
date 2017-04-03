package com.excilys.computerdatabase.computerdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.computerdb.dao.ComputerDao;
import com.excilys.computerdatabase.computerdb.model.entities.Company;
import com.excilys.computerdatabase.computerdb.model.entities.Computer;

public class MapperComputer implements RowMapper{



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
        Computer computer = new Computer.ComputerBuilder(rs.getString("name"))
                .id(rs.getLong("id"))
                .dateIntroduced(rs.getString("introduced"))
                .dateDiscontinued(rs.getString("discontinued"))
                .company(company)
                .build();
        return computer;
    }


}
