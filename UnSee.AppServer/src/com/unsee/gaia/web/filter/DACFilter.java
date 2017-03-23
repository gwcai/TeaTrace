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

import com.sun.jndi.toolkit.url.UrlUtil;
import com.unsee.gaia.web.util.SessionHelper;

/**
 * Servlet Filter implementation class DACFilter
 */

public class DACFilter implements Filter {
	private final static Logger logger = Logger.getLogger(DACFilter.class);

	/**
	 * Default constructor.
	 */
	public DACFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		StringBuffer currentURL = request.getRequestURL(); // 取得根目录所对应的绝对路径:
		String currentURI = request.getRequestURI();

		if (!requestOpenResource(request.getContextPath(), currentURI)) {
			// 判断当前页是否是重定向以后的登录页面页面，如果是就不做session的判断，防止出现死循环
			if (!SessionHelper.isUserLogon(request)) {
				response.sendRedirect(request.getContextPath()
						+ "/Admin/login.jsp?url="
						+ UrlUtil.encode(currentURL.toString(), "UTF-8"));
				return;
			} 
//			else if (!currentURI.endsWith("/Admin/")
//					&& !currentURI.endsWith("/Admin/index.jsp")
//					&& currentURI.contains("/Admin/")) {
//				String featureCode = request.getParameter("featureCode");
//				if (StringUtil.isNullOrEmpty(featureCode)
//						|| !SysUsersService.getInstance().userHasFeatureCode(
//								SessionHelper.getCurrentAccount(request),
//								featureCode)) {
//					response.sendRedirect(request.getContextPath()
//							+ "/deny.jsp?url="
//							+ UrlUtil.encode(currentURL.toString(), "UTF-8"));
//				}
//			}
		}

		// 加入filter链继续向下执行
		filterChain.doFilter(request, response);
	}

	private boolean requestOpenResource(String contextPath, String targetURL) {
		return targetURL.startsWith(String.format("%s/Admin/login.jsp",
				contextPath))
				|| !targetURL.startsWith(String
						.format("%s/Admin/", contextPath));
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
