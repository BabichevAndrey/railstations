package com.rail.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class filters input for encoding.
 */

@WebFilter(urlPatterns = "/*",
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")},
        dispatcherTypes = {DispatcherType.REQUEST}
)
public class EncodingFilter implements Filter {

    public static final Logger LOG = LoggerFactory.getLogger(EncodingFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.trace("before chain");
        String characterEncoding = request.getCharacterEncoding();
        LOG.debug("current characterEncoding: {}", characterEncoding);
        if (characterEncoding == null) {
            LOG.debug("set encoding: {}", encoding);
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
        LOG.trace("after chain");
    }

}
