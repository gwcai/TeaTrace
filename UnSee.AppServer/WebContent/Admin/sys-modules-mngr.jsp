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
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/main.jsp" %>
<%
	final String VIEW_CODE = "sys_modules_view";
	final String DICTIONARY_SYSTEMS = "所有子系统字典";
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);

	variables.put("soe", soe);
	variables.put("title", "系统模块管理");	
	variables.put("contentPage", "common/crud-table.html");
	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("系统模块管理");
	tableTmpl.setSubTitle("对系统模块进行维护和管理");
	
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	
	try
	{	
		DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
		Dictionary dictSystems = dictionaryManager.getDictionary(DICTIONARY_SYSTEMS, true);
		variables.put("systems", dictSystems.toArray());
	}
	catch(Exception ex) {
		ex.printStackTrace();
	}
	
	DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
	tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE, RequestUtil.requestParameterToMap(request), provider));
	
	EditorDialog dialog = new EditorDialog();
	dialog.setId("editor");
	dialog.setTemplatePath("/Admin/Editors/sys-modules-editor.html");
	dialog.setTitle("编辑");
	dialog.setGetActionUrl(String.format("%s/service/CommonEntity.action?verb=get&en=SysModulesEntity", getServletContext().getContextPath()));
	dialog.setUpdateActionUrl(String.format("%s/service/CommonEntity.action?verb=put&en=SysModulesEntity", getServletContext().getContextPath()));
	dialog.setRemoveActionUrl(String.format("%s/service/CommonEntity.action?verb=remove&en=SysModulesEntity", getServletContext().getContextPath()));
	tableTmpl.setDialog(dialog);
	
	variables.put("table", tableTmpl);
	
	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>