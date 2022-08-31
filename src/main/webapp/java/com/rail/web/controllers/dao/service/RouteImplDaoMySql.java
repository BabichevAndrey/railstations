package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.RouteDAO;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.exception.DAOParcerException;
import com.rail.web.controllers.dao.models.Route;
import com.rail.web.db.PoolcConnectionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class implements the routes entity interface.
 * Adds new routes, deletes routes, searches for routes by parameters, updates routes.
 */

public class RouteImplDaoMySql implements RouteDAO {



    private static final Logger LOG = LoggerFactory.getLogger(RouteImplDaoMySql.class);

    private final DataSource ds;

    public RouteImplDaoMySql(DataSource dataSource) {
        ds = dataSource;
    }

    Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


    /**
     * searches for a route based on its id
     * @param id
     * @return route
     * @throws DAOException
     */
    @Override
    public Route getById(int id) throws DAOException {

        Route route = new Route();

        String SQL_ROUTE_BY_ID = "select * from routes where id = ?";

        try (Connection con = this.getConnection()){

            PreparedStatement pstmt =  con.prepareStatement(SQL_ROUTE_BY_ID);
            pstmt.setString(1, String.valueOf(id));
            ResultSet rs = pstmt.executeQuery();
       //    request.getSession().setAttribute("deletedWay", "Way has been deleted");
            if (rs.next()){
                route.setId(id);
                route.setTraineNumber(rs.getInt("traine_number"));
                route.setStationIdStart(rs.getInt("stations_id_start"));
                route.setStationIdArrival(rs.getInt("stations_id_arrival"));
                route.setDateStart(rs.getString("date_start"));
                route.setDateArrival(rs.getString("date_arrival"));
                route.setFreePlaces(rs.getInt("free_places"));
                route.setRoutePrice(rs.getInt("route_price"));
                return route;
            }
        } catch (SQLException ex){
            throw new DAOException("Some trouble with connection, please try again", ex);
        }
        return null;
    }

    @Override
    public List<Route> getAll() {
        return null;
    }

