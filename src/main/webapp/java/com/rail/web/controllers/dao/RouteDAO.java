package com.rail.web.controllers.dao;

import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Route;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Andrii Babichev
 * @version 1.0
 * Interface of basic methods for entity route.
 */

public interface RouteDAO extends ItemDAO<Route>{

    List<Route> findRoutesList(Route route) throws DAOException;

    void deleteWayById(String wayId) throws DAOException;

}
