package com.rail.web.controllers.dao.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketTest {

    public Ticket ticket = new Ticket();

    @Before
    public void createTicket(){
        ticket.setRouteIdTicket(1);
        ticket.setUserIdTicket(15);
    }

    @Test
    public void getRouteIdTicket() {
        assertEquals(1, ticket.getRouteIdTicket());
    }

    @Test
    public void getUserIdTicket() {
        assertEquals(15, ticket.getUserIdTicket());
    }

    @Test
    public void setRouteIdTicket() {
        ticket.setRouteIdTicket(18);
        assertEquals(18, ticket.getRouteIdTicket());
    }

    @Test
    public void setUserIdTicket() {
        ticket.setUserIdTicket(10);
        assertEquals(10, ticket.getUserIdTicket());
    }
}