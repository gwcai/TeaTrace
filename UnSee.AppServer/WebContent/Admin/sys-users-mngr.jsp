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
	final String VIEW_CODE = "sys_users_view";
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);

	variables.put("title", "用户管理");	
	variables.put("contentPage", "sys-users-table.html");
	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("用户管理");
	tableTmpl.setSubTitle("对系统用户（账户）进行管理");
	
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	
	DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
	tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE, RequestUtil.requestParameterToMap(request), provider));
	
	EditorDialog dialog = new EditorDialog();
	dialog.setId("editor");
	dialog.setTemplatePath("/Admin/Editors/sys-user-editor.html");
	dialog.setTitle("编辑");
	dialog.setGetActionUrl(String.format("%s/service/SysUsers.action?verb=get", getServletContext().getContextPath()));
	dialog.setUpdateActionUrl(String.format("%s/service/SysUsers.action?verb=put", getServletContext().getContextPath()));
	dialog.setRemoveActionUrl(String.format("%s/service/SysUsers.action?verb=remove&target=removeUser", getServletContext().getContextPath()));
	tableTmpl.setDialog(dialog);
	
	variables.put("table", tableTmpl);
	variables.put("roles", SysRolesService.getInstance().getAllObjects(null));
	
	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>