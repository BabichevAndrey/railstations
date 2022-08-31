package com.rail.web.command;


/**
 * @author Andrii Babichev
 * @version 1.0
 * This class executes the command <strong>to registaring new user</strong>.
 * Check for a correctly entered captcha.
 * Check for the existence of a login in the system.
 * Add new user.
 */

import com.rail.web.controllers.dao.DAOFactory;
import com.rail.web.controllers.dao.exception.DAOException;
import com.rail.web.controllers.dao.exception.DuplicateStationExeption;
import com.rail.web.controllers.dao.exception.DuplicateUserExeption;
import com.rail.web.controllers.dao.models.Station;
import com.rail.web.controllers.dao.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RegistrationCommand implements Command {

    private final DAOFactory daoFactory;

    public RegistrationCommand(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String captcha = req.getParameter("captcha");
        HttpSession session = req.getSession();
        String captchaGeneratedValue = String.valueOf(session.getAttribute("dns_security_code"));





        String login = req.getParameter("login");
        req.getSession().setAttribute("login_reg", login);

        String name = req.getParameter("name");
        req.getSession().setAttribute("name_reg", name);

        String secondName = req.getParameter("second_name");
        req.getSession().setAttribute("secondName_reg", secondName);

        String eMail = req.getParameter("email");
        req.getSession().setAttribute("email_reg", eMail);

        String password1 = req.getParameter("password1");
        req.getSession().setAttribute("password1_reg", password1);

        String password2 = req.getParameter("password2");
        req.getSession().setAttribute("password2_reg", password2);

        if (login == "" || name =="" || secondName == "" || eMail == ""){
            req.getSession().setAttribute("errorMessage", "registration.inputallfields");
            return "registration";
        }

        if (!password1.equals(password2)){
            req.getSession().setAttribute("errorMessage", "registration.wrongpassword");
            return "registration";
        }

        if(!captcha.equals(captchaGeneratedValue)){
            req.getSession().setAttribute("errorMessage", "registration.captcha");
            return "registration";
        }

        User user = new User();
        user.setLogin(login);
        user.setFullName(secondName + " " + name);
        user.setPassword(password1);
        user.setRoleId(2);


        try {
            daoFactory.getUserDAO().addNew(user);
            LOG.debug("New user has been added");
            return "newuser.jsp";
        } catch (DuplicateUserExeption e){
            req.getSession().setAttribute("errorMessage", "User with such login is already exist!");
            LOG.warn("User with such login is already exist in db");
            req.getSession().setAttribute("login_reg", null);
        } catch (DAOException ex){
            req.getSession().setAttribute("errorMessage", ex);
            LOG.error("SQL exception at adding new user");
        }

        return "registration";


    }
}
