<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.unsee.gaia.web.util.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Setting setting = (Setting)request.getServletContext().getAttribute("app.setting");

	HashMap<String,Object> variables = new HashMap<String,Object>();
	variables.put("title", "后台管理——登录");

	PageRender render = new PageRender(getServletContext(), setting.getPageTemplateRoot(), "Admin/login.html", variables);
	render.render(out);
%>