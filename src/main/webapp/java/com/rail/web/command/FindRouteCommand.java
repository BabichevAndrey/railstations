package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to find route between two station given time</strong>.
 * Return a list of routes based on the given stations and time range.
 */

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindRouteCommand implements Command {

    private final DAOFactory daoFactory;

    public FindRouteCommand(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(FindRouteCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String checkInDate = req.getParameter("check_in_date");
        String checkOutDate = req.getParameter("check_out_date");
        String startStation = req.getParameter("start_station");
        String arrivalStation = req.getParameter("final_station");


        Route route = new Route();
        route.setDateStart(checkInDate);
        route.setDateArrival(checkOutDate);
        route.setStationStartName(startStation);
        route.setStationArrivalName(arrivalStation);


        List<Route> routes = null;
        try {
           routes = daoFactory.getRouteDAO().findRoutesList(route);
           LOG.debug("Route has been found");
        } catch (DAOException ex){
            req.getSession().setAttribute("routeListInfo", "dbexception");
            LOG.error("Route has not found. SQL exception");
        }


        if (routes != null){
            req.setAttribute("routeList", routes);
            req.getRequestDispatcher("search_results.jsp").forward(req, resp);
        }

        return "hello-rail";

    }
}
