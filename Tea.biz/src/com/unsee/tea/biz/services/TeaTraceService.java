package com.unsee.tea.biz.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.unsee.gaia.dal.brokers.AbstractBroker;

// if need logger remove comment
// import org.apache.log4j.Logger;
/*********************************************************************
* @author: UnSee Corp.
* @version: 1.0
* @date: 2017-02-25 11:26:05
* @entity: TeaTraceService
*********************************************************************/
public class TeaTraceService extends TeaCommonService {
    // if need logger remove comment
	// private final static Logger logger = Logger.getLogger(SysGroupsService.class);
	
	private static class SingletonHolder { 
	     private static final TeaTraceService INSTANCE = new TeaTraceService();
	}
	
	private TeaTraceService() {
		super("TeaTraceBroker");
	}
	
	public static TeaTraceService getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public TeaTraceService(String brokerName) {
		super(brokerName);
	}
}