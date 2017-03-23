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
<%@page import="com.unsee.gaia.biz.ibatis.RowBoundsEx"%>
<%@include file="common/main.jsp" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	final String VIEW_CODE = "sys_code_data_view";
	final String DICTIONARY_CATALOGS = "代码集分类";
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);
	
	variables.put("soe", soe);
	variables.put("title", "系统代码集管理");
	variables.put("contentPage", "common/crud-pagination-table.html");
	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("系统代码集管理");
	tableTmpl.setSubTitle("对系统代码集进行管理");
	
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	
	DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
	Dictionary dictModules = dictionaryManager.getDictionary(DICTIONARY_CATALOGS, true);
	
	// 分页参数	
	Pagination pagination = new Pagination();
	
	int pageSize = 20;
	try
	{
		pageSize = Integer.valueOf(request.getParameter("size"));
	}
	catch(NumberFormatException ex) {
		pageSize = 20;
	}
	
	int pageIndex = 0;
	try
	{
		pageIndex = Integer.valueOf(request.getParameter("index"));
	}
	catch(NumberFormatException ex) {
		pageIndex = 0;
	}
	
	pagination.setIndex(pageIndex);
	pagination.setSize(pageSize);
	
	RowBoundsEx rowBounds = new RowBoundsEx(pageIndex,pageSize);
	DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
	tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE, RequestUtil.requestParameterToMap(request), provider, null, null, rowBounds));
	
	pagination.setCount(rowBounds.getRowCount());
	pagination.setTotal(rowBounds.getPageTotal());
	tableTmpl.setPagination(pagination);
	// End of 分页参数
	
	EditorDialog dialog = new EditorDialog();
	dialog.setId("editor");
	dialog.setTemplatePath("/Admin/Editors/sys-code-data-editor.html");
	dialog.setTitle("编辑");
	dialog.setGetActionUrl(String.format("%s/service/CommonEntity.action?verb=get&en=SysCodeDataEntity", getServletContext().getContextPath()));
	dialog.setUpdateActionUrl(String.format("%s/service/CommonEntity.action?verb=put&en=SysCodeDataEntity", getServletContext().getContextPath()));
	dialog.setRemoveActionUrl(String.format("%s/service/CommonEntity.action?verb=remove&en=SysCodeDataEntity", getServletContext().getContextPath()));
	tableTmpl.setDialog(dialog);
	
	ToolItem importItem = new ToolItem();
	importItem.setId("import");
	importItem.setTitle("导入");
	importItem.setAction("doImport");
	importItem.setIcon("fa-reply-all");
	tableTmpl.getToolBar().addToolItem(importItem);
	
	variables.put("table", tableTmpl);
	variables.put("modules", dictModules.toArray());
	
	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>