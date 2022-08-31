package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.DAOCloudScape;
import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.DbUtil;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.exception.DuplicateStationExeption;
import com.rail.web.controllers.dao.models.Station;
import com.rail.web.db.PoolcConnectionBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class StationImplDaoMySqlTest {

    static StationImplDaoMySql stImpl;


    @Before
    public void setUpBeforeClass() throws Exception {
        stImpl = new StationImplDaoMySql(DbUtil.getDataSource());
        System.out.println("data source is " + DbUtil.getDataSource());
    }

    @Test
    public void testGetConnection() {
        assertNotNull(stImpl);
        try (Connection connection = stImpl.getConnection()) {
        } catch (SQLException e) {
            fail("Cannot get connection");
        }
    }

    @Test
    public void getAllTest() {
        List<Station> stations = stImpl.getAll();
        assertNotNull(stations);
        assertTrue(stations.size()>0);
        return;
    }


    @Test
    public void addStationTest() throws DAOException {

        Station station = new Station();
        station.setStationName("rularula");
        String dateString = "2010-10-10 10:10:10";
        station.setAddTime(dateString);
        stImpl.addStation(station);

        Station stationFromDb = stImpl.getStationByName("rularula");

        assertEquals("2010-10-10 10:10:10", stationFromDb.getAddTime(), "Time must be equals");

        String SQL_DELETE_STATION_BY_NAME = "delete from stations where station_name = ?";
        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_STATION_BY_NAME, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "rularula");
            pstmt.executeUpdate();
        } catch (SQLException ex){
            throw new DAOException("SQL exception", ex);
        }

    }

    @Test
    public void getStationByNameTest() throws DAOException{

        Station station = new Station();
        station.setStationName("troptrop");
        String dateString = "2011-11-11 11:11:11";
        station.setAddTime(dateString);
        stImpl.addStation(station);

        Station stationFromDb = stImpl.getStationByName("troptrop");

        assertEquals("2011-11-11 11:11:11", stationFromDb.getAddTime(), "Time must be equals");

        String SQL_DELETE_STATION_BY_NAME = "delete from stations where station_name = ?";
        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_STATION_BY_NAME, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "troptrop");
            pstmt.executeUpdate();
        } catch (SQLException ex){
            throw new DAOException("SQL exception", ex);
        }

    }

    @Test
    public void findStationsTest() throws DAOException {
        List<Station> stations = stImpl.findStations(0, 5, 1);
        assertNotNull(stations);
        assertTrue(stations.size()==5);
        return;
    }

    @Test
    public void deleteStationById() throws DAOException{

        Station station = new Station();
        station.setStationName("troptrop");
        String dateString = "2011-11-11 11:11:11";
        station.setAddTime(dateString);

        String SQL_ADD_NEW_STATION = "INSERT INTO stations (station_name, user_id, adding_time)" +
                " VALUES (?, ?, ?);";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_STATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, station.getStationName());
            pstmt.setString(2, String.valueOf(1));
            pstmt.setString(3, station.getAddTime());
            pstmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e){
            throw new DuplicateStationExeption("Such record already exist in db", e);

        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DAOException("SQL exception", ex);
        }

        Station stationFromDb = stImpl.getStationByName("troptrop");
        int stId = stationFromDb.getRecordId();

        stImpl.deleteStationById(String.valueOf(stId));
        Station nullStation = stImpl.getStationByName("troptrop");
        assertNull(nullStation);

    }

    @Test
    public void countAllStationsTest() throws DAOException{
        Station station = new Station();
        station.setStationName("troptrop");

        int stationCount = stImpl.countAllStations();

        String SQL_ADD_NEW_STATION = "INSERT INTO stations (station_name)" +
                " VALUES (?);";

        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_STATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, station.getStationName());
            pstmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e){
            throw new DuplicateStationExeption("Such record already exist in db", e);

        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DAOException("SQL exception", ex);
        }

        int stationCountAfterAddNewStation = stImpl.countAllStations();

        assertEquals(stationCount + 1, stationCountAfterAddNewStation);


        String SQL_DELETE_STATION_BY_NAME = "delete from stations where station_name = ?";
        try (Connection con = DbUtil.getDataSource().getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_STATION_BY_NAME, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "troptrop");
            pstmt.executeUpdate();
        } catch (SQLException ex){
            throw new DAOException("SQL exception", ex);
        }

    }

}