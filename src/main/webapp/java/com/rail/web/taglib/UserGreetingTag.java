package com.rail.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;

public class UserGreetingTag extends TagSupport {
    private static final long serialVersionUID = 1L;


    @Override
    public int doStartTag() throws JspException {

        try {
            pageContext.getOut().print(LocalDateTime.now());
        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }
}
