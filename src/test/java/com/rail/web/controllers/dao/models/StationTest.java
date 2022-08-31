package com.rail.web.controllers.dao.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StationTest {

    public Station station = new Station();

    @Before
    public void createStation(){
        station.setStationName("gorodno");
        station.setUserName("andrey");
        station.setRecordId(1);
        station.setAddTime("12-12-2012 20:12");
    }


    @Test
    public void setUserName() {
        station.setUserName("ivan");
        assertEquals("ivan", station.getUserName());
    }

    @Test
    public void getUserName() {
        assertEquals("andrey", station.getUserName());
    }

    @Test
    public void getStationName() {
        assertEquals("gorodno", station.getStationName());
    }

    @Test
    public void getAddTime() {
       assertEquals("12-12-2012 20:12", station.getAddTime());
    }

    @Test
    public void getRecordId() {
        assertEquals(1, station.getRecordId());
    }

    @Test
    public void setStationName() {
        station.setStationName("korovino");
        assertEquals("korovino", station.getStationName());
    }

    @Test
    public void setAddTime() {
        station.setAddTime("10-10-2010 10:10");
        assertEquals("10-10-2010 10:10", station.getAddTime());
    }

    @Test
    public void setRecordId() {
        station.setRecordId(2);
        assertEquals(2, station.getRecordId());
    }
}