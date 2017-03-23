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
	final String VIEW_CODE = "sys_roles_view";
	final String DICTIONARY_FEATURES = "系统功能列表.有效的";
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);

	variables.put("soe", soe);	
	variables.put("title", "角色管理");	
	variables.put("contentPage", "common/crud-table.html");
	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("角色管理");
	tableTmpl.setSubTitle("对系统角色进行维护");
	
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	
	try
	{	
		variables.put("features", SysFeaturesService.getInstance().getAllObjects(null));
	}
	catch(Exception ex) {
		ex.printStackTrace();
	}
	
	DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
	tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE, RequestUtil.requestParameterToMap(request), provider));
	
	EditorDialog dialog = new EditorDialog();
	dialog.setId("editor");
	dialog.setTemplatePath("/Admin/Editors/sys-role-editor.html");
	dialog.setTitle("编辑");
	dialog.setGetActionUrl(String.format("%s/service/CommonEntity.action?verb=get&en=SysRolesEntity", getServletContext().getContextPath()));
	dialog.setUpdateActionUrl(String.format("%s/service/CommonEntity.action?verb=put&en=SysRolesEntity", getServletContext().getContextPath()));
	dialog.setRemoveActionUrl(String.format("%s/service/CommonEntity.action?verb=remove&en=SysRolesEntity", getServletContext().getContextPath()));
	tableTmpl.setDialog(dialog);
	
	variables.put("table", tableTmpl);
	
	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>