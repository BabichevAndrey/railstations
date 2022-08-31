package com.rail.web.controllers.dao.exception;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class describes a transaction error in DAO layer
 */


public class DAOTransactionSQLException extends DAOException {
    public DAOTransactionSQLException(String s, Throwable ex) {
    }
}
