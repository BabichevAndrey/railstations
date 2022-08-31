package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to add show stations list</strong>.
 */

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowStationList implements Command {

    private final DAOFactory daoFactory;

    public ShowStationList(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(ShowStationList.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int pagerow = Integer.parseInt(req.getParameter("pagerow"));
        int rowsOnPage = Integer.parseInt(req.getParameter("rows_on_page"));
        String sortByName = req.getParameter("sortby");
        int ordering = 1;

        if (sortByName.equals("nameup")){
            req.setAttribute("stationName", "nameup");
            ordering =  2;
        }

        if (sortByName.equals("namedown")){
            req.setAttribute("stationName", "namedown");
            ordering =  3;
        }

        if ((rowsOnPage % 5 != 0) || (rowsOnPage > 21)){
            rowsOnPage = 5;
        }


        req.setAttribute("rowsOnPage", rowsOnPage);

        int rowsInStations = 0;
        try {
            rowsInStations = daoFactory.getStationDAO().countAllStations();
            LOG.debug("Show station list");
        } catch (DAOException e){
            LOG.error("SQL exception at station list");
        }

        if (pagerow*rowsOnPage > (rowsInStations)) pagerow = 0;
        if (pagerow < 0) pagerow = rowsInStations/rowsOnPage;


        List<Station> stationList = daoFactory.getStationDAO().findStations(pagerow, rowsOnPage, ordering);

        if (stationList != null){
            req.setAttribute("stationList", stationList);
            req.setAttribute("pagerow", pagerow);
        }

        return "addstation.jsp";
    }
}
