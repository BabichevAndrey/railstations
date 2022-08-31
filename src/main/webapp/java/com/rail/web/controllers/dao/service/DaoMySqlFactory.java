package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.*;
import com.rail.web.controllers.dao.models.Ticket;
import com.rail.web.db.PoolcConnectionBuilder;


/**
 * @author Andrii Babichev
 * @version 1.0
 * This class is a factory that defines entities for their implementation
 */

public class DaoMySqlFactory extends DAOFactory {

    private UserDAO userDAO;
    private StationDAO stationDAO;
    private RouteDAO routeDAO;
    private TicketDAO ticketDAO;



    public synchronized UserDAO getUserDAO() {
        if (userDAO == null){
            userDAO = new UserImplDaoMySql(PoolcConnectionBuilder.getInstance().getDataSource());
        }
        return userDAO;
    }

    public synchronized StationDAO getStationDAO() {
        if (stationDAO == null){
            stationDAO = new StationImplDaoMySql(PoolcConnectionBuilder.getInstance().getDataSource());
        }
        return stationDAO;
    }


    public synchronized RouteDAO getRouteDAO() {
        if (routeDAO == null){
            routeDAO = new RouteImplDaoMySql(PoolcConnectionBuilder.getInstance().getDataSource());
        }
        return routeDAO;
    }

    public synchronized TicketDAO getTicketDAO() {
        if (ticketDAO == null){
            ticketDAO = new TicketImplDaoMySql(PoolcConnectionBuilder.getInstance().getDataSource());
        }
        return ticketDAO;
    }
}
