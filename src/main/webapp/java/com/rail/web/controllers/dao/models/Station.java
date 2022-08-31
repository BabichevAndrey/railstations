package com.rail.web.controllers.dao.models;

/**
 * @author Andrii Babichev
 * @version 1.0
 * Describes the station entity. All fields that exist in the database and methods for receiving and returning data.
 */

public class Station extends Model {

    private String stationName;

    private String addTime;

    private int recordId;

    private String userName;


    @Override
    public String toString() {
        return "Station{" +
                "stationName='" + stationName + '\'' +
                ", addTime='" + addTime + '\'' +
                ", recordId=" + recordId +
                ", userName='" + userName + '\'' +
                '}';
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getStationName() {
        return stationName;
    }

    public String getAddTime() {
        return addTime;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
