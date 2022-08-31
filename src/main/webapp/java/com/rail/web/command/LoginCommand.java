package com.rail.web.command;


/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to login user in application</strong>.
 * Check for a correctly entered captcha.
 * Check for the existence of a login in the system.
 * Check if the entered password is correct.
 */

import com.mysql.cj.Session;
import com.rail.web.controllers.dao.DAOCloudScape;
import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.models.User;
import com.rail.web.listener.ContexListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(LoginCommand.class);

    private final DAOFactory daoFactory;

    public LoginCommand(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

//        String captcha = req.getParameter("captcha");
//        HttpSession session = req.getSession();
//        String captchaGeneratedValue = String.valueOf(session.getAttribute("dns_security_code"));
//
//        if(!captcha.equals(captchaGeneratedValue)){
//            req.getSession().setAttribute("errorMessage", "wrongcaptcha");
//            return "login";
//        }



        String login = req.getParameter("login");

        String password = req.getParameter("password");

        User user = null;
        try {
            user = daoFactory.getUserDAO().findUserByLogin(login);
            LOG.debug("User {} has been found", login);
        } catch (DAOException ex){
            LOG.debug("User {} input illegal login or passwod", login);
            req.getSession().setAttribute("errorMessage", "illegalloginorpasswordmessage");
        }

        if(user != null && user.getPassword().equals(password)){
            req.getSession().setAttribute("currentUser", user);
            LOG.info("User {} successful login at aplication", login);
   //         return "start.jsp";
            req.getRequestDispatcher("/start.jsp").forward(req, resp);
        }


        req.getSession().setAttribute("errorMessage", "illegalloginorpasswordmessage");
        return "login";

    }
}
