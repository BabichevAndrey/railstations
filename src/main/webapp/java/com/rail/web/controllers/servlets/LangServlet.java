package com.rail.web.controllers.servlets;

import javax.servlet.http.Cookie;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LangServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String locale = req.getParameter("lang");
 //       req.getSession().setAttribute("langLocale", locale);

//        Cookie localeCookie = new Cookie("locale_cookie", locale);
//        localeCookie.setMaxAge(60*60*24);
//        resp.addCookie( localeCookie );
////
//        req.setAttribute("langLocale", locale);

        resp.addCookie(new Cookie("defaultLocale", locale));
        String referer = req.getHeader("referer");
        resp.sendRedirect(referer);
    }
}