    /**
     * adds a new route according to the parameters entered by the administrator
     * @param route
     * @throws DAOException
     */
    @Override
    public void addNew(Route route) throws DAOException{
        String SQL_ADD_NEW_WAY = "INSERT INTO routes (traine_number, stations_id_start, " +
                "stations_id_arrival, date_start, date_arrival, free_places, route_price)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_WAY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, String.valueOf(route.getTraineNumber()));
            pstmt.setString(2, String.valueOf(route.getStationStartName()));
            pstmt.setString(3, String.valueOf(route.getStationArrivalName()));
            pstmt.setString(4, route.getDateStart());
            pstmt.setString(5, route.getDateArrival());
            pstmt.setString(6, String.valueOf(route.getFreePlaces()));
            pstmt.setString(7, String.valueOf(route.getRoutePrice()));
            pstmt.executeUpdate();
        }  catch (SQLException ex){
            throw new DAOException("DB issues, try again", ex);
        }

    }

    /**
     * updates route information
     * @param route
     * @throws DAOException
     */
    @Override
    public void update(Route route) throws DAOException {

        String SQL_UPDATE_ROUTE = "UPDATE routes SET traine_number =?, stations_id_start =?, stations_id_arrival =?," +
                "date_start = ?, date_arrival=?, free_places=?, route_price=? WHERE id=?";

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_UPDATE_ROUTE);
            pstmt.setString(1, String.valueOf(route.getTraineNumber()));
            pstmt.setString(2, String.valueOf(route.getStationIdStart()));
            pstmt.setString(3, String.valueOf(route.getStationIdArrival()));
            pstmt.setString(4, String.valueOf(route.getDateStart()));
            pstmt.setString(5, String.valueOf(route.getDateArrival()));
            pstmt.setString(6, String.valueOf(route.getFreePlaces()));
            pstmt.setString(7, String.valueOf(route.getRoutePrice()));
            pstmt.setString(8, String.valueOf(route.getId()));
            pstmt.executeUpdate();
         } catch (SQLException ex){
            throw new DAOException("Route not updated", ex);
        }

    }

    @Override
    public void remove(Route model) {

    }

    /**
     * returns a list of routes based on the given search parameters
     * @param route
     * @return
     * @throws DAOException
     */
    @Override
    public List<Route> findRoutesList(Route route) throws DAOException {

        String startStation = route.getStationStartName();
        String arrivalStation = route.getStationArrivalName();
        String checkInDate = route.getDateStart();
        String checkOutDate = route.getDateArrival();

        String SQL_FIND_ROUTE_BY_INSERT_VALUES = "select * from routes where stations_id_start =? " +
                        "and stations_id_arrival =? and date_start>? and date_arrival<?";

            List<Route> routes = new ArrayList<>();

            int startStationId = findStationByName(startStation);
            int arrivalStationId = findStationByName(arrivalStation);

            if (checkInDate == "") {
                Date date = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
                checkInDate = formatForDateNow.format(date);
            }

            if (checkOutDate == ""){
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = new GregorianCalendar();
                calendar.add (Calendar.MONTH, 2);
                date = calendar.getTime();
                checkOutDate = sdf.format(date);
            }


            try (Connection con = this.getConnection()){
                PreparedStatement pstmt =  con.prepareStatement(SQL_FIND_ROUTE_BY_INSERT_VALUES);
                pstmt.setString(1, String.valueOf(startStationId));
                pstmt.setString(2, String.valueOf(arrivalStationId));
                pstmt.setString(3, String.valueOf(checkInDate));
                pstmt.setString(4, String.valueOf(checkOutDate));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                    routes.add(extractRoute(rs, startStation, arrivalStation));
                }

            } catch (SQLException ex){
                throw new DAOException("No any matches has been finded", ex);
            }

            return routes;


    }

    /**
     * remove route by id
     * @param wayId
     * @throws DAOException
     */
    @Override
    public void deleteWayById(String wayId) throws DAOException{

        String SQL_DELETE_WAY_BY_ID = "delete from routes where id = ?";

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_WAY_BY_ID);
            pstmt.setString(1, wayId);
            pstmt.executeUpdate();
        } catch (SQLException ex){
            throw new DAOException("Deleting failed", ex);
        }
    }

    /**
     * retrieves route information
     * @param rs
     * @param startStation
     * @param arrivalStation
     * @return
     * @throws DAOException
     */
    private Route extractRoute(ResultSet rs, String startStation, String arrivalStation) throws DAOException {
        Route route = new Route();
        try {
            route.setTraineNumber(rs.getInt("traine_number"));
            route.setStationIdStart(rs.getInt("stations_id_start"));
            route.setStationIdArrival(rs.getInt("stations_id_arrival"));
            route.setDateStart(rs.getString("date_start"));
            route.setDateArrival(rs.getString("date_arrival"));
            route.setFreePlaces(rs.getInt("free_places"));
            route.setRoutePrice(rs.getInt("route_price"));
            route.setId(rs.getInt("id"));
        } catch (SQLException e){
            throw new DAOException("Connection issues", e);
        }

        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");

        long diffMinutes = 0;
        long diffHours = 0;
        long diffDays = 0;
        try {
            long millisStart = format.parse(rs.getString("date_start")).getTime();
            long millisArrival = format.parse(rs.getString("date_arrival")).getTime();
            long millisInTrip = millisArrival-millisStart;

            diffMinutes = millisInTrip / (60 * 1000) % 60;
            diffHours = millisInTrip / (60 * 60 * 1000) % 24;
            diffDays = millisInTrip / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            throw new DAOParcerException("Time parsing issues", e);
        } catch (SQLException e) {
            throw new DAOException("Connection issues", e);
        }

        String dateInTripOut = "";
        if (diffDays>0){ dateInTripOut = diffDays + " days"; }

        String dHm = dateInTripOut + " " +  new DecimalFormat( "00" ).format(diffHours)
                + ":" + new DecimalFormat( "00" ).format(diffMinutes);

        route.setTripTime(dHm);
        route.setStationStartName(startStation);
        route.setStationArrivalName(arrivalStation);
        return route;
    }

    /**
     * returns the route id given its name
     * @param stationName
     * @return
     * @throws DAOException
     */
    private int findStationByName(String stationName) throws DAOException {

        String SQL_FIND_ID_STATION_BY_NAME = "select id from stations where station_name=?";

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_FIND_ID_STATION_BY_NAME);
            pstmt.setString(1, stationName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }

        } catch (SQLException ex){
            LOG.error("Station {} has not found. SQL exception", stationName);
            throw new DAOException("DB exception", ex);
        }
        return 0;
    }

}
