package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to add a new route between two stations</strong>.
 * To add a route, there should be information about the train number, departure time and arrival time,
 * departure station and arrival station, ticket price and number of empty seats.
 * Upon successful addition of a route - stay on the add route page.
 */

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddNewWay implements Command {

    private final DAOFactory daoFactory;

    public AddNewWay(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(AddNewWay.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String wayTrainId = req.getParameter("add_way_train_id");
        req.getSession().setAttribute("wayTrainId", wayTrainId);

        String wayTimeDep = req.getParameter("add_way_dep_time").replace("T", " ");;
        req.getSession().setAttribute("wayTimeDep", wayTimeDep);

        String wayTimeArrival = req.getParameter("add_way_arrival_time").replace("T", " ");
        req.getSession().setAttribute("wayTimeArrival", wayTimeArrival);

        String wayStationDep = req.getParameter("add_way_dep_station");
        req.getSession().setAttribute("wayStationDep", wayStationDep);

        String wayStationArrival = req.getParameter("add_way_arrival_station");
        req.getSession().setAttribute("wayStationArrival", wayStationArrival);

        String wayTicketPrice = req.getParameter("add_way_ticket_price");
        req.getSession().setAttribute("wayTicketPrice", wayTicketPrice);

        String wayTicketFree = req.getParameter("add_way_ticket_free");
        req.getSession().setAttribute("wayTicketFree", wayTicketFree);

        if (wayTrainId.isEmpty()) {req.getSession().setAttribute("addWayMessage", "trainenumberempty"); return "addnewway";}
        if (wayTimeDep.isEmpty()) {req.getSession().setAttribute("addWayMessage", "deptimeempty"); return "addnewway";}
        if (wayTimeArrival.isEmpty()) {req.getSession().setAttribute("addWayMessage", "arrivaltimeempty"); return "addnewway";}
        if (wayStationDep.isEmpty()) {req.getSession().setAttribute("addWayMessage", "depstationempty"); return "addnewway";}
        if (wayStationArrival.isEmpty()) {req.getSession().setAttribute("addWayMessage", "arrivalstation"); return "addnewway";}
        if (wayTicketPrice.isEmpty()) {req.getSession().setAttribute("addWayMessage", "ticketpriceempty"); return "addnewway";}
        if (wayTicketFree.isEmpty()) {req.getSession().setAttribute("addWayMessage", "freeplacesempty"); return "addnewway";}

        Route route = new Route();
        route.setTraineNumber(Integer.parseInt(wayTrainId));
        route.setDateStart(wayTimeDep);
        route.setDateArrival(wayTimeArrival);
        route.setStationStartName(wayStationDep);
        route.setStationArrivalName(wayStationArrival);
        route.setRoutePrice(Integer.parseInt(wayTicketPrice));
        route.setFreePlaces(Integer.parseInt(wayTicketFree));

        try {
            daoFactory.getRouteDAO().addNew(route);
            req.getSession().setAttribute("addWayMessage", "wayhasbeenadded");
            LOG.debug("New way has been added");
        } catch (DAOException ex){
            req.getSession().setAttribute("addWayMessage", "Way has not been added!");
            LOG.error("SQL exception at adding new way");
        }


        return "addnewway";

    }

}