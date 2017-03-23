<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.unsee.util.StringUtil"%>
<%@page import="com.unsee.tea.biz.services.CompanyService"%>
<%@page import="com.unsee.tea.biz.entities.CompanyEntity"%>
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
	SysObjectsService sos = new SysObjectsService("SysObjectsBroker");
	Map queryMap = new HashMap();
	HashMap<String,Object> variables = new HashMap<String,Object>();
	
	variables.put("title", "公司信息录入");	
	variables.put("contentPage", "/TEA/input-company-info.html");
	variables.put("importDlgTitle","上传相关图片");
	variables.put("serviceTarget","upLoadPicture");
	
	TableTemplate tableTmpl = new TableTemplate();
	tableTmpl.setTitle("公司信息录入");
	tableTmpl.setSubTitle("公司信息录入");
	variables.put("table", tableTmpl);
	
	CompanyEntity entity = (CompanyEntity)CompanyService.getInstance().getCompanyInfo();
	if(null == entity) entity = new CompanyEntity();
	variables.put("entity", entity);
	
	PageRender render = new PageRender(getServletContext(), "/META-INF/Template/", "/Admin/content.html", variables);
	render.render(out);
%>