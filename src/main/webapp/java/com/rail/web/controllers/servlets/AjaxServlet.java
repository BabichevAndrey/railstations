package com.rail.web.controllers.servlets;

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class is a servlet container to delete station or route using ajax.
 */

@WebServlet("/ajaxservlet")
public class AjaxServlet extends HttpServlet {

    private DAOFactory daoFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String stationId = request.getParameter("stationid");
        String wayId = request.getParameter("wayid");


        if (stationId != null){
            try {
                daoFactory.getStationDAO().deleteStationById(stationId);
                request.getSession().setAttribute("deletedStation", "Station has been deleted");
            } catch (DAOException e){
                request.getSession().setAttribute("deletedStation", "Troubles with connection");
            }

            String content = String.valueOf(request.getSession().getAttribute("deletedStation"));
            response.setContentType("text/plain");

            OutputStream outStream = response.getOutputStream();
            outStream.write(content.getBytes("UTF-8"));
            outStream.flush();
            outStream.close();
        }

        if(wayId != null){
            try {
                daoFactory.getRouteDAO().deleteWayById(wayId);
                request.getSession().setAttribute("deletedWay", "Way has been deleted");
            } catch (DAOException ex){
                request.getSession().setAttribute("deletedWay", "Some trouble with connection, please try again");
            }
            String content = String.valueOf(request.getSession().getAttribute("deletedWay"));
            response.setContentType("text/plain");

            OutputStream outStream = response.getOutputStream();
            outStream.write(content.getBytes("UTF-8"));
            outStream.flush();
            outStream.close();
        }
    }

}