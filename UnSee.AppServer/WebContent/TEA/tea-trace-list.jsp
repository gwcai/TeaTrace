<%@page import="com.unsee.gaia.biz.ibatis.RowBoundsEx"%>
<%@page import="com.unsee.util.StringUtil" %>
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
<%
	final String VIEW_CODE = "tea_trace_view";
	final String SALE_VIEW_CODE = "tea_batch_sale_info";

	Map queryMap = new HashMap();
	HashMap<String,Object> variables = new HashMap<String,Object>();

	variables.put("title", "茶叶信息管理");	
	variables.put("contentPage", "/TEA/tea-trace-list.html");
	//获取服务器IP
	Setting setting = (Setting)request.getServletContext().getAttribute("app.setting");
	String serverIp = setting.getVariable("server.ip");
	if(StringUtil.isNullOrEmpty(serverIp)){
		serverIp = "localhost";
	}
	variables.put("serverIp", serverIp);
	variables.put("queryArgs", RequestUtil.requestParameterToMap(request));
	
	try
	{	
		DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
		Dictionary dictNode = dictionaryManager.getDictionary("节点", true);
		variables.put("dictNode", dictNode);	}
	catch(Exception ex) {
		ex.printStackTrace();
	}
	
	int pageSize,pageIndex;
	try{
		pageSize = Integer.parseInt(request.getParameter("pageSize"));
	}catch(Exception ex){
		pageSize = 20;
	}
	try{
		pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
	}catch(Exception ex){
		pageIndex = 0;
	}
	
	Pagination pagination = new Pagination();
	RowBoundsEx rowBounds = new RowBoundsEx(pageIndex,pageSize);
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	SysObjectsEntity soe = null;
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("节点信息管理");
	tableTmpl.setSubTitle("茶叶节点信息查询");

	if(!StringUtil.isNullOrEmpty(request.getParameter("batch"))){
		if(!"6".equals(request.getParameter("nodeId"))){
			queryMap.clear();
			queryMap.put("objectCode", VIEW_CODE);
			soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);
			tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));	
			DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
			tableTmpl.setRows(sos.querySystemViewByCodeName(VIEW_CODE, RequestUtil.requestParameterToMap(request), provider, new IWhereProvider(){
				@Override
				public String buildWhere() {
					StringBuffer sb = new StringBuffer(" 1=1 ");
					if(!StringUtil.isNullOrEmpty(request.getParameter("nodeId"))){
						if(!"0".equalsIgnoreCase(request.getParameter("nodeId")))
							sb.append(" AND node_id="+request.getParameter("nodeId"));
					}
					if(!StringUtil.isNullOrEmpty(request.getParameter("batch"))){
						sb.append(" AND batch like '%"+request.getParameter("batch") + "%'");
					}
					return sb.toString();
				}
			}, "Simple", rowBounds));
		}else{
			queryMap.clear();
			queryMap.put("objectCode", SALE_VIEW_CODE);
			soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);
			tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
			DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
			tableTmpl.setRows(sos.querySystemViewByCodeName(SALE_VIEW_CODE, RequestUtil.requestParameterToMap(request), provider, new IWhereProvider(){
				@Override
				public String buildWhere() {
					StringBuffer sb = new StringBuffer(" 1=1 ");
					if(!StringUtil.isNullOrEmpty(request.getParameter("batch"))){
						sb.append(" AND batch like '%"+request.getParameter("batch") + "%'");
					}
					return sb.toString();
				}
			}, "Simple", rowBounds));
		}
	}
	
	pagination.setSize(pageSize);
	pagination.setIndex(pageIndex);
	pagination.setCount(rowBounds.getRowCount());
	pagination.setTotal(rowBounds.getPageTotal());
	tableTmpl.setPagination(pagination);
	variables.put("table", tableTmpl);
	
	PageRender render = new PageRender(getServletContext(), "/META-INF/Template/", "/Admin/content.html", variables);
	render.render(out);
%>