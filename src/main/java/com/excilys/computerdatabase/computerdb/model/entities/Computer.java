package com.excilys.computerdatabase.computerdb.model.entities;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.computerdb.model.Utils;

/**
 * @author renaud
 *
 */

@Entity
@Table(name = "COMPUTER")
public class Computer implements Pageable {

    private final long id;
    private final String name;
    private final LocalDate dateIntroduced;
    private final LocalDate dateDiscontinued;
    private final Company company;

    /**
     * Computer Constructor.
     *
     * @param computerBuilder
     *            .
     */
    public Computer(ComputerBuilder computerBuilder) {
        this.id = computerBuilder.id;
        this.name = computerBuilder.name;
        this.dateIntroduced = computerBuilder.dateIntroduced;
        this.dateDiscontinued = computerBuilder.dateDiscontinued;
        this.company = computerBuilder.company;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    public long getId() {
        return id;
    }
    
    @Column(name = "name", unique = false, nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public Optional<LocalDate> getDateIntroduced() {
        return Optional.ofNullable(dateIntroduced);

    }

    public Optional<LocalDate> getDateDiscontinued() {
        return Optional.ofNullable(dateDiscontinued);
    }

    public Optional<Company> getCompany() {
        return Optional.ofNullable(company);
    }

    public Long getCompanyId() {
        return ((company != null) ? company.getId() : null);
    }

    
    
    
    
    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", dateIntroduced=" + dateIntroduced + ", dateDiscontinued="
                + dateDiscontinued + ", company=" + company + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((dateDiscontinued == null) ? 0 : dateDiscontinued.hashCode());
        result = prime * result + ((dateIntroduced == null) ? 0 : dateIntroduced.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (dateDiscontinued == null) {
            if (other.dateDiscontinued != null) {
                return false;
            }
        } else if (!dateDiscontinued.equals(other.dateDiscontinued)) {
            return false;
        }
        if (dateIntroduced == null) {
            if (other.dateIntroduced != null) {
                return false;
            }
        } else if (!dateIntroduced.equals(other.dateIntroduced)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }


    public static class ComputerBuilder {
        
        private static final Logger LOGGER = LoggerFactory.getLogger(ComputerBuilder.class);
        private long id = -1;
        private final String name;
        private LocalDate dateIntroduced;
        private LocalDate dateDiscontinued;
        private Company company;

        /**
         * Construct a ComputerBuilder with only the required value.
         *
         * @param name
         *            name of computer
         */
        public ComputerBuilder(String name) {
            this.name = name;
        }

        /**
         * Add the optional date of introduction.
         *
         * @param dateIntroduced
         *            .
         * @return itself
         */
        public ComputerBuilder dateIntroduced(LocalDate dateIntroduced) {
            this.dateIntroduced = dateIntroduced;
            return this;
        }
        
        public ComputerBuilder dateIntroduced(String dateIntroduced) {
            if(StringUtils.isBlank(dateIntroduced)) {
                return this;
            }
            LOGGER.warn(dateIntroduced);
            this.dateIntroduced = Utils.stringToDate(dateIntroduced).get();
            return this;
        }
        
        public ComputerBuilder dateDiscontinued(String dateDiscontinued) {
            if(StringUtils.isBlank(dateDiscontinued)) {
                return this;
            }
            this.dateDiscontinued = Utils.stringToDate(dateDiscontinued).get();
            return this;
        }
        
        public ComputerBuilder dateIntroduced(Optional<LocalDate> dateIntroduced) {
            if(! dateIntroduced.isPresent()) {
                return this;
            }
            return dateIntroduced(dateIntroduced.get());
        }
        
        public ComputerBuilder dateDiscontinued(Optional<LocalDate> dateDiscontinued) {
            if(! dateDiscontinued.isPresent()) {
                return this;
            }
            return dateDiscontinued(dateDiscontinued.get());
        }

        /**
         * Add the optional id of the computer.
         *
         * @param id
         *            .
         * @return itself
         */
        public ComputerBuilder id(long id) {
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
        public ComputerBuilder dateDiscontinued(LocalDate dateDiscontinued) {
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
        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * Build the Computer.
         *
         * @return A built Computer
         */
        public Computer build() {
            return new Computer(this);
        }
    }

}
