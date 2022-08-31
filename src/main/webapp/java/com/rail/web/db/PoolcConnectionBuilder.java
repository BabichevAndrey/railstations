package com.rail.web.db;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class is returns a database connection.
 */

public class PoolcConnectionBuilder implements ConnectionBuilder{


    private static PoolcConnectionBuilder instance = null;


    public static PoolcConnectionBuilder getInstance(){
        if (instance == null){
            instance =  new PoolcConnectionBuilder();
        }
        return instance;
    }


    @Override
    public Connection getConnection() throws SQLException {
  //      return dataSource.getConnection();
        Context ctx = null;
        DataSource dataSource = null;
        Connection c = null;
        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/projectRail");
            c =  dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    public DataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        return dataSource;
    }

}
