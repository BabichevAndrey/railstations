package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.UserDAO;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.exception.DuplicateStationExeption;
import com.rail.web.controllers.dao.exception.DuplicateUserExeption;
import com.rail.web.controllers.dao.models.User;
import com.rail.web.db.PoolcConnectionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class implements the user entity interface.
 */

public class UserImplDaoMySql implements UserDAO {


    private final DataSource ds;

    public UserImplDaoMySql(DataSource dataSource) {
        ds = dataSource;
    }

    Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


    /**
     * find user by login
     * @param login
     * @return user info
     * @throws DAOException
     */
    @Override
    public User findUserByLogin(String login) throws DAOException {

        String SQL_FIND_USER_BY_LOGIN = "select * from users where login=?";

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return extractUser(rs);
            }

   //         LOG.debug("User has been find");
        } catch (SQLException ex){
            throw new DAOException("No DB connection", ex);
        }

        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void addNew(User user) throws DAOException{

        String SQL_ADD_NEW_STATION = "INSERT INTO users (login, user_name, password, role)" +
                " VALUES (?, ?, ?, ?);";

        try (Connection con = this.getConnection()){
            PreparedStatement pstmt =  con.prepareStatement(SQL_ADD_NEW_STATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, String.valueOf(user.getRoleId()));
            pstmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e){
            throw new DuplicateUserExeption("Such record already exist in db", e);

        } catch (SQLException ex){
            throw new DAOException("SQL exception", ex);
        }

    }

    @Override
    public void update(User model) {

    }

    @Override
    public void remove(User model) {

    }

    /**
     * extract user info from resultset
     * @param rs
     * @return user info
     * @throws SQLException
     */
    private User extractUser(ResultSet rs) throws SQLException{
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRoleId(rs.getInt("role"));
        user.setFullName(rs.getString("user_name"));
        return user;
    }

}
