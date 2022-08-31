package com.rail.web.controllers.dao.models;

/**
 * @author Andrii Babichev
 * @version 1.0
 * Describes the user entity. All fields that exist in the database and methods for receiving and returning data.
 */

public class User extends Model{

    private String login;

    private String password;

    private String fullName;

    private int roleId;

    @Override
    public String toString() {
        return "User{" +
                "password = " + password +
                ", login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roleId=" + roleId +
                '}';
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public int getRoleId() {
        return roleId;
    }
}
