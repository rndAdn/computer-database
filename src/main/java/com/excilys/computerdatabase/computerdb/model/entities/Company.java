package com.excilys.computerdatabase.computerdb.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "company")
public class Company implements Pageable {
    
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;
    
    @Column(name = "name", unique = false, nullable = false, length = 20)
    private String name;

    /**
     * Company Constructor.
     *
     * @param companyBuilder
     *            .
     */
    private Company(CompanyBuilder companyBuilder) {
        this.id = companyBuilder.id;
        this.name = companyBuilder.name;
    }
    

    public Company() {

    }

    
    public long getId() {
        return id;
    }

    
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        Company other = (Company) obj;
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
    
    
    
    

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }





    public static class CompanyBuilder {
        private long id;
        private final String name;

        /**
         * create a CompanyBuilder with the name of the Company.
         *
         * @param name of the Company
         */
        public CompanyBuilder(String name) {
            this.name = name;
        }

        /**
         * Set the computer id.
         *
         * @param id
         *            of the Computer
         * @return Itself
         */
        public CompanyBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Build the Company.
         *
         * @return A built Company
         */
        public Company build() {
            return new Company(this);
        }

    }

}
