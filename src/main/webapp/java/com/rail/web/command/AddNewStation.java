package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to add a new train station</strong>.
 * Stations have unique names, if the station already exists, then the entry will not be added.
 * Upon successful addition of a station, we return to the list with stations.
 */

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Station;
import com.rail.web.controllers.dao.models.User;
import com.rail.web.controllers.dao.exception.DuplicateStationExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewStation implements Command {

    private final DAOFactory daoFactory;

    public AddNewStation(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(AddNewStation.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String newStation = req.getParameter("add_station");

        User currentUser = (User) req.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String dateNow = ft.format(date);

        Station station = new Station();
        station.setStationName(newStation);
        station.setId(userId);
        station.setAddTime(dateNow);

        try {
            daoFactory.getStationDAO().addStation(station);
            req.getSession().setAttribute("addStationtationMessage", "stationhasbeenadded");
            LOG.debug("Station has been added");
        } catch (DuplicateStationExeption e){
            req.getSession().setAttribute("addStationtationMessage", "stationalreadiexist");
            LOG.warn("Station already exist in db");
        } catch (DAOException ex){
            req.getSession().setAttribute("addStationtationMessage", "dbexception");
            LOG.error("SQL exception at adding new station");
        }


        return "controller?command=showStations&pagerow=0";

    }

}