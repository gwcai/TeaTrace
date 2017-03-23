package com.unsee.tea.biz.services;

// if need logger remove comment
// import org.apache.log4j.Logger;
/*********************************************************************
* @author: UnSee Corp.
* @version: 1.0
* @date: 2017-02-25 11:26:05
* @entity: TeaTraceService
*********************************************************************/
public class TeaSaleService extends TeaCommonService {
    // if need logger remove comment
	// private final static Logger logger = Logger.getLogger(SysGroupsService.class);
	
	private static class SingletonHolder { 
	     private static final TeaSaleService INSTANCE = new TeaSaleService();
	}
	
	private TeaSaleService() {
		super("TeaSaleBroker");
	}
	
	public static TeaSaleService getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public TeaSaleService(String brokerName) {
		super(brokerName);
	}
}