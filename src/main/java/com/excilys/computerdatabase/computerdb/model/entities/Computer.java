package com.excilys.computerdatabase.computerdb.model.entities;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

/**
 * @author renaud
 *
 */
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

    public long getId() {
        return id;
    }

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

    /**
     * Get a String representation of Computer.
     *
     * @return String representation
     */
    public String getDetail() {
        return "Id : " + id + "\tNom : " + name + "\tDate Introduction : "
                + ((dateIntroduced != null) ? dateIntroduced : "NC") + "\tDate fin : "
                + ((dateDiscontinued != null) ? dateDiscontinued : "NC") + "\tCompany : "
                + ((company != null) ? company.getName() : "NC");
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

    public String toString2() {
        return "Computer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateIntroduced=" + dateIntroduced +
                ", dateDiscontinued=" + dateDiscontinued +
                ", company=" + company.toString2() +
                '}';
    }

    public static class ComputerBuilder {
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
