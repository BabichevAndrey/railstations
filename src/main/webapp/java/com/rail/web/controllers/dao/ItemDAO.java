package com.rail.web.controllers.dao;

import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.Model;

import java.util.List;

/**
 * @author Andrii Babichev
 * @version 1.0
 * A common interface filled with basic methods for all entities.
 */

public interface ItemDAO <T extends Model>{

    public T getById(int id) throws DAOException;

    public List<T> getAll();

    public void addNew(T model) throws DAOException;

    public void update(T model) throws DAOException;

    public void remove(T model);
}
