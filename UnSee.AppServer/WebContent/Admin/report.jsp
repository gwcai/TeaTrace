<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.unsee.gaia.web.vo.*"%>
<%@page import="com.unsee.gaia.web.util.*"%>
<%@page import="com.unsee.gaia.web.template.*"%>
<%@page import="com.unsee.gaia.web.template.table.*"%>
<%@page import="com.unsee.gaia.biz.services.*"%>
<%@page import="com.unsee.gaia.web.codeset.*"%>
<%@page import="com.unsee.gaia.biz.entities.*"%>
<%@page import="com.unsee.gaia.dal.codeset.*"%>
<%@page import="com.unsee.gaia.dal.codeset.Dictionary"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/main.jsp" %>
<%
	final String VIEW_CODE = request.getParameter("viewCode");
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);

	variables.put("title", soe.getObjectName());	
	variables.put("contentPage", "common/report.html");
	variables.put("soe", soe);
	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle(soe.getObjectName());
	tableTmpl.setSubTitle(soe.getObjectDesc());
	
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	
	DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
	DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
	try
	{
		tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE, RequestUtil.requestParameterToMap(request), provider));
	}
	catch(Exception ex) {
		ex.printStackTrace();
	}
	
	variables.put("table", tableTmpl);

	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>