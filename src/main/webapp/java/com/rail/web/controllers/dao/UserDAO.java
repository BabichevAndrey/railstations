package com.rail.web.controllers.dao;

import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.User;

/**
 * @author Andrii Babichev
 * @version 1.0
 * Interface of basic methods for entity ticket.
 */

public interface UserDAO extends ItemDAO<User>{

    User findUserByLogin(String login) throws DAOException;
}
