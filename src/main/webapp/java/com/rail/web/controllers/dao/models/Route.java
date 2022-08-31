package com.rail.web.controllers.dao.models;


/**
 * @author Andrii Babichev
 * @version 1.0
 * Describes the route entity. All fields that exist in the database and methods for receiving and returning data.
 */

public class Route extends Model{

    private int traineNumber;

    private int stationIdStart;

    private int stationIdArrival;

    private String dateStart;

    private String dateArrival;

    private String stationStartName;

    private String stationArrivalName;

    private String tripTime;

    private int freePlaces;

    private int routePrice;



    @Override
    public String toString() {
        return "Route{" +
                "traineNumber=" + traineNumber +
                ", stationIdStart=" + stationIdStart +
                ", getStationIdArrival=" + stationIdArrival +
                ", dateStart=" + dateStart +
                ", dateArrival=" + dateArrival +
                ", freePlaces=" + freePlaces +
                ", routePrice=" + routePrice +
                '}';
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setStationStartName(String stationStartName) {
        this.stationStartName = stationStartName;
    }

    public void setStationArrivalName(String stationArrivalName) {
        this.stationArrivalName = stationArrivalName;
    }

    public String getStationStartName() {
        return stationStartName;
    }

    public String getStationArrivalName() {
        return stationArrivalName;
    }

    public void setStationIdArrival(int stationIdArrival) {
        this.stationIdArrival = stationIdArrival;
    }

    public int getStationIdArrival() {
        return stationIdArrival;
    }

    public void setTraineNumber(int traineNumber) {
        this.traineNumber = traineNumber;
    }

    public void setStationIdStart(int stationIdStart) {
        this.stationIdStart = stationIdStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateArrival(String dateArrival) {
        this.dateArrival = dateArrival;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public void setRoutePrice(int routePrice) {
        this.routePrice = routePrice;
    }

    public int getTraineNumber() {
        return traineNumber;
    }

    public int getStationIdStart() {
        return stationIdStart;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateArrival() {
        return dateArrival;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public int getRoutePrice() {
        return routePrice;
    }
}
