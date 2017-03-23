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
	final String VIEW_CODE = "sys_objects_view";
	final String DICTIONARY_SYS_OBJECT_TYPE = "系统对象分类";
	final String DICTIONARY_ENABLED_DICTIONARIES = "有效的字典列表";
	final String DICTIONARY_DATATYPE_DICTIONARIES = "受支持的DB数据类型";
	final String DICTIONARY_OPERATOR_DICTIONARIES = "受支持的DB运算符";
	final String DICTIONARY_CONTROL_DICTIONARIES = "受支持的控件类型";
	final String DICTIONARY_WEB_CONTROL_DICTIONARIES = "WEB控件集";
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntity(request.getParameter("id"));
	if(soe == null) soe = new SysObjectsEntity();

	variables.put("title", "编辑系统对象");	
	variables.put("contentPage", "/Admin/Editors/sys-objects-editor.html");
	variables.put("soe", soe);
	
	try
	{
		DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
		variables.put("dictObjecType", dictionaryManager.getDictionary(DICTIONARY_SYS_OBJECT_TYPE, true));
		variables.put("dictionaries", dictionaryManager.getDictionary(DICTIONARY_ENABLED_DICTIONARIES, true));
		variables.put("datatypes", dictionaryManager.getDictionary(DICTIONARY_DATATYPE_DICTIONARIES, true).toArray());
		variables.put("operators", dictionaryManager.getDictionary(DICTIONARY_OPERATOR_DICTIONARIES, true).toArray());
		variables.put("controls", dictionaryManager.getDictionary(DICTIONARY_CONTROL_DICTIONARIES, true).toArray());
		variables.put("webControls", dictionaryManager.getDictionary(DICTIONARY_WEB_CONTROL_DICTIONARIES, true));
	}
	catch(Exception ex) {
		ex.printStackTrace();
	}
	
	variables.put("objectId",request.getParameter("id"));	
	
	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>