<%@page import="com.unsee.tea.biz.entities.TeaSaleEntity"%>
<%@page import="com.unsee.tea.biz.services.TeaSaleService"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.unsee.util.StringUtil"%>
<%@page import="com.unsee.tea.biz.services.TeaTraceService"%>
<%@page import="com.unsee.tea.biz.entities.TeaTraceEntity"%>
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
	
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	queryMap.put("objectCode", VIEW_CODE);
	SysObjectsEntity soe = (SysObjectsEntity)sos.getEntityByRequest(queryMap);
	HashMap<String,Object> variables = new HashMap<String,Object>();
	
	variables.put("soe",soe);
	variables.put("title", "茶叶信息管理");	
	variables.put("contentPage", "/TEA/tea-trace-input.html");
	variables.put("importDlgTitle","上传相关图片");
	variables.put("serviceTarget","upLoadPicture");
	variables.put("queryArgs", RequestUtil.requestParameterToMap(request));
	
	String id = request.getParameter("id");
	
	TeaSaleEntity saleEntity = null;
	TeaTraceEntity traceEntity = null;
	
	if(!StringUtil.isNullOrEmpty(id)){
		if("6".equalsIgnoreCase(request.getParameter("nodeId"))){
			saleEntity = (TeaSaleEntity)TeaSaleService.getInstance().getEntity(id);
		}else{
			traceEntity = (TeaTraceEntity)TeaTraceService.getInstance().getEntity(id);
		}
	}
	
	if(null == saleEntity){
		saleEntity= new TeaSaleEntity();
	}
	if(null == traceEntity){
		traceEntity = new TeaTraceEntity();
	}
	variables.put("saleEnt", saleEntity);
	variables.put("traceEnt", traceEntity);
	
	try
	{	
		DictionaryManager dictionaryManager = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(DictionaryManager.class);
		Dictionary dictNode = dictionaryManager.getDictionary("节点", true);
		variables.put("dictNode", dictNode);	}
	catch(Exception ex) {
		ex.printStackTrace();
	}
	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("节点信息管理");
	tableTmpl.setSubTitle("茶叶节点信息录入");
	
	tableTmpl.setColumns(TableColumn.convertSysObjectPropsToColumns(soe));
	variables.put("table", tableTmpl);
	
	PageRender render = new PageRender(getServletContext(), "/META-INF/Template/", "/Admin/content.html", variables);
	render.render(out);
%>