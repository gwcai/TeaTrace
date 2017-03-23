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
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/main.jsp"%>
<%
	request.setCharacterEncoding("UTF-8");
	final String VIEW_CODE = request.getParameter("viewCode");

	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity) sos.getEntityByRequest(queryMap);

	variables.put("title", soe.getObjectName());
	variables.put("contentPage", "common/crud-pagination-table.html");
	variables.put("soe", soe);

	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle(soe.getObjectName());
	tableTmpl.setSubTitle(soe.getObjectDesc());

	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	tableTmpl.setRowTemplate("pagination-rows.html");

	// 分页参数	
	Pagination pagination = new Pagination();

	int pageSize = 20;
	try {
		pageSize = Integer.valueOf(request.getParameter("size"));
	} catch (NumberFormatException ex) {
		pageSize = 20;
	}

	int pageIndex = 0;
	try {
		pageIndex = Integer.valueOf(request.getParameter("index"));
	} catch (NumberFormatException ex) {
		pageIndex = 0;
	}

	pagination.setIndex(pageIndex);
	pagination.setSize(pageSize);

	RowBoundsEx rowBounds = new RowBoundsEx(pageIndex, pageSize);
	DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
	Map queryArgs = RequestUtil.requestParameterToMap(request, true);
	variables.put("queryArgs", queryArgs);
	tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE,
			queryArgs, provider,
			null, null, rowBounds));

	pagination.setCount(rowBounds.getRowCount());
	pagination.setTotal(rowBounds.getPageTotal());
	tableTmpl.setPagination(pagination);
	// End of 分页参数

	EditorDialog dialog = new EditorDialog();
	dialog.setId("editor");
	dialog.setTitle("编辑");
	dialog.setTemplatePath("/Admin/Editors/common-editor.html");
	dialog.setGetActionUrl(String.format("%s/service/CommonData.action?verb=get&objectCode=" + VIEW_CODE, getServletContext().getContextPath()));
	dialog.setUpdateActionUrl(String.format("%s/service/CommonData.action?verb=put&objectCode=" + VIEW_CODE, getServletContext().getContextPath()));
	dialog.setRemoveActionUrl(String.format("%s/service/CommonData.action?verb=put&objectCode=" + VIEW_CODE,getServletContext().getContextPath()));
	tableTmpl.setDialog(dialog);

	variables.put("table", tableTmpl);
	
	ToolItem newItem = new ToolItem();
	tableTmpl.getToolBar().addToolItem(newItem.setAction("doNew").setIcon("fa-pencil-square-o").setTitle("新增").setId("new"));

	PageRender render = new PageRender(getServletContext(), templateRoot, "/Admin/content.html", variables);
	render.render(out);
%>