package com.rail.web.controllers.dao.exception;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class describes an error when adding a station with an existing name in DAO layer
 */


public class DuplicateStationExeption extends DAOException {
    public DuplicateStationExeption(String s, Throwable e) {
    }
}
