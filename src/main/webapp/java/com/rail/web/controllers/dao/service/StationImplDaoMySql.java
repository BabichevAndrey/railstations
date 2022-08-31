package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.exception.DuplicateStationExeption;
import com.rail.web.controllers.dao.models.Station;
import com.rail.web.controllers.dao.StationDAO;
import com.rail.web.db.PoolcConnectionBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class implements the station entity interface.
 * Adds new station, deletes station, search station, updates station.
 */

public class StationImplDaoMySql implements StationDAO {


    private final DataSource ds;

    public StationImplDaoMySql(DataSource dataSource) {
        ds = dataSource;
    }

    Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public List<Station> getAll() {

        String SQL_FIND_ALL_STATIONS_AT_ALL ="select * from stations ORDER BY id";

        List<Station> stations = new ArrayList<>();

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt;
            pstmt = con.prepareStatement(SQL_FIND_ALL_STATIONS_AT_ALL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                stations.add(extractStation(rs));
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return stations;
    }

    @Override
    public void addStation(Station station) throws DAOException {
            String SQL_ADD_NEW_STATION = "INSERT INTO stations (station_name, user_id, adding_time)" +
                " VALUES (?, ?, ?);";

            try (Connection con = this.getConnection()){
                PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_STATION, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, station.getStationName());
                pstmt.setString(2, String.valueOf(station.getId()));
                pstmt.setString(3, station.getAddTime());
                pstmt.executeUpdate();

            } catch (SQLIntegrityConstraintViolationException e){
                throw new DuplicateStationExeption("Such record already exist in db", e);

            } catch (SQLException ex){
                ex.printStackTrace();
                throw new DAOException("SQL exception", ex);
            }
    }

    @Override
    public Station getStationByName(String stationName) throws DAOException {
        String SQL_FIND_STATION_BY_NAME ="select * from stations WHERE station_name = ?";
        Station stations = new Station();

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt;
            pstmt = con.prepareStatement(SQL_FIND_STATION_BY_NAME);
            pstmt.setString(1, stationName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return extractStation(rs);
            }

        } catch (SQLException ex){
            throw new DAOException("SQL exception", ex);
        }

        return null;
    }

    /**
     * find all station
     * @param offsetId
     * @return list of stations
     * @throws DAOException
     */
    @Override
    public List<Station> findStations(int offsetId, int rowsOnPage, int ordering) throws DAOException {

        String SQL_FIND_ALL_STATIONS = "select * from stations ORDER BY id LIMIT ? OFFSET ?";
        if (ordering == 2)
            SQL_FIND_ALL_STATIONS = "select * from stations ORDER BY station_name ASC LIMIT ? OFFSET ?";
        if (ordering == 3)
            SQL_FIND_ALL_STATIONS = "select * from stations ORDER BY station_name DESC LIMIT ? OFFSET ?";
        List<Station> stations = new ArrayList<>();

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt;
            pstmt = con.prepareStatement(SQL_FIND_ALL_STATIONS);
            pstmt.setInt(1, rowsOnPage);
            pstmt.setInt(2, offsetId*rowsOnPage);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                stations.add(extractStation(rs));
            }

        } catch (SQLException ex){
            throw new DAOException("SQL exception", ex);
        }

        return stations;
    }

    /**
     * delete station by id
     * @param stationId
     * @throws DAOException
     */
    @Override
    public void deleteStationById(String stationId) throws DAOException {

            String SQL_DELETE_STATION_BY_ID = "delete from stations where id = ?";

            try (Connection con = this.getConnection()){
                PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_STATION_BY_ID);
                pstmt.setString(1, stationId);
                pstmt.executeUpdate();
            } catch (SQLException ex){
                throw new DAOException("Troubles with connection", ex);
            }

    }

    /**
     * count all station in db
     * @return count of station
     * @throws DAOException
     */
    @Override
    public int countAllStations() throws DAOException{

        String SQL_FIND_COUNT_ALL_STATIONS = "select count(*) as count_stations from stations";

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt;
            pstmt = con.prepareStatement(SQL_FIND_COUNT_ALL_STATIONS);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("count_stations");
            }
        } catch (SQLException ex){
            throw new DAOException("No db connection", ex);
        }

        return 0;
    }

    @Override
    public void update(Station station) {

    }

    @Override
    public void remove(Station model) {

    }

    @Override
    public Station getById(int id) {
        return null;
    }

    @Override
    public void addNew(Station model) {

    }

    /**
     * extract data of station, which was found in db
     * @param rs
     * @return station
     * @throws SQLException
     */
    private Station extractStation(ResultSet rs) throws SQLException{
        Station station = new Station();
        station.setStationName(rs.getString("station_name"));
        station.setAddTime(rs.getString("adding_time"));
        station.setRecordId(rs.getInt("id"));
        return  station;
    }




}
