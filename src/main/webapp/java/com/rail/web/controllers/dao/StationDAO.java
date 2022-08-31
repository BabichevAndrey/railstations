package com.rail.web.controllers.dao;

import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Station;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Andrii Babichev
 * @version 1.0
 * Interface of basic methods for entity station.
 */

public interface StationDAO extends ItemDAO<Station>{


    List<Station> findStations(int offsetId, int rowsOnPage, int ordering) throws DAOException;

    void deleteStationById(String id) throws DAOException;

    int countAllStations() throws DAOException;

    void addStation(Station station) throws DAOException;

    Station getStationByName(String stationName) throws DAOException;
}
