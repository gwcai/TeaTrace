package com.unsee.tea.biz.services;

import org.apache.log4j.Logger;

import com.unsee.gaia.biz.services.GAIACommonService;
import com.unsee.gaia.dal.intl.ISqlSessionProvider;
import com.unsee.gaia.dal.services.SqlSessionPool;
import com.unsee.tea.biz.constants.TeaConstants;

public class TeaCommonService extends GAIACommonService{
	private static Logger logger = Logger.getLogger(TeaCommonService.class);
	
	public TeaCommonService(String brokerName) {
		super(brokerName);
	}

	@Override
	public ISqlSessionProvider getSessionProvider(){
		return SqlSessionPool.getInstance().queryProvider(TeaConstants.PROVIDER_BIZ);
	}
}
