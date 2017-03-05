package com.excilys.computerdatabase.computerdb.model.dto;

public class CompanyDTO {
    private final long id;
    private final String name;

    /**
     * Constructor of a Company DTO with a company.
     *
     * @param company .
     */
    /*public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
    }*/

    /** Constructor of a Company DTO with a Optional company.
     * if Company is empty id is equals to -1 and name to "-"
     * @param company Optional Company
     */
    /*public CompanyDTO(Optional<Company> company) {
        if (company.isPresent()) {
            this.id = company.get().getId();
            this.name = company.get().getName();
        } else {
            this.id = -1;
            this.name = "-";
        }
    }*/
    
    private CompanyDTO(CompanyDTOBuilder company) {
        this.id = company.id;
        this.name = company.name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public static class CompanyDTOBuilder {
        private long id;
        private String name = "-";


        /**
         * Set the computer id.
         *
         * @param id
         *            of the Computer
         * @return Itself
         */
        public CompanyDTOBuilder id(long id) {
            this.id = id;
            return this;
        }
        
        public CompanyDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Build the Company.
         *
         * @return A built Company
         */
        public CompanyDTO build() {
            return new CompanyDTO(this);
        }

    }

}
