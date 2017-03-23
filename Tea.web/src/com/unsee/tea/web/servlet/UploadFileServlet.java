package com.unsee.tea.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.jni.File;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unsee.gaia.biz.entities.TempAttachmentsEntity;
import com.unsee.gaia.biz.entities.UploadItemInfo;
import com.unsee.gaia.biz.services.TempAttachmentsService;
import com.unsee.gaia.web.servlet.Result;
import com.unsee.gaia.web.util.JSONUtil;
import com.unsee.gaia.web.util.RequestUtil;
import com.unsee.gaia.web.util.Setting;
import com.unsee.util.StringUtil;

public class UploadFileServlet extends HttpServlet {

	private static final String PLACE_DB = "database";
	
	private static Logger logger = Logger.getLogger(UploadFileServlet.class);

	/***
	 * 上传文件至临时目录，成功返回UploadItemInfo信息 支持多个文件上传
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Result result = new Result();
		Gson gson = new Gson();
		
		if("remove".equalsIgnoreCase(request.getParameter("target"))){
			try{
				if(!StringUtil.isNullOrEmpty(request.getParameter("id"))){
					TempAttachmentsService.getInstance().removeEntity(RequestUtil.requestParameterToMap(request));
				}
				result.setSuccess(true);
			}catch(Exception ex){
				result.setSuccess(false);
				result.setMessage("IO异常");
				logger.error(ex);
			}
		}else{
			result = UploadFile(request,response);
		}
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Context-type", "application/json");
		response.getOutputStream().write(gson.toJson(result).getBytes("UTF-8"));
	}
	
	private Result UploadFile(HttpServletRequest request,
			HttpServletResponse response){
		Result result = new Result();
		Map<String, String> postData = new HashMap<String, String>();
		Gson gson = new Gson();

		try {
			if (!ServletFileUpload.isMultipartContent(request)) {
	
				result.setSuccess(false);
				result.setMessage("不受支持的文件上传方式");
				response.setCharacterEncoding("UTF-8");
				response.addHeader("Context-type", "application/json");
				response.getOutputStream().write(
						gson.toJson(result).getBytes("UTF-8"));
	
				return result;
			}
	
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
	
			upload.setHeaderEncoding("utf-8");
		
			List<UploadItemInfo> uploadList = new ArrayList<UploadItemInfo>();
			Setting setting = (Setting) request.getServletContext()
					.getAttribute("app.setting");

			for (FileItem item : upload.parseRequest(request)) {
				if (!item.isFormField()) {
					InputStream stream = null;

					try {
						stream = item.getInputStream();
						if(PLACE_DB.equalsIgnoreCase(setting.getUploadFileStorePlace())) {
							logger.debug("上传的文件将被放置到数据库中");
							uploadList.add(storeToDatabase(setting, item, stream, RequestUtil.getClientIpAddr(request)));
						} else {
							logger.debug("上传的文件将被放置到：" + setting.getTempUploadPath());
							uploadList.add(storeToTempFolder(setting, item, stream));
						}
					} catch (Exception e) {
						throw new FileUploadException(e.getMessage());
					} finally {
						if (stream != null)
							stream.close();
					}
				} else {
					Scanner scanner = new Scanner(item.getInputStream(),
							"UTF-8");
					StringBuffer sb = new StringBuffer();
					while (scanner.hasNext()) {
						sb.append(scanner.next());
					}
					postData.put(item.getFieldName(), sb.toString());
				}
			}

			if (postData.containsKey("target")
					&& "jump".equals(postData.get("target"))) {
				request.getRequestDispatcher(postData.get("behavior")).forward(
						request, response);
				return result;
			}
			
			result.setSuccess(true);
			result.addParams("items", gson.toJson(uploadList));
		} catch (FileUploadException e) {
			logger.error(e);
			result.setSuccess(false);
			result.setMessage("文件上传异常");
		} catch (IOException e) {
			logger.error(e);
			result.setSuccess(false);
			result.setMessage("IO异常");
		} catch (ServletException e) {
			logger.error(e);
			result.setSuccess(false);
			result.setMessage("servlet异常");
		}
		
		return result;
	}

	private UploadItemInfo storeToDatabase(Setting setting, FileItem item,
			InputStream stream, String clientIP) throws Exception {
		TempAttachmentsEntity attachmentsEnt = TempAttachmentsEntity.buildFromStream(stream);
		attachmentsEnt.setName(URLDecoder.decode(item.getName(), "utf-8"));
		attachmentsEnt.setFileType(FilenameUtils.getExtension(item.getName()));
		attachmentsEnt.setClientIp(clientIP);
		TempAttachmentsService.getInstance().updateEntity(attachmentsEnt);
		
		UploadItemInfo uii = new UploadItemInfo();
		uii.setName(attachmentsEnt.getName());
		uii.setUuid(attachmentsEnt.getId());
		uii.setSize(attachmentsEnt.getSize());
		uii.setFileType(attachmentsEnt.getFileType());
		
		return uii;
	}

	private UploadItemInfo storeToTempFolder(Setting setting, FileItem item,
			InputStream stream) throws Exception {
		String newName = UUID.randomUUID().toString() +'.' + FilenameUtils.getExtension(item.getName());
		
		OutputStream os = new FileOutputStream(String.format("%s/%s",
				setting.getTempUploadPath(), newName));

		UploadItemInfo uii = new UploadItemInfo();
		uii.setName(URLDecoder.decode(item.getName(), "utf-8"));
		uii.setUuid(newName);
		uii.setSize(IOUtils.copy(stream, os));
		uii.setMd5hash(org.apache.commons.codec.digest.DigestUtils
				.md5Hex(stream));
		uii.setFileType(FilenameUtils.getExtension(item.getName()));

		os.close();

		return uii;
	}
	
	private boolean removeFromTempFolder(Setting setting, UploadItemInfo uii) {
		String fileName = String.format("%s/%s", setting.getTempUploadPath(), uii.getUuid());
		java.io.File file = new java.io.File(fileName);
		return file.delete();
	}
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Result result = new Result();
		try{
			String id = req.getParameter("id");
			logger.debug(id);
			TempAttachmentsEntity ent = (TempAttachmentsEntity) TempAttachmentsService.getInstance().getEntity(req.getParameter("id"));
			if(null != ent){
				resp.setContentType("image/"+ent.getFileType());
				ByteArrayInputStream bis = new ByteArrayInputStream(ent.getContent());
				byte[] image = new byte[bis.available()];
				bis.read(image);
				
				resp.getOutputStream().write(image);
			}else{
				throw new Exception("未找到图片");
			}
		}
		catch(Exception ex) {
			logger.error("doGet", ex);
			result.setSuccess(false);
			result.setMessage(ex.getMessage());
			
			resp.setCharacterEncoding("UTF-8");
			resp.addHeader("Context-type", "application/json");
			resp.getOutputStream().write(JSONUtil.toJSON(result).getBytes("UTF-8"));
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Result result = new Result();
		try
		{
			Type type = new TypeToken<UploadItemInfo[]>() {}.getType();
			UploadItemInfo[] items = (UploadItemInfo[]) JSONUtil.fromJSON(req.getParameter("items"), type);
		}
		catch(Exception ex) {
			logger.error("", ex);
			result.setSuccess(false);
			result.setMessage(ex.getMessage());
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader("Context-type", "application/json");
		resp.getOutputStream().write(JSONUtil.toJSON(result).getBytes("UTF-8"));
	}	
}
