package com.excilys.computerdatabase.computerdb.model.dto;

import java.time.LocalDate;

public class ComputerDTO {

    private final long id;
    private final String name;
    private final String dateIntroduced;
    private final String dateDiscontinued;
    private final CompanyDTO company;

    /**
     * Constructor of a Computer DTO with a computer.
     * replace every data with a string and empty optional by "-" String
     * @param computer .
     */
    private ComputerDTO(ComputerDTOBuilder computer) {
        
        this.id = computer.id;
        this.name = computer.name;
        this.dateIntroduced = computer.dateIntroduced;
        this.dateDiscontinued = computer.dateDiscontinued;
        this.company =  computer.company;
        /*this.id = computer.getId();
        this.name = computer.getName();
        if (computer.getDateIntroduced().isPresent()) {
            this.dateIntroduced = computer.getDateIntroduced().get().toString();
        } else {
            this.dateIntroduced = "-";
        }

        if (computer.getDateDiscontinued().isPresent()) {
            this.dateDiscontinued = computer.getDateDiscontinued().get().toString();
        } else {
            this.dateDiscontinued = "-";
        }
        if(computer.getCompany().isPresent()){
            company = new CompanyDTO.CompanyDTOBuilder().id(computer.getCompany().get().getId()).name(computer.getCompany().get().getName() ).build();
        } else {
            company = new CompanyDTO.CompanyDTOBuilder().name("-").build();
        }*/
        
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
    
    
    public static class ComputerDTOBuilder {
        private long id;
        private final String name;
        private String dateIntroduced = "-";
        private String dateDiscontinued = "-";
        private CompanyDTO company;

        /**
         * Construct a ComputerBuilder with only the required value.
         *
         * @param name
         *            name of computer
         */
        public ComputerDTOBuilder(String name) {
            this.name = name;
        }

        /**
         * Add the optional date of introduction.
         *
         * @param dateIntroduced
         *            .
         * @return itself
         */
        public ComputerDTOBuilder dateIntroduced(String dateIntroduced) {
            this.dateIntroduced = dateIntroduced;
            return this;
        }
        
        /**
         * Add the optional date of introduction.
         *
         * @param dateIntroduced
         *            .
         * @return itself
         */
        public ComputerDTOBuilder dateIntroduced(LocalDate dateIntroduced) {
            this.dateIntroduced = dateIntroduced.toString();
            return this;
        }

        /**ComputerBuilder
         * Add the optional id of the computer.
         *
         * @param id
         *            .
         * @return itself
         */
        public ComputerDTOBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Add the optional end of service date of the computer.
         *
         * @param dateDiscontinued
         *            .
         * @return itself
         */
        public ComputerDTOBuilder dateDiscontinued(String dateDiscontinued) {
            this.dateDiscontinued = dateDiscontinued;
            return this;
        }

        /**
         * Add the optional company of the computer.
         *
         * @param company
         *            .
         * @return itself
         */
        public ComputerDTOBuilder company(CompanyDTO company) {
            this.company = company;
            return this;
        }

        /**
         * Build the Computer.
         *
         * @return A built Computer
         */
        public ComputerDTO build() {
            return new ComputerDTO(this);
        }
    }

}
