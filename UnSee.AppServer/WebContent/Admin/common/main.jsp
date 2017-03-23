<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.unsee.gaia.web.util.*"%>
<%@page import="com.unsee.gaia.web.vo.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	HashMap<String,Object> variables = new HashMap<String,Object>();
	variables.put("title", "后台管理");
	
	Setting setting = (Setting)request.getServletContext().getAttribute("app.setting");
	String loadSystems = setting.getVariable("load.systems");
	String systems[] = loadSystems == null ? new String[]{} : loadSystems.split(",");
	String templateRoot = setting.getPageTemplateRoot();
	
	variables.put("menus", SysMenuItemVO.getMenusByUserId(Integer.valueOf(SessionHelper.getCurrentUser(request).getId()),systems));
	variables.put("currentUser", SessionHelper.getCurrentUser(request));
%>