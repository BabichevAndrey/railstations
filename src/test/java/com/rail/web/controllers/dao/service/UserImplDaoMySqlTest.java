package com.rail.web.controllers.dao.service;

import com.rail.web.controllers.dao.DbUtil;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class UserImplDaoMySqlTest {

    static UserImplDaoMySql userImpl;


    @Before
    public void setUpBeforeClass() throws Exception {
        userImpl = new UserImplDaoMySql(DbUtil.getDataSource());
    }

    @Test
    public void testGetConnection() {
        assertNotNull(userImpl);
        try (Connection connection = userImpl.getConnection()) {
        } catch (SQLException e) {
            fail("Cannot get connection");
        }
    }

    @Test
    public void findUserByLoginTest() throws DAOException{

        try {
            User user = new User();
            user.setLogin("testusertestlogin");
            user.setFullName("usernametesttest");
            user.setPassword("012345");
            user.setRoleId(1);

            String SQL_ADD_NEW_STATION = "INSERT INTO users (login, user_name, password, role)" +
                    " VALUES (?, ?, ?, ?);";

            try (Connection con = DbUtil.getDataSource().getConnection()) {
                PreparedStatement pstmt = con.prepareStatement(SQL_ADD_NEW_STATION, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, user.getLogin());
                pstmt.setString(2, user.getFullName());
                pstmt.setString(3, user.getPassword());
                pstmt.setString(4, String.valueOf(user.getRoleId()));
                pstmt.executeUpdate();

            } catch (SQLException ex) {
                throw new DAOException("SQL exception", ex);
            }

            User userFromDb = userImpl.findUserByLogin(user.getLogin());

            assertEquals(user.getLogin(), userFromDb.getLogin(), "Logins must be same");
            assertEquals(user.getFullName(), userFromDb.getFullName(), "FullName must be same");
            assertEquals(user.getPassword(), userFromDb.getPassword(), "Password must be same");
            assertEquals(user.getRoleId(), userFromDb.getRoleId(), "Role must be same");

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {

            String SQL_DELETE_USER_BY_LOGIN = "delete from users where login = ?";

            try (Connection con = DbUtil.getDataSource().getConnection()){
                PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_USER_BY_LOGIN);
                pstmt.setString(1, "testusertestlogin");
                pstmt.executeUpdate();
            } catch (SQLException ex){
                ex.printStackTrace();
                throw new DAOException("Deleting failed", ex);
            }
        }

    }


    @Test
    public void addNewTest() throws DAOException{

        try{
            User user = new User();
            user.setLogin("testusertestlogin");
            user.setFullName("usernametesttest");
            user.setPassword("012345");
            user.setRoleId(1);

            userImpl.addNew(user);

            User userFromDb = new User();
            String SQL_FIND_USER_BY_LOGIN = "select * from users where login=?";

            try (Connection con = DbUtil.getDataSource().getConnection()){
                PreparedStatement pstmt =  con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
                pstmt.setString(1, user.getLogin());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()){
                    userFromDb = extractUser(rs);
                }
            } catch (SQLException ex){
                throw new DAOException("No DB connection", ex);
            }


            assertEquals(user.getLogin(), userFromDb.getLogin(), "Logins must be same");
            assertEquals(user.getFullName(), userFromDb.getFullName(), "FullName must be same");
            assertEquals(user.getPassword(), userFromDb.getPassword(), "Password must be same");
            assertEquals(user.getRoleId(), userFromDb.getRoleId(), "Role must be same");

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {

            String SQL_DELETE_USER_BY_LOGIN = "delete from users where login = ?";

            try (Connection con = DbUtil.getDataSource().getConnection()){
                PreparedStatement pstmt =  con.prepareStatement(SQL_DELETE_USER_BY_LOGIN);
                pstmt.setString(1, "testusertestlogin");
                pstmt.executeUpdate();
            } catch (SQLException ex){
                ex.printStackTrace();
                throw new DAOException("Deleting failed", ex);
            }
        }

    }

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