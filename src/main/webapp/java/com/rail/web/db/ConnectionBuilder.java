package com.rail.web.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This interface that defines a connection to a database.
 */

public interface ConnectionBuilder {
    Connection getConnection() throws SQLException;
}
