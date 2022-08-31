package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.exception.DAOCloseTransactionConnection;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.exception.DAOTransactionSQLException;
import com.rail.web.controllers.dao.models.Route;
import com.rail.web.controllers.dao.models.User;
import com.rail.web.controllers.dao.TicketDAO;
import com.rail.web.db.PoolcConnectionBuilder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


/**
 * @author Andrii Babichev
 * @version 1.0
 * This class implements the ticket entity interface.
 */

public class TicketImplDaoMySql implements TicketDAO {

    private final DataSource ds;

    public TicketImplDaoMySql(DataSource dataSource) {
        ds = dataSource;
    }

    Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public Route getById(int id) {
        return null;
    }

    @Override
    public List<Route> getAll() {
        return null;
    }

    @Override
    public void addNew(Route model) {

    }

    @Override
    public void update(Route model) {

    }

    @Override
    public void remove(Route model) {

    }

    /**
     * transaction operation to buy a ticket
     * @param route
     * @param user
     * @return ticket id
     * @throws DAOException
     */
    @Override
    public int addNewTicket(Route route, User user) throws DAOException {

        String SQL_ADD_NEW_WAY = "INSERT INTO tickets (routes_id, users_id, price)" +
                " VALUES (?, ?, ?);";
        String SQL_DECREMET_TICKET_ROUTE = "UPDATE routes SET free_places = free_places - 1";

        String SQL_SELECT_SCOPE_IDENTITY = "SELECT * FROM tickets WHERE id=(SELECT max(id) FROM tickets);";

        Connection con = null;
        try {
            con = this.getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_WAY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, String.valueOf(route.getId()));
            pstmt.setString(2, String.valueOf(user.getId()));
            pstmt.setString(3, String.valueOf(route.getRoutePrice()));
            pstmt.executeUpdate();

            pstmt = con.prepareStatement(SQL_DECREMET_TICKET_ROUTE);
            pstmt.executeUpdate();

            pstmt = con.prepareStatement(SQL_SELECT_SCOPE_IDENTITY);
            con.commit();

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                return rs.getInt("id");
            }
        }  catch (SQLException ex){
            try {
                con.rollback();
            } catch (SQLException e) {
                throw new DAOException("Rollback issues", e);
            }
            throw new DAOTransactionSQLException("Transaction has not complete", ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new DAOCloseTransactionConnection("Connection close issues", e);
            }
        }
        return 0;
    }
}
