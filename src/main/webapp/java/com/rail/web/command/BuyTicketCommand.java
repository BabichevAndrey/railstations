package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to buy ticket</strong>.
 * Buy a ticket on the selected route.
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

public class BuyTicketCommand implements Command {

    private final DAOFactory daoFactory;

    public BuyTicketCommand(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(BuyTicketCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int routeId = Integer.parseInt(req.getParameter("route_id"));

        Route route =  null;
        try {
            route = daoFactory.getRouteDAO().getById(routeId);
            LOG.debug("Ticket has been bought");
        } catch (DAOException ex){
            req.getSession().setAttribute("routeToTicket", "dbexception");
            LOG.error("SQL exception when try to bought new ticket");
        }


        List<Station> stationList = daoFactory.getStationDAO().getAll();

        req.setAttribute("route", route);
        req.setAttribute("stationList", stationList);

        return "/buyticket.jsp";
    }

}
