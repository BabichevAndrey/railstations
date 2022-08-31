package com.rail.web.controllers.dao.exception;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class describes an error when adding a user with an existing login in DAO layer
 */


public class DuplicateUserExeption extends DAOException {
    public DuplicateUserExeption(String s, Throwable e) {
    }
}