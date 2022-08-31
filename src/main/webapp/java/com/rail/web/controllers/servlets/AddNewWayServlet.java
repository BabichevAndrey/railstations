package com.rail.web.controllers.servlets;

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.models.Station;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class is a servlet container to create a new route.
 */


@WebServlet("/addnewway")
public class AddNewWayServlet extends HttpServlet {

    private DAOFactory daoFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Station> stations = daoFactory.getStationDAO().getAll();
        request.setAttribute("stationList", stations);
        request.getRequestDispatcher("/addway.jsp").forward(request, response);
    }

}