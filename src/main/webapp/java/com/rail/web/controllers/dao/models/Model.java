package com.rail.web.controllers.dao.models;

/**
 * @author Andrii Babichev
 * @version 1.0
 * Describes a unique model that is in all database table entities, namely a unique identifier
 */

public class Model implements java.io.Serializable{

    private int id;

    public Model() {

    }

    public Model(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
