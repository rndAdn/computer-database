package com.excilys.computerdatabase.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.computerdb.model.entities.Company;

public class MapperCompany {

    /**
     * Get a company from a ResultSet.
     *
     * @param rset
     *            ResultSset of Company from database.
     * @return A Company
     * @throws SQLException
     *             Bad info in ResultSet
     */
    public static Company mapCompany(ResultSet rset) throws SQLException {
        long id = rset.getLong("id");
        String name = rset.getString("name");
        Company company = new Company.CompanyBuilder(name).id(id).build();
        return company;
    }

}
