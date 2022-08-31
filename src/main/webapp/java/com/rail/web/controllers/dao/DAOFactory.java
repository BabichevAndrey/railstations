package com.rail.web.controllers.dao;

import com.rail.web.controllers.dao.service.DaoMySqlFactory;


/**
 * @author Andrii Babichev
 * @version 1.0
 * This class abstract factory. Selects the factory with which the dao layer should work.
 */

public abstract class DAOFactory{

    protected DAOFactory(){
    }

    public static final String MYSQL = "MYSQL";

    /**
     * select which factory will be working with DAO layer accoding initial settings
     * @param whichFactory
     */
    public static DAOFactory getDAOFactory(String whichFactory){

        switch (whichFactory){
            case  MYSQL: return new DaoMySqlFactory();
        }
        return null;
    }

    public abstract UserDAO getUserDAO();

    public abstract StationDAO getStationDAO();

    public abstract RouteDAO getRouteDAO();

    public abstract TicketDAO getTicketDAO();

}
