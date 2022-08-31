package com.rail.web.controllers.dao;


/**
 * @author Andrii Babichev
 * @version 1.0
 * This class sets the setting with which database the dao layer needs to work.
 */

public enum DAOCloudScape {

    DAOCLOUDSCAPE("MYSQL");

    private  String subd;

    DAOCloudScape(String s) {
        this.subd = s;
    }

    public String getSubd() {
        return subd;
    }
}
