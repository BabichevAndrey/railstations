package com.rail.web.controllers.dao;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;


    public class DbUtil {

        private static final String URL = "jdbc:mysql://localhost:3306/mydb";
        private static final String USER = "root";
        private static final String PASSWORD = "root";

        public static DataSource getDataSource() {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL(URL);
            dataSource.setUser(USER);
            dataSource.setPassword(PASSWORD);
            return dataSource;
        }
    }

