package com.excilys.computerdatabase.computerdb.model;

import com.excilys.computerdatabase.computerdb.service.pages.Pageable;

public class Company implements Pageable {

    private final long id;
    private final String name;

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

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "id : " + id + "\tname : " + name;
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
         * Build a Company.
         *
         * @return A built COmpany
         */
        public Company build() {
            return new Company(this);
        }

    }

}
