package com.rail.web.listener;


import com.rail.web.command.*;
import com.rail.web.controllers.dao.DAOCloudScape;
import com.rail.web.controllers.dao.DAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Andrii Babichev
 * @version 1.0
 * When the application starts, it creates a dynamic container with all the commands. Selects the required dao layer.
 */

@WebListener
public class ContexListener implements ServletContextListener, HttpSessionListener {

    private static final Logger LOG = LoggerFactory.getLogger(ContexListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        javax.servlet.jsp.jstl.fmt.LocaleSupport l;
        LOG.debug("Start context initialization");
        ServletContext context = sce.getServletContext();
        LOG.debug("DataSource initialized");
        initServices(context);
        LOG.debug("Services initialized");

        String realPath = context.getRealPath("WEB-INF/classes/messages_en.properties");
        LOG.info(realPath);

        if(LOG.isDebugEnabled()){
            LOG.debug("Debug info:" + context);
        }
    }

    private void initServices(ServletContext context) {

        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOCloudScape.DAOCLOUDSCAPE.getSubd());

        CommandContainer commands = new CommandContainer();
        Command command = new IndexCommand();
        commands.addCommand(null, command);
        commands.addCommand("", command);

        command = new LoginCommand(daoFactory);
        commands.addCommand("login", command);
        command = new LogoutCommand();
        commands.addCommand("logout", command);
        command = new FindRouteCommand(daoFactory);
        commands.addCommand("find_route", command);
        command = new AddNewStation(daoFactory);
        commands.addCommand("add_station", command);
        command = new ShowStationList(daoFactory);
        commands.addCommand("showStations", command);
        command = new AddNewWay(daoFactory);
        commands.addCommand("add_new_way", command);
        command = new EditWayCommand(daoFactory);
        commands.addCommand("edit_route", command);
        command = new BuyTicketCommand(daoFactory);
        commands.addCommand("buy_ticket", command);
        command = new ShowTicketCommand(daoFactory);
        commands.addCommand("ticket", command);
        command = new RegistrationCommand(daoFactory);
        commands.addCommand("registration", command);

        context.setAttribute("commandContainer", commands);
        context.setAttribute("daoFactory", daoFactory);
        LOG.trace("context.setAttribute 'commandContainer': {}", commands);
    }
}



















