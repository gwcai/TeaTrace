package com.unsee.gaia.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.unsee.gaia.web.util.SessionHelper;

public class DebugLoginServletFilter implements Filter {
	private final static Logger logger = Logger.getLogger(DebugLoginServletFilter.class);
	
	private String userName = null;
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
		userName = filterConfig.getInitParameter("userName");
    }

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        logger.debug("login with account: " + userName);
        SessionHelper.userLogon(httpRequest, httpResponse, userName);
        
        chain.doFilter(request, response);
    }
}