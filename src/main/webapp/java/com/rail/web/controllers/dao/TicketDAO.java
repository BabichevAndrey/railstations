package com.rail.web.controllers.dao;

import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Route;
import com.rail.web.controllers.dao.models.User;

/**
 * @author Andrii Babichev
 * @version 1.0
 * Interface of basic methods for entity ticket.
 */

public interface TicketDAO extends ItemDAO<Route>{

    int addNewTicket(Route route, User user) throws DAOException;

}