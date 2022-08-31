package com.rail.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class StationInfoTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    private String a;
    private String b;
    private String time;


    @Override
    public int doStartTag() throws JspException {

        try {





            pageContext.getOut().print(String.format("<div class=\"station_list station_list_name\">\n" +
                    "                    %s\n" +
                    "            </div>", a));

            pageContext.getOut().print(String.format("<div class=\"station_list station_list_time\">\n" +
                    "                    %s\n" +
                    "            </div>", time));








        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setB(String b) {
        this.b = b;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getTime() {
        return time;
    }
}
