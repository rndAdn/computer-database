package com.excilys.computerdatabase.computerdb.model;

import java.util.Optional;

public class CompanyDTO {
    private final long id;
    private final String name;

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
    }

    public CompanyDTO(Optional<Company> company) {
        if (company.isPresent()) {
            this.id = company.get().getId();
            this.name = company.get().getName();
        } else {
            this.id = -1;
            this.name = "-";
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
