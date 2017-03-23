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
<%@include file="common/main.jsp" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	final String VIEW_CODE = "sys_menus_view";
	final String DICTIONARY_MODULES = "系统模块列表.有效的";
	final String DICTIONARY_FEATURES = "系统功能列表.有效的";
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);

	variables.put("title", "菜单管理");	
	variables.put("contentPage", "menu/nestable-list.html");
	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("菜单管理");
	tableTmpl.setSubTitle("对系统菜单进行管理");
	
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	
	ToolItem toolItem = new ToolItem();
	toolItem.setTitle("增加根菜单");
	toolItem.setIcon("glyphicon-plus");
	toolItem.setAction("add");
	
	tableTmpl.getToolBar().addToolItem(toolItem);
	
	try
	{	
		DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
		Dictionary dictModules = dictionaryManager.getDictionary(DICTIONARY_MODULES, true);
		variables.put("modules", dictModules.toArray());
		Dictionary dictFeatures = dictionaryManager.getDictionary(DICTIONARY_FEATURES, true);
		variables.put("features", dictFeatures.toArray());
	}
	catch(Exception ex) {
		ex.printStackTrace();
	}
	
	DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
	tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE, RequestUtil.requestParameterToMap(request), provider));
	
	EditorDialog dialog = new EditorDialog();
	dialog.setId("editor");
	dialog.setTemplatePath("/Admin/Editors/sys-menu-editor.html");
	dialog.setTitle("编辑");
	dialog.setGetActionUrl(String.format("%s/service/CommonEntity.action?verb=get&en=SysMenusEntity", getServletContext().getContextPath()));
	dialog.setUpdateActionUrl(String.format("%s/service/CommonEntity.action?verb=put&en=SysMenusEntity", getServletContext().getContextPath()));
	dialog.setRemoveActionUrl(String.format("%s/service/CommonEntity.action?verb=remove&en=SysMenusEntity", getServletContext().getContextPath()));
	tableTmpl.setDialog(dialog);
	
	variables.put("table", tableTmpl);	
	
	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>