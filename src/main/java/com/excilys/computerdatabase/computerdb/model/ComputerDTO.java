package com.excilys.computerdatabase.computerdb.model;

import java.time.LocalDate;


public class ComputerDTO {
    
    private final long id;
    private final String name;
    private final String dateIntroduced;
    private final String dateDiscontinued;
    private final CompanyDTO company;
    
    public ComputerDTO(Computer computer){
        this.id = computer.getId();
        this.name = computer.getName();
        if(computer.getDateIntroduced().isPresent()){
            this.dateIntroduced = computer.getDateIntroduced().get().toString(); 
        } else {
            this.dateIntroduced = "-";
        }
        
        if(computer.getDateDiscontinued().isPresent()){
            this.dateDiscontinued = computer.getDateDiscontinued().get().toString(); 
        } else {
            this.dateDiscontinued = "-";
        }
        company = new CompanyDTO(computer.getCompany());
    }



    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateIntroduced() {
        return dateIntroduced;
    }

    public String getDateDiscontinued() {
        return dateDiscontinued;
    }

    public CompanyDTO getCompany() {
        return company;
    }
    
    
    
    

}
