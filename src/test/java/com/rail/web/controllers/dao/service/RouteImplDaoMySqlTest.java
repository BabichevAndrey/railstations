package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.DbUtil;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Route;
import com.rail.web.controllers.dao.models.Station;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class RouteImplDaoMySqlTest {


    static RouteImplDaoMySql routeImpl;

    @Before
    public void setUpBeforeClass() throws Exception {
        routeImpl = new RouteImplDaoMySql(DbUtil.getDataSource());
    }

    @Test
    public void testGetConnection() {
        assertNotNull(routeImpl);
        try (Connection connection = routeImpl.getConnection()) {
        } catch (SQLException e) {
            fail("Cannot get connection");
        }
    }


    @Test
    public void getByIdTest() throws DAOException{

        try {
            addTwoTestStation();
            Route route = createTestRoute();

            String SQL_ADD_NEW_WAY = "INSERT INTO routes (traine_number, stations_id_start, " +
                    "stations_id_arrival, date_start, date_arrival, free_places, route_price)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?);";

            try (Connection con = DbUtil.getDataSource().getConnection()) {
                PreparedStatement pstmt = con.prepareStatement(SQL_ADD_NEW_WAY, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, String.valueOf(route.getTraineNumber()));
                pstmt.setString(2, String.valueOf(route.getStationStartName()));
                pstmt.setString(3, String.valueOf(route.getStationArrivalName()));
                pstmt.setString(4, route.getDateStart());
                pstmt.setString(5, route.getDateArrival());
                pstmt.setString(6, String.valueOf(route.getFreePlaces()));
                pstmt.setString(7, String.valueOf(route.getRoutePrice()));
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DAOException("DB issues, try again", ex);
            }

            String SQL_FIND_ID_ROUTE_BY_DATE = "select id from routes where date_start = ? and date_arrival = ?";
            int routeTestId = 0;

            try (Connection con = DbUtil.getDataSource().getConnection()) {
                PreparedStatement pstmt = con.prepareStatement(SQL_FIND_ID_ROUTE_BY_DATE);
                pstmt.setString(1, "2010-10-10 10:10:10");
                pstmt.setString(2, "2010-10-10 10:10:10");
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    routeTestId = rs.getInt("id");
                }
            } catch (SQLException ex) {
                throw new DAOException("DB exception", ex);
            }

            if (routeTestId == 0) throw new IllegalArgumentException();
            Route routeFromDb = new Route();
            routeFromDb = routeImpl.getById(routeTestId);

            assertEquals(route.getDateArrival(), routeFromDb.getDateArrival(), "Time must be equals");
            assertEquals(route.getDateStart(), routeFromDb.getDateStart(), "Time must be equals");
            assertEquals(route.getStationIdStart(), routeFromDb.getStationIdStart(), "Start stations must be same");
            assertEquals(route.getStationIdArrival(), routeFromDb.getStationIdArrival(), "Arrival stations must be same");
            assertEquals(route.getFreePlaces(), routeFromDb.getFreePlaces(), "Count of free places must be equals");
            assertEquals(route.getRoutePrice(), routeFromDb.getRoutePrice(), "Price must be equals");

            deleteTestRoute();
            deleteTestTwoStations();
        } catch (Exception ex){
            deleteTestRoute();
            deleteTestTwoStations();
        }

    }

    @Test
    public void addNewTest() throws DAOException {

        try {
            addTwoTestStation();
            Route route = createTestRoute();
            int startTestStationId = getTestStationId("teststartstation");
            int arrivalTestStationId = getTestStationId("testarrivalstation");

            routeImpl.addNew(route);

            Route routeFromDb = new Route();

            String SQL_ROUTE_BY_START_AND_ARRIVAL_STATION_ID = "select * from routes where " +
                    "stations_id_start = ? and stations_id_arrival = ?";

            try (Connection con = DbUtil.getDataSource().getConnection()) {

                PreparedStatement pstmt = con.prepareStatement(SQL_ROUTE_BY_START_AND_ARRIVAL_STATION_ID);
                pstmt.setString(1, String.valueOf(startTestStationId));
                pstmt.setString(2, String.valueOf(arrivalTestStationId));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    routeFromDb.setTraineNumber(rs.getInt("traine_number"));
                    routeFromDb.setStationIdStart(rs.getInt("stations_id_start"));
                    routeFromDb.setStationIdArrival(rs.getInt("stations_id_arrival"));
                    routeFromDb.setDateStart(rs.getString("date_start"));
                    routeFromDb.setDateArrival(rs.getString("date_arrival"));
                    routeFromDb.setFreePlaces(rs.getInt("free_places"));
                    routeFromDb.setRoutePrice(rs.getInt("route_price"));
                }
            } catch (SQLException ex) {
                throw new DAOException("Some trouble with connection, please try again", ex);
            }

            assertEquals(route.getDateArrival(), routeFromDb.getDateArrival(), "Time must be equals");
            assertEquals(route.getDateStart(), routeFromDb.getDateStart(), "Time must be equals");
            assertEquals(route.getStationIdStart(), routeFromDb.getStationIdStart(), "Start stations must be same");
            assertEquals(route.getStationIdArrival(), routeFromDb.getStationIdArrival(), "Arrival stations must be same");
            assertEquals(route.getFreePlaces(), routeFromDb.getFreePlaces(), "Count of free places must be equals");
            assertEquals(route.getRoutePrice(), routeFromDb.getRoutePrice(), "Price must be equals");


        } catch(Exception ex){
            ex.printStackTrace();
        } finally {
            deleteTestRoute();
            deleteTestTwoStations();
        }


    }

    private void deleteTestRoute() throws DAOException{
        String SQL_DELETE_ROUTE = "delete from routes where date_start = ? and date_arrival = ?";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_ROUTE);
            pstmt.setString(1, "2010-10-10 10:10:10");
            pstmt.setString(2, "2010-10-10 10:10:10");
            pstmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DAOException("Deleting failed", ex);
        }
    }

    private void deleteTestTwoStations() throws DAOException{
        String SQL_DELETE_STATION_BY_NAME = "delete from stations where station_name = ?";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_STATION_BY_NAME);
            pstmt.setString(1, "teststartstation");
            pstmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DAOException("Deleting failed", ex);
        }

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_STATION_BY_NAME);
            pstmt.setString(1, "testarrivalstation");
            pstmt.executeUpdate();
        } catch (SQLException ex){
            throw new DAOException("Deleting failed", ex);
        }
    }

    private int getTestStationId(String testStationName) throws DAOException {
        String SQL_FIND_ID_STATION_BY_NAME = "select id from stations where station_name= ?";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_FIND_ID_STATION_BY_NAME);
            pstmt.setString(1, testStationName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException ex){
            throw new DAOException("DB exception", ex);
        }
        return 0;
    }

    private void addTwoTestStation() throws DAOException{
        Station station = new Station();
        station.setStationName("teststartstation");

        String SQL_ADD_NEW_STATION = "INSERT INTO stations (station_name)" +
                " VALUES (?);";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_STATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, station.getStationName());
            pstmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DAOException("SQL exception", ex);
        }

        station.setStationName("testarrivalstation");

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_STATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, station.getStationName());
            pstmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DAOException("SQL exception", ex);
        }
    }

    @Test
    public void update() throws DAOException{

        try {
            addTwoTestStation();
            Route route = createTestRoute();
            int startTestStationId = getTestStationId("teststartstation");
            int arrivalTestStationId = getTestStationId("testarrivalstation");

            String SQL_ADD_NEW_WAY = "INSERT INTO routes (traine_number, stations_id_start, " +
                    "stations_id_arrival, date_start, date_arrival, free_places, route_price)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?);";

            try (Connection con = DbUtil.getDataSource().getConnection()) {
                PreparedStatement pstmt = con.prepareStatement(SQL_ADD_NEW_WAY, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, String.valueOf(route.getTraineNumber()));
                pstmt.setString(2, String.valueOf(route.getStationStartName()));
                pstmt.setString(3, String.valueOf(route.getStationArrivalName()));
                pstmt.setString(4, route.getDateStart());
                pstmt.setString(5, route.getDateArrival());
                pstmt.setString(6, String.valueOf(route.getFreePlaces()));
                pstmt.setString(7, String.valueOf(route.getRoutePrice()));
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DAOException("DB issues, try again", ex);
            }

            String SQL_FIND_ID_ROUTE_BY_DATE = "select id from routes where date_start = ? and date_arrival = ?";
            int routeTestId = 0;

            try (Connection con = DbUtil.getDataSource().getConnection()) {
                PreparedStatement pstmt = con.prepareStatement(SQL_FIND_ID_ROUTE_BY_DATE);
                pstmt.setString(1, "2010-10-10 10:10:10");
                pstmt.setString(2, "2010-10-10 10:10:10");
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    routeTestId = rs.getInt("id");
                }
            } catch (SQLException ex) {
                throw new DAOException("DB exception", ex);
            }

            if (routeTestId == 0) throw new IllegalArgumentException();


            Route routeToUpdate = new Route();
            routeToUpdate.setTraineNumber(Integer.parseInt(String.valueOf(10)));
            routeToUpdate.setDateStart("2010-10-10 10:10:10");
            routeToUpdate.setDateArrival("2010-10-10 10:10:10");
            routeToUpdate.setStationStartName(String.valueOf(startTestStationId));
            routeToUpdate.setStationArrivalName(String.valueOf(arrivalTestStationId));
            routeToUpdate.setStationIdStart(startTestStationId);
            routeToUpdate.setStationIdArrival(arrivalTestStationId);
            routeToUpdate.setRoutePrice(Integer.parseInt(String.valueOf(20)));
            routeToUpdate.setFreePlaces(Integer.parseInt(String.valueOf(20)));
            routeToUpdate.setId(routeTestId);

            routeImpl.update(routeToUpdate);

            assertEquals(route.getDateArrival(), routeToUpdate.getDateArrival(), "Time must be equals");
            assertEquals(route.getDateStart(), routeToUpdate.getDateStart(), "Time must be equals");
            assertEquals(route.getStationIdStart(), routeToUpdate.getStationIdStart(), "Start stations must be same");
            assertEquals(route.getStationIdArrival(), routeToUpdate.getStationIdArrival(), "Arrival stations must be same");
            assertEquals(20, routeToUpdate.getFreePlaces(), "Count of free places must be equals");
            assertEquals(20, routeToUpdate.getRoutePrice(), "Price must be equals");

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            deleteTestRoute();
            deleteTestTwoStations();
        }

    }


    @Test
    public void findRoutesListTest() throws DAOException{

        try {
            addTwoTestStation();
            Route route = createTestRoute();

            String SQL_ADD_NEW_WAY = "INSERT INTO routes (traine_number, stations_id_start, " +
                    "stations_id_arrival, date_start, date_arrival, free_places, route_price)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?);";

            try (Connection con = DbUtil.getDataSource().getConnection()) {
                PreparedStatement pstmt = con.prepareStatement(SQL_ADD_NEW_WAY, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, String.valueOf(route.getTraineNumber()));
                pstmt.setString(2, String.valueOf(route.getStationStartName()));
                pstmt.setString(3, String.valueOf(route.getStationArrivalName()));
                pstmt.setString(4, route.getDateStart());
                pstmt.setString(5, route.getDateArrival());
                pstmt.setString(6, String.valueOf(route.getFreePlaces()));
                pstmt.setString(7, String.valueOf(route.getRoutePrice()));
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DAOException("DB issues, try again", ex);
            }

            Route routeFindListInfo = new Route();
            routeFindListInfo.setStationStartName("teststartstation");
            routeFindListInfo.setStationArrivalName("testarrivalstation");
            routeFindListInfo.setDateStart("2009-10-10 10:10:10");
            routeFindListInfo.setDateArrival("2011-10-10 10:10:10");

            List<Route> routeList = routeImpl.findRoutesList(routeFindListInfo);

            assertNotNull(routeList);
            assertTrue(routeList.size() > 0);

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            deleteTestRoute();
            deleteTestTwoStations();
        }

    }
    
    public Route createTestRoute() throws DAOException {
        int startTestStationId = getTestStationId("teststartstation");
        int arrivalTestStationId = getTestStationId("testarrivalstation");
        Route route = new Route();
        route.setTraineNumber(Integer.parseInt(String.valueOf(10)));
        route.setDateStart("2010-10-10 10:10:10");
        route.setDateArrival("2010-10-10 10:10:10");
        route.setStationStartName(String.valueOf(startTestStationId));
        route.setStationArrivalName(String.valueOf(arrivalTestStationId));
        route.setStationIdStart(startTestStationId);
        route.setStationIdArrival(arrivalTestStationId);
        route.setRoutePrice(Integer.parseInt(String.valueOf(10)));
        route.setFreePlaces(Integer.parseInt(String.valueOf(10)));
        return route;
    }

    @Test
    public void deleteWayByIdTest() throws DAOException {

        try {
            addTwoTestStation();
            Route route = createTestRoute();

            String SQL_ADD_NEW_WAY = "INSERT INTO routes (traine_number, stations_id_start, " +
                    "stations_id_arrival, date_start, date_arrival, free_places, route_price)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?);";

            try (Connection con =  DbUtil.getDataSource().getConnection()){
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

            String SQL_FIND_ID_ROUTE_BY_DATE = "select id from routes where date_start = ? and date_arrival = ?";
            int routeTestId = 0;

            try (Connection con = DbUtil.getDataSource().getConnection()){
                PreparedStatement pstmt =  con.prepareStatement(SQL_FIND_ID_ROUTE_BY_DATE);
                pstmt.setString(1, "2010-10-10 10:10:10");
                pstmt.setString(2, "2010-10-10 10:10:10");
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()){
                    routeTestId = rs.getInt("id");
                }
            } catch (SQLException ex){
                throw new DAOException("DB exception", ex);
            }

            if (routeTestId == 0) throw new IllegalArgumentException();

            routeImpl.deleteWayById(String.valueOf(routeTestId));

            Route nullRoute = routeImpl.getById(routeTestId);

            assertNull(nullRoute);

            deleteTestTwoStations();
        } catch (Exception ex){
            deleteTestTwoStations();
        }

    }

}