package com.unsee.tea.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.unsee.gaia.biz.entities.GAIAEntity;
import com.unsee.gaia.biz.entities.TempAttachmentsEntity;
import com.unsee.gaia.biz.services.TempAttachmentsService;
import com.unsee.gaia.web.servlet.RESTServlet;
import com.unsee.gaia.web.servlet.Result;
import com.unsee.gaia.web.util.RequestUtil;
import com.unsee.tea.biz.entities.TeaSaleEntity;
import com.unsee.tea.biz.entities.TeaTraceEntity;
import com.unsee.tea.biz.services.TeaSaleService;
import com.unsee.tea.biz.services.TeaTraceService;
import com.unsee.util.StringUtil;
import com.unsee.util.qrcode.QRCodeUtil;

public class TeaTraceServlet extends RESTServlet{
	
	@Override
	protected Result doRESTList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return super.doRESTList(request, response);
	}

	@Override
	protected Result doRESTGet(HttpServletRequest request, HttpServletResponse response) {
		if("getPicture".equalsIgnoreCase(request.getParameter("target"))){
			return getPicture(request,response);
		}else if("getQRCode".equalsIgnoreCase(request.getParameter("target"))){
			return getQRCode(request,response);
		}
		return super.doRESTGet(request, response);
	}

	/**
	 * 生成批次二维码
	 * @param request
	 * @return
	 */
	private Result getQRCode(HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		try{
			String batch = request.getParameter("batch");
			if(StringUtil.isNullOrEmpty(batch)) throw new Exception("没有批次信息");
			
			String qrFileFolder = "D:\\qrcode\\";
			File file =new File(qrFileFolder);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    System.out.println("//不存在");  
			    file .mkdir();    
			} else   
			{  
			    System.out.println("//目录存在");  
			}
			
			String content = "http://192.168.0.103/UnSee.AppServer/TEA/tea-batch-trace.jsp?batch="+batch;
			File qrFile = new File(qrFileFolder+batch);
			
			QRCodeUtil.generateQRCodeFile(qrFileFolder+batch, content, 100);
//			BufferedImage image = QRCodeHelper.enQRCode(content, 100);
//			ImageIO.write(image, "png", qrFile);
//			
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			ImageIO.write(image, "gif", os);
//			
//			response.setContentType("image/png");
//			response.getOutputStream().write(os.toByteArray());
		}catch(Exception ex) {
			logger.error("getQRCode", ex);
			result.setSuccess(false);
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	private Result getPicture(HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		try{
				TempAttachmentsEntity ent = (TempAttachmentsEntity) TempAttachmentsService.getInstance().getEntity(request.getParameter("id"));
				if(null != ent){
					response.setContentType("image/"+ent.getFileType());
					ByteArrayInputStream bis = new ByteArrayInputStream(ent.getContent());
					byte[] image = new byte[bis.available()];
					bis.read(image);
					
					response.getOutputStream().write(image);
				}else{
					throw new Exception("未找到图片");
				}
			}
			catch(Exception ex) {
				logger.error("doGet", ex);
				result.setSuccess(false);
				result.setMessage(ex.getMessage());
			}
		return result;
	}

	@Override
	protected Result doRESTPut(HttpServletRequest request, HttpServletResponse response) {
		if("updateTeaTraceInfo".equalsIgnoreCase(request.getParameter("target"))){
			return updateTeaTraceInfo(request);
		}else if("updateTeaSaleInfo".equalsIgnoreCase(request.getParameter("target"))){
			return updateTeaSaleInfo(request);
		}
		return super.doRESTPut(request, response);
	}

	private Result updateTeaSaleInfo(HttpServletRequest request) {
		Result result = new Result();
		try{
			logger.debug("entity"+request.getParameter("entity"));
			TeaSaleEntity ent = (TeaSaleEntity) fromJSON(request.getParameter("entity"), TeaSaleEntity.class);
			if(null == ent) throw new Exception("参数错误");
			
			if(GAIAEntity.NEW_DIRTY == ent.getDirty() && StringUtil.isNullOrEmpty(request.getParameter("IgnoreRepeat"))){
				//判断重复
				List tmp = TeaSaleService.getInstance().listSalesByBatch(ent.getBatch());
				if(null != tmp && tmp.size() > 0){
					result.addParams("exists", "true");
					throw new Exception("已存在");
				}
			}
			
			if(ent.getDirty() == GAIAEntity.NEW_DIRTY || ent.getDirty() == GAIAEntity.MODIFY_DIRTY){
				TeaSaleService.getInstance().updateEntity(ent);
			}else if(ent.getDirty() == GAIAEntity.REMOVE_DIRTY){
				TeaSaleService.getInstance().removeEntity(ent);
			}
			result.addParams("entity", toJSON(ent));
			result.setSuccess(true);
		}catch(Exception ex){
			result.setMessage(ex.getMessage());
			result.setSuccess(false);
			logger.error(ex);
		}
		return result;
	}

	private Result updateTeaTraceInfo(HttpServletRequest request) {
		Result result = new Result();
		try{
			logger.debug("entity"+request.getParameter("entity"));
			TeaTraceEntity ent = (TeaTraceEntity) fromJSON(request.getParameter("entity"), TeaTraceEntity.class);
			
			if(null == ent) throw new Exception("参数错误");
			
			if(ent.getDirty() == GAIAEntity.NEW_DIRTY || ent.getDirty() == GAIAEntity.MODIFY_DIRTY){
				TeaTraceService.getInstance().updateEntity(ent);
			}else if(ent.getDirty() == GAIAEntity.REMOVE_DIRTY){
				TeaTraceService.getInstance().removeEntity(ent);
			}
			result.addParams("entity", toJSON(ent));
			result.setSuccess(true);
		}catch(Exception ex){
			result.setMessage(ex.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	protected Result doRESTRemove(HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		try{
			String id = request.getParameter("id");	
			String nodeId = request.getParameter("nodeId");
			
			if(StringUtil.isNullOrEmpty(id)) throw new Exception("参数错误");
			
			if("6".equalsIgnoreCase(nodeId)){
				TeaSaleService.getInstance().removeEntity(RequestUtil.requestParameterToMap(request));
			}else{
				TeaTraceService.getInstance().removeEntity(RequestUtil.requestParameterToMap(request));
			}
			result.setSuccess(true);
		}catch(Exception ex){
			result.setMessage(ex.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

}