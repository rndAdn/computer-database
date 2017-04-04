package com.excilys.computerdatabase.computerdb.dao;

public class DaoException extends Exception {

    byte code;
    /**
     * @param message exception message
     */
    public DaoException(byte code, String message) {
        super(message);
        this.code = code;
    }
    
    
    
    
    public byte getCode(){
        return code;
    }

}
