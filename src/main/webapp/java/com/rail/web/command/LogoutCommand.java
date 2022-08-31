package com.rail.web.command;


/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to logout user from the applictation</strong>.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().removeAttribute("currentUser");
        LOG.debug("User logout");
        req.getRequestDispatcher("/start.jsp").forward(req, resp);
        return "start.jsp";
    }
}
