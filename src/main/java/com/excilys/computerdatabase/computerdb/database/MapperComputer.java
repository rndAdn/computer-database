package com.excilys.computerdatabase.computerdb.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.computerdatabase.computerdb.model.Company;
import com.excilys.computerdatabase.computerdb.model.Computer;

public class MapperComputer {
    
    /**
     * Get a Computer from a ResultSet.
     *
     * @param rset
     *            ResultSset of Computer from database.
     * @return A Computer
     * @throws SQLException
     *             Bad info in ResultSet
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
            // LOGGER.warn("mapComputer date null dans la bd id : " +
            // computer.getDetail());
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

}
