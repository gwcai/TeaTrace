<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.unsee.gaia.web.vo.*"%>
<%@page import="com.unsee.gaia.web.util.*"%>
<%@page import="com.unsee.gaia.web.template.*"%>
<%@page import="com.unsee.gaia.web.template.table.*"%>
<%@page import="com.unsee.gaia.biz.services.*"%>
<%@page import="com.unsee.gaia.biz.ibatis.RowBoundsEx"%>
<%@page import="com.unsee.gaia.web.codeset.*"%>
<%@page import="com.unsee.gaia.biz.entities.*"%>
<%@page import="com.unsee.gaia.dal.codeset.*"%>
<%@page import="com.unsee.gaia.dal.codeset.Dictionary"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/main.jsp" %>
<%
	final String VIEW_CODE = "sys_features_view";
	final String DICTIONARY_MODULES = "系统模块列表.代码";
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);

	variables.put("title", "功能管理");	
	variables.put("contentPage", "common/crud-pagination-table.html");
	variables.put("soe", soe);
	
	try
	{	
		DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
		Dictionary dictModules = dictionaryManager.getDictionary(DICTIONARY_MODULES, true);
		variables.put("modules", dictModules.toArray());
	}
	catch(Exception ex) {
		ex.printStackTrace();
	}

	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("功能管理");
	tableTmpl.setSubTitle("对系统功能进行管理");
	
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	tableTmpl.setRowTemplate("detail-rows.html");
	
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
	tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE, RequestUtil.requestParameterToMap(request, true), provider, null, null, rowBounds));
	
	pagination.setCount(rowBounds.getRowCount());
	pagination.setTotal(rowBounds.getPageTotal());
	tableTmpl.setPagination(pagination);
	// End of 分页参数

	EditorDialog dialog = new EditorDialog();
	dialog.setId("editor");
	dialog.setTemplatePath("/Admin/Editors/sys-feature-editor.html");
	dialog.setTitle("编辑");
	dialog.setGetActionUrl(String.format("%s/service/CommonEntity.action?verb=get&en=SysFeaturesEntity", getServletContext().getContextPath()));
	dialog.setUpdateActionUrl(String.format("%s/service/CommonEntity.action?verb=put&en=SysFeaturesEntity", getServletContext().getContextPath()));
	dialog.setRemoveActionUrl(String.format("%s/service/CommonEntity.action?verb=remove&en=SysFeaturesEntity", getServletContext().getContextPath()));
	tableTmpl.setDialog(dialog);
		
	variables.put("table", tableTmpl);
	
	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>