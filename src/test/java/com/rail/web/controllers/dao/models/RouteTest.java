package com.rail.web.controllers.dao.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteTest {

    public Route route = new Route();

    @Before
    public void createRoute(){
        route.setRoutePrice(150);
        route.setStationIdStart(1);
        route.setStationIdArrival(2);
        route.setFreePlaces(100);
        route.setDateStart("11-11-2011 11:11");
        route.setDateArrival("14-05-2012 12:12");
        route.setTraineNumber(21);
        route.setStationArrivalName("kiev");
        route.setStationStartName("london");
    }

    @Test
    public void setStationStartName() {
        route.setStationStartName("rovno");
        assertEquals("rovno", route.getStationStartName());
    }

    @Test
    public void setStationArrivalName() {
        route.setStationArrivalName("lubny");
        assertEquals("lubny", route.getStationArrivalName());
    }

    @Test
    public void setStationIdArrival() {
        route.setStationIdArrival(8);
        assertEquals(8, route.getStationIdArrival());
    }

    @Test
    public void setTraineNumber() {
        route.setTraineNumber(42);
        assertEquals(42, route.getTraineNumber());
    }

    @Test
    public void setStationIdStart() {
        route.setStationIdStart(9);
        assertEquals(9, route.getStationIdStart());
    }

    @Test
    public void setDateStart() {
        route.setDateStart("09-09-2009 14:14");
        assertEquals("09-09-2009 14:14", route.getDateStart());
    }

    @Test
    public void setDateArrival() {
        route.setDateArrival("09-09-2019 15:15");
        assertEquals("09-09-2019 15:15", route.getDateArrival());
    }

    @Test
    public void setFreePlaces() {
        route.setFreePlaces(82);
        assertEquals(82, route.getFreePlaces());
    }

    @Test
    public void setRoutePrice() {
        route.setRoutePrice(425);
        assertEquals(425, route.getRoutePrice());
    }

    @Test
    public void getStationIdArrival() {
        assertEquals(2, route.getStationIdArrival());
    }

    @Test
    public void getStationStartName() {
        assertEquals("london", route.getStationStartName());
    }

    @Test
    public void getStationArrivalName() {
        assertEquals("kiev", route.getStationArrivalName());
    }

    @Test
    public void getTraineNumber() {
        assertEquals(21, route.getTraineNumber());
    }

    @Test
    public void getStationIdStart() {
        assertEquals(1, route.getStationIdStart());
    }

    @Test
    public void getDateStart() {
        assertEquals("11-11-2011 11:11", route.getDateStart());
    }

    @Test
    public void getDateArrival() {
        assertEquals("14-05-2012 12:12", route.getDateArrival());
    }

    @Test
    public void getFreePlaces() {
        assertEquals(100, route.getFreePlaces());
    }

    @Test
    public void getRoutePrice() {
        assertEquals(150, route.getRoutePrice());
    }
}