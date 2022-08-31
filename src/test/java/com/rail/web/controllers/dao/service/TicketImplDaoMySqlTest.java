package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.DbUtil;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.exception.DuplicateUserExeption;
import com.rail.web.controllers.dao.models.Route;
import com.rail.web.controllers.dao.models.Station;
import com.rail.web.controllers.dao.models.Ticket;
import com.rail.web.controllers.dao.models.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class TicketImplDaoMySqlTest {

    static TicketImplDaoMySql ticketImpl;


    @Before
    public void setUpBeforeClass() throws Exception {
        ticketImpl = new TicketImplDaoMySql(DbUtil.getDataSource());
    }

    @Test
    public void testGetConnection() {
        assertNotNull(ticketImpl);
        try (Connection connection = ticketImpl.getConnection()) {
        } catch (SQLException e) {
            fail("Cannot get connection");
        }
    }

    @Test
    public void addNewTicket() throws DAOException{

        int ticketId = 0;

        try {
            addTwoTestStation();
            addTestUser();
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


            int userId = getUserId();
            User userFromDb = new User();
            if (userId == 0) throw new IllegalArgumentException();
            userFromDb.setId(userId);

            Route routeFromDb = getRouteFromDb();
            int routeId = getRouteId();
            routeFromDb.setId(routeId);



            ticketId = ticketImpl.addNewTicket(routeFromDb, userFromDb);
            Ticket ticketFromDb = getTicketFromDb(ticketId);

            assertEquals(routeFromDb.getRoutePrice(), ticketFromDb.getTicketPrice());


        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            deleteTicket(ticketId);
            deleteTestRoute();
            deleteUser();
            deleteTestTwoStations();
        }

    }

    private Ticket getTicketFromDb(int ticketId) throws DAOException {

        Ticket ticket = new Ticket();

        String SQL_TICKET_FROM_DB = "select * from tickets where id = ?";

        try (Connection con = DbUtil.getDataSource().getConnection()) {

            PreparedStatement pstmt = con.prepareStatement(SQL_TICKET_FROM_DB);
            pstmt.setString(1, String.valueOf(ticketId));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ticket.setUserIdTicket(rs.getInt("users_id"));
                ticket.setRouteIdTicket(rs.getInt("routes_id"));
                ticket.setTicketPrice(rs.getInt("price"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Some trouble with connection, please try again", ex);
        }
        return ticket;
    }

    private void deleteTicket(int ticketId) throws DAOException {
        String SQL_DELETE_TICKET_BY_ID = "delete from tickets where id = ?";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_TICKET_BY_ID);
            pstmt.setString(1, String.valueOf(ticketId));
            pstmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DAOException("Deleting failed", ex);
        }

    }

    private int getRouteId() throws DAOException{
        String SQL_FIND_ROUTE_ID_BY_DATE = "select id from routes where  date_start = ? and date_arrival = ?";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_FIND_ROUTE_ID_BY_DATE);
            pstmt.setString(1, "2010-10-10 10:10:10");
            pstmt.setString(2, "2010-10-10 10:10:10");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException ex){
            throw new DAOException("DB exception", ex);
        }
        return 0;
    }

    private Route getRouteFromDb() throws DAOException {
        Route routeFromDb = new Route();

        int startTestStationId = getTestStationId("teststartstation");
        int arrivalTestStationId = getTestStationId("testarrivalstation");

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

        return routeFromDb;
    }

    private int getUserId() throws DAOException{
        String SQL_FIND_USER_ID_BY_LOGIN = "select id from users where login = ?";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_FIND_USER_ID_BY_LOGIN);
            pstmt.setString(1, "testusertestlogin");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException ex){
            throw new DAOException("DB exception", ex);
        }
        return 0;

    }

    private void deleteUser() throws DAOException{
            String SQL_DELETE_USER_BY_NAME = "delete from users where login = ?";

            try (Connection con = DbUtil.getDataSource().getConnection()){
                PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_USER_BY_NAME);
                pstmt.setString(1, "testusertestlogin");
                pstmt.executeUpdate();
            } catch (SQLException ex){
                ex.printStackTrace();
                throw new DAOException("Deleting failed", ex);
            }
    }

    private void addTestUser() throws DAOException{

        String SQL_ADD_NEW_STATION = "INSERT INTO users (login, user_name, password, role)" +
                " VALUES (?, ?, ?, ?);";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_STATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "testusertestlogin");
            pstmt.setString(2, "usernametesttest");
            pstmt.setString(3, "012345");
            pstmt.setString(4, String.valueOf(1));
            pstmt.executeUpdate();

        } catch (SQLException ex){
            throw new DAOException("SQL exception", ex);
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

    private void addTwoTestStation() throws DAOException {
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
}