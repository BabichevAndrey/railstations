package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to show a ticket</strong>.
 */

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOCloseTransactionConnection;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.exception.DAOTransactionSQLException;
import com.rail.web.controllers.dao.models.Route;
import com.rail.web.controllers.dao.models.Station;
import com.rail.web.controllers.dao.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTicketCommand implements Command {

    private final DAOFactory daoFactory;

    public ShowTicketCommand(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(ShowTicketCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int routeId = Integer.parseInt(req.getParameter("route_id"));
        System.out.println("routeId is " + routeId);

        Route route = daoFactory.getRouteDAO().getById(routeId);
        List<Station> stationList = daoFactory.getStationDAO().getAll();

        System.out.println("in route " + route);

        User user = (User) req.getSession().getAttribute("currentUser");

        int ticketId = 0;
        try {
            ticketId = daoFactory.getTicketDAO().addNewTicket(route, user);
            LOG.debug("Ticked id is {}", ticketId);
        } catch (DAOTransactionSQLException ex){
            req.getSession().setAttribute("ticketStatus", "dbexception");
            LOG.warn("Transaction failed on ticket bought");
        } catch (DAOCloseTransactionConnection e){
            req.getSession().setAttribute("ticketStatus", "dbexception");
            LOG.warn("Transaction failed on ticket bought. Mistake on closing connection");
        } catch (DAOException d){
            req.getSession().setAttribute("ticketStatus", "dbexception");
            LOG.error("DB connection issues at ticket bought");
        }

        req.getSession().setAttribute("route", route);
        req.getSession().setAttribute("ticketCode", ticketId);
        req.getSession().setAttribute("buy_way", true);
        req.getSession().setAttribute("stationList", stationList);


       return "buyticket.jsp";
    }

}
