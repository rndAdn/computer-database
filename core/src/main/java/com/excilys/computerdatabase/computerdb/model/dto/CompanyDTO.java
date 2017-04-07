package com.excilys.computerdatabase.computerdb.model.dto;

public class CompanyDTO {
    private long id;
    private String name;

    /**
     * Constructor of a Company DTO with a companyDTOBuilder.
     *
     * @param company .
     */
    
    private CompanyDTO(CompanyDTOBuilder company) {
        this.id = company.id;
        this.name = company.name;
    }
    
    public CompanyDTO(){
        
    }
    

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class CompanyDTOBuilder {
        private long id = -1;
        private String name = "";


        /**
         * Set the computer id.
         *
         * @param id of the Computer
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
