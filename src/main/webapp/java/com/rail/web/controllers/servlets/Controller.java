package com.rail.web.controllers.servlets;

import com.rail.web.command.Command;
import com.rail.web.command.CommandContainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class is a servlet container which is the controller when receiving get and post requests,
 * which are further processed by the corresponding classes.
 */

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private CommandContainer commands;

    @Override
    public void init(ServletConfig config) throws ServletException {
        commands = (CommandContainer) config.getServletContext().getAttribute("commandContainer");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandName = req.getParameter("command");
    //    System.out.println("commandName == > " + commandName);

    //    Command command =  CommandContainer.getCommand(commandName);
        Command command = commands.getCommand(commandName);

        String adress = "error.jsp";
        try {
            adress = command.execute(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage());
        }

        if (req.getSession().getAttribute("currentUser") == null){
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

        req.getRequestDispatcher(adress).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter("command");
//        Command command =  CommandContainer.getCommand(commandName);
        Command command =  commands.getCommand(commandName);

        String adress = "error.jsp";
        try {
            adress = command.execute(req, resp);
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
        }

//        if (req.getSession().getAttribute("currentUser") == null){
//            req.getRequestDispatcher("login.jsp").forward(req, resp);
//        }

   //     PRG
        resp.sendRedirect(adress);
    }
}