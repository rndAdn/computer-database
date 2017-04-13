package com.excilys.computerdatabase.computerdb.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ComputerDTO")
public class ComputerDTO implements Serializable{

    private long id;
    private String name;
    private String dateIntroduced;
    private String dateDiscontinued;
    private long companyId = -1;
    private String companyName = "";

    /**
     * Constructor of a Computer DTO with a computer.
     * replace every data with a string and empty optional by "-" String
     *
     * @param computer .
     */
    private ComputerDTO(ComputerDTOBuilder computer) {

        this.id = computer.id;
        this.name = computer.name;
        this.dateIntroduced = computer.dateIntroduced;
        this.dateDiscontinued = computer.dateDiscontinued;
       

    }
    
    public ComputerDTO(){
        
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

    public long getCompanyId() {
        return companyId;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDateIntroduced(String dateIntroduced) {
        this.dateIntroduced = dateIntroduced;
    }

    public void setDateDiscontinued(String dateDiscontinued) {
        this.dateDiscontinued = dateDiscontinued;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

   
    

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    

    

    
    

    @Override
    public String toString() {
        return "ComputerDTO [id=" + id + ", name=" + name + ", dateIntroduced=" + dateIntroduced + ", dateDiscontinued="
                + dateDiscontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
    }




    public static class ComputerDTOBuilder {
        private long id;
        private final String name;
        private String dateIntroduced = "";
        private String dateDiscontinued = "";
        private CompanyDTO company;

        /**
         * Construct a ComputerBuilder with only the required value.
         *
         * @param name name of computer
         */
        public ComputerDTOBuilder(String name) {
            this.name = name;
        }

        /**
         * Add the optional date of introduction.
         *
         * @param dateIntroduced .
         * @return itself
         */
        public ComputerDTOBuilder dateIntroduced(String dateIntroduced) {
            this.dateIntroduced = dateIntroduced;
            return this;
        }

        /**
         * Add the optional date of introduction.
         *
         * @param dateIntroduced .
         * @return itself
         */
        public ComputerDTOBuilder dateIntroduced(LocalDate dateIntroduced) {

            this.dateIntroduced = dateIntroduced.toString();
            return this;
        }

        /**
         * ComputerBuilder
         * Add the optional id of the computer.
         *
         * @param id .
         * @return itself
         */
        public ComputerDTOBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Add the optional end of service date of the computer.
         *
         * @param dateDiscontinued .
         * @return itself
         */
        public ComputerDTOBuilder dateDiscontinued(String dateDiscontinued) {
            this.dateDiscontinued = dateDiscontinued;
            return this;
        }

        /**
         * Add the optional company of the computer.
         *
         * @param company .
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
