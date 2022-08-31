package com.rail.web.command;


/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to go to main page</strong>.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(IndexCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        LOG.debug("Go to main page");
        return "start.jsp";
    }
}
