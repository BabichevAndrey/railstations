package com.rail.web.controllers.dao.exception;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class is a main class of all exception in DAO layer
 */

public class DAOException extends Exception{

    public DAOException(){
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

}
