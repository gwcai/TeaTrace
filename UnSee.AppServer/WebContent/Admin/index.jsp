<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.unsee.gaia.web.util.*"%>
<%@page import="com.unsee.gaia.web.vo.*"%>
<%@include file="common/main.jsp" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	variables.put("title", "后台管理");
	variables.put("currentUser", SessionHelper.getCurrentUser(request));

	System.out.println(templateRoot);
	PageRender render = new PageRender(getServletContext(), templateRoot, "Admin/foundation.html", variables);
	render.render(out);
%>


