package com.rail.web.controllers.dao.models;

/**
 * @author Andrii Babichev
 * @version 1.0
 * Describes the ticket entity. All fields that exist in the database and methods for receiving and returning data.
 */

public class Ticket extends Model{
    private int routeIdTicket;

    private int userIdTicket;

    private int ticketPrice;

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getRouteIdTicket() {
        return routeIdTicket;
    }

    public int getUserIdTicket() {
        return userIdTicket;
    }

    public void setRouteIdTicket(int routeIdTicket) {
        this.routeIdTicket = routeIdTicket;
    }

    public void setUserIdTicket(int userIdTicket) {
        this.userIdTicket = userIdTicket;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "routeIdTicket=" + routeIdTicket +
                ", userIdTicket=" + userIdTicket +
                '}';
    }
}
