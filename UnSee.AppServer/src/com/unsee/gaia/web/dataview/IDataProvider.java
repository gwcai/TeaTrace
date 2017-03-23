package com.unsee.gaia.web.dataview;

import javax.servlet.http.HttpServletRequest;

public interface IDataProvider{
	Object queryData(String objectCode, HttpServletRequest request) throws Exception;
}