package com.unsee.tea.biz.services;

import org.apache.ibatis.session.SqlSession;

import com.unsee.gaia.dal.brokers.AbstractBroker;
import com.unsee.gaia.dal.brokers.DefaultBroker;
import com.unsee.gaia.dal.entities.BaseEntity;
import com.unsee.tea.biz.entities.CompanyEntity;

// if need logger remove comment
// import org.apache.log4j.Logger;
/*********************************************************************
* @author: UnSee Corp.
* @version: 1.0
* @date: 2017-02-25 11:26:05
* @entity: TeaTraceService
*********************************************************************/
public class CompanyService extends TeaCommonService {
    // if need logger remove comment
	// private final static Logger logger = Logger.getLogger(SysGroupsService.class);
	
	private static class SingletonHolder { 
	     private static final CompanyService INSTANCE = new CompanyService();
	}
	
	private CompanyService() {
		super("CompanyBroker");
	}
	
	public static CompanyService getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public CompanyService(String brokerName) {
		super(brokerName);
	}

	@Override
	protected void beforeUpdateEntity(SqlSession session, AbstractBroker broker, BaseEntity ent) throws Exception {
		DefaultBroker companyBroker = (DefaultBroker) broker;
		//companyBroker.removeEntity(ent);
		super.beforeUpdateEntity(session, broker, ent);
	}
	
	public CompanyEntity getCompanyInfo() throws Exception{
		SqlSession session = null;
		try{
			session = getSessionProvider().getSqlSession();
			return (CompanyEntity) session.selectOne("SelectCompanyInfo");
		}catch(Exception ex){
			throw ex;
		}finally{
			getSessionProvider().closeSqlSession();
		}
	}
}