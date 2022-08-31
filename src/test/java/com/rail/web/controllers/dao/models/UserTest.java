package com.rail.web.controllers.dao.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    User user = new User();

    @Before
    public void createNewUser(){
        user.setId(1);
        user.setFullName("yura");
        user.setLogin("tarik");
        user.setPassword("12ig!9");
        user.setRoleId(1);
    }

    @Test
    public void setLogin() {
        user.setLogin("karte");
        assertEquals("karte", user.getLogin());
    }

    @Test
    public void setPassword() {
        user.setPassword("456fj");
        assertEquals("456fj", user.getPassword());
    }

    @Test
    public void setFullName() {
        user.setFullName("tolia");
        assertEquals("tolia", user.getFullName());
    }

    @Test
    public void setRoleId() {
        user.setRoleId(4);
        assertEquals(4, user.getRoleId());
    }

    @Test
    public void getLogin() {
        assertEquals("tarik", user.getLogin());
    }

    @Test
    public void getPassword() {
        assertEquals("12ig!9", user.getPassword());
    }

    @Test
    public void getFullName() {
        assertEquals("yura", user.getFullName());
    }

    @Test
    public void getRoleId() {
        assertEquals(1, user.getRoleId());
    }
}