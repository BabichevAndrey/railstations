package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This is the interface, when called, the actions of the command are implemented.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
