package com.excilys.computerdatabase.computerdb.model;

import java.sql.Date;

import com.excilys.computerdatabase.computerdb.ui.pages.Pageable;

public class Computer implements Pageable {

	
	
	private long id;
	private String name;
	private Date dateIntroduced;
	private Date dateDiscontinued;
	private Company company;
	
	
	public Computer(){}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateIntroduced() {
		return dateIntroduced;
	}
	public void setDateIntroduced(Date dateIntroduced) {
		this.dateIntroduced = dateIntroduced;
	}
	public Date getDateDiscontinued() {
		return dateDiscontinued;
	}
	public void setDateDiscontinued(Date dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}
	public Company getCompany() {
		return this.company;
	}
	
	public Long getCompanyId() {
		return ((company!= null)?company.getId():null);
	}
	
	public void setCompagny(Company company) {
		this.company = company;
	}
	
	public String toString(){
		return "" + id + 
				"\t" + name;
	}
	
	public String getDetail(){
		//String s = String.format("Id : %4d Nom : %15s Date Introduction : %10t Date fin : %10t Company : %10s", id, name, ((dateIntroduced != null)?dateIntroduced:"NC"), ((dateDiscontinued != null)?dateDiscontinued:"NC"), ((company!= null)?company.getName():"NC"));
		
		return "Id : "+id + 
				"\tNom : " + name +
				"\tDate Introduction : " + ((dateIntroduced != null)?dateIntroduced:"NC") + 
				"\tDate fin : " + ((dateDiscontinued != null)?dateDiscontinued:"NC") +
				"\tCompany : " + ((company!= null)?company.getName():"NC"); 
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (dateDiscontinued == null) {
			if (other.dateDiscontinued != null)
				return false;
		} else if (!dateDiscontinued.equals(other.dateDiscontinued))
			return false;
		if (dateIntroduced == null) {
			if (other.dateIntroduced != null)
				return false;
		} else if (!dateIntroduced.equals(other.dateIntroduced))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}
