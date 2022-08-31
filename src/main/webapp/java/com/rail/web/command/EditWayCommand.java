package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to edit an existing route</strong>.
 * To edit a route, there should be information about the train number, departure time and arrival time,
 * departure station and arrival station, ticket price and number of empty seats.
 * Upon successful addition of a route - stay on the edit route page.
 */

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Route;
import com.rail.web.controllers.dao.models.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditWayCommand implements Command {

    private final DAOFactory daoFactory;

    public EditWayCommand(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(EditWayCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int routeId = Integer.parseInt(req.getParameter("route_id"));
        String editWay = req.getParameter("edit_way");
        if(Boolean.parseBoolean(editWay) == true){
            Route routeUpdate = new Route();

            String wayTrainId = req.getParameter("add_way_train_id");
            String wayTimeDep = req.getParameter("add_way_dep_time").replace("T", " ").replace("%3A", ":");;
            String wayTimeArrival = req.getParameter("add_way_arrival_time").replace("T", " ").replace("%3A", ":");
            String wayStationDep = req.getParameter("add_way_dep_station");
            String wayStationArrival = req.getParameter("add_way_arrival_station");
            String wayTicketPrice = req.getParameter("add_way_ticket_price");
            String wayTicketFree = req.getParameter("add_way_ticket_free");

            routeUpdate.setId(routeId);
            routeUpdate.setTraineNumber(Integer.parseInt(wayTrainId));
            routeUpdate.setDateStart(wayTimeDep);
            routeUpdate.setDateArrival(wayTimeArrival);
            routeUpdate.setStationIdStart(Integer.parseInt(wayStationDep));
            routeUpdate.setStationIdArrival(Integer.parseInt(wayStationArrival));
            routeUpdate.setRoutePrice(Integer.parseInt(wayTicketPrice));
            routeUpdate.setFreePlaces(Integer.parseInt(wayTicketFree));

//            if (wayTrainId.isEmpty()) {req.getSession().setAttribute("addWayMessage", "Train Number must be not empty!"); return "edit.jsp";}
//            if (wayTimeDep.isEmpty()) {req.getSession().setAttribute("addWayMessage", "Dep. Time must be not empty!"); return "edit.jsp";}
//            if (wayTimeArrival.isEmpty()) {req.getSession().setAttribute("addWayMessage", "Arrival Time must be not empty!"); return "edit.jsp";}
//            if (wayStationDep.isEmpty()) {req.getSession().setAttribute("addWayMessage", "Dep. Station must be not empty!"); return "edit.jsp";}
//            if (wayStationArrival.isEmpty()) {req.getSession().setAttribute("addWayMessage", "Arrival Station must be not empty!"); return "edit.jsp";}
//            if (wayTicketPrice.isEmpty()) {req.getSession().setAttribute("addWayMessage", "Ticket Price must be not empty!"); return "edit.jsp";}
//            if (wayTicketFree.isEmpty()) {req.getSession().setAttribute("addWayMessage", "Free Tickets must be not empty!"); return "edit.jsp";}

            try {
                daoFactory.getRouteDAO().update(routeUpdate);
                LOG.debug("Route id {} has been updated ", routeId);
            } catch (DAOException ex){
                req.getSession().setAttribute("addWayMessage", "dbexception");
                LOG.error("Route id {} has not been updated. SQL exception.", routeId);
            }
            req.getSession().setAttribute("addWayMessage", "routeupdate");
        }


        Route route = daoFactory.getRouteDAO().getById(routeId);
        List<Station> stationList = daoFactory.getStationDAO().getAll();

        req.setAttribute("route", route);
        req.setAttribute("stationList", stationList);


        return "editway.jsp";
    }

}
