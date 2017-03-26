<%@page import="com.unsee.tea.biz.services.CompanyService"%>
<%@page import="com.unsee.tea.biz.entities.CompanyEntity"%>
<%@page import="com.unsee.gaia.biz.ibatis.RowBoundsEx"%>
<%@page import="org.apache.commons.beanutils.DynaBeanPropertyMapDecorator"%>
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
	final String VIEW_CODE = "tea_batch_trace_view";
	final String SALE_VIEW_CODE = "tea_batch_sale_info";
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);
	HashMap<String,Object> variables = new HashMap<String,Object>();
	
	variables.put("soe",soe);
	variables.put("title", "茶叶批次信息");	
	variables.put("contentPage", "/TEA/tea-batch-trace.html");
	variables.put("batch",request.getParameter("batch"));
	
	//公司信息
	CompanyEntity entity = (CompanyEntity)CompanyService.getInstance().getCompanyInfo();
	if(null == entity) entity = new CompanyEntity();
	variables.put("company", entity);
	
	//获取服务器IP
		Setting setting = (Setting)request.getServletContext().getAttribute("app.setting");
		String serverIp = setting.getVariable("server.ip");
		if(StringUtil.isNullOrEmpty(serverIp)){
			serverIp = "localhost";
		}
		variables.put("serverIp", serverIp);
		
	try
	{	
		DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
		Dictionary dictNode = dictionaryManager.getDictionary("节点", true);
		variables.put("dictNode", dictNode);	}
	catch(Exception ex) {
		ex.printStackTrace();
	}

	TableTemplate tableTmpl = new TableTemplate();
	if(!StringUtil.isNullOrEmpty(request.getParameter("batch"))){
		DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
		queryMap = RequestUtil.requestParameterToMap(request);
		List<?> list = sos.querySystemViewByCodeName(VIEW_CODE, queryMap, provider, new IWhereProvider(){
			@Override
			public String buildWhere() {
				StringBuffer sb = new StringBuffer(" 1=1 ");
				sb.append("AND batch like '%"+request.getParameter("batch") + "%'");
				return sb.toString();
			}
		}, "Simple");
		tableTmpl.setRows(list);
		variables.put("list",list);
	}
	tableTmpl.setTitle("茶叶批次信息");
	tableTmpl.setSubTitle("茶叶批次信息");
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	variables.put("table", tableTmpl);
	
	TableTemplate tmpl = new TableTemplate();
	queryMap.clear();
	queryMap.put("objectCode", SALE_VIEW_CODE);
	soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);
	tmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	
	if(!StringUtil.isNullOrEmpty(request.getParameter("batch"))){
		DBQueryCodeSetProvider provider = new DBQueryCodeSetProvider();
		queryMap = RequestUtil.requestParameterToMap(request);
		List<?> list = sos.querySystemViewByCodeName(SALE_VIEW_CODE, queryMap, provider, new IWhereProvider(){
			@Override
			public String buildWhere() {
				StringBuffer sb = new StringBuffer(" 1=1 ");
				sb.append("AND batch like '%"+request.getParameter("batch") + "%'");
				return sb.toString();
			}
		}, "Simple");
		tmpl.setRows(list);
	}
	tmpl.setTitle("茶叶批次信息");
	tmpl.setSubTitle("茶叶批次信息");
	variables.put("saleTable", tmpl);
	
	PageRender render = new PageRender(getServletContext(), "/META-INF/Template/", "/Admin/content.html", variables);
	render.render(out);
%>