package com.rail.web.controllers.dao.exception;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class describes a database abort error during a transaction in DAO layer
 */

public class DAOCloseTransactionConnection extends DAOException {
    public DAOCloseTransactionConnection(String s, Throwable e) {
    }
}
