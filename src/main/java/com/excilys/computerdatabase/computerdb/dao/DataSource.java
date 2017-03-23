package com.excilys.computerdatabase.computerdb.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class DataSource  extends DriverManagerDataSource {
    
    
    private Logger  LOGGER = LoggerFactory.getLogger(DataSource.class);
    
    
    public DataSource() {  
        this.setDriverClassName("com.mysql.jdbc.Driver");
        this.setUrl("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull");
        this.setUsername("admincdb");
        this.setPassword("qwerty1234");
        
        LOGGER.debug("DS :" + this.toString());
    }

}
