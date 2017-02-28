package com.excilys.computerdatabase.computerdb.model;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public class Computer implements Pageable {

    private final long id;
    private final String name;
    private final LocalDate dateIntroduced;
    private final LocalDate dateDiscontinued;
    private final Company company;

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

    public String toString() {
        return "" + id + "\t" + name;
    }

    public String getDetail() {
        // String s = String.format("Id : %4d Nom : %15s Date Introduction :
        // %10t Date fin : %10t Company : %10s", id, name, ((dateIntroduced !=
        // null)?dateIntroduced:"NC"), ((dateDiscontinued !=
        // null)?dateDiscontinued:"NC"), ((company!=
        // null)?company.getName():"NC"));

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

    public static class ComputerBuilder {
        private long id;
        private final String name;
        private LocalDate dateIntroduced;
        private LocalDate dateDiscontinued;
        private Company company;

        public ComputerBuilder(String name) {
            this.name = name;
        }

        public ComputerBuilder dateIntroduced(LocalDate dateIntroduced) {
            this.dateIntroduced = dateIntroduced;
            return this;
        }

        public ComputerBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ComputerBuilder dateDiscontinued(LocalDate dateDiscontinued) {
            this.dateDiscontinued = dateDiscontinued;
            return this;
        }

        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

}
