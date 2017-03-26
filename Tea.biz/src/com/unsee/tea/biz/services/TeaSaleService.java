package com.unsee.tea.biz.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.unsee.gaia.biz.entities.GAIAEntity;
import com.unsee.gaia.dal.brokers.AbstractBroker;
import com.unsee.gaia.dal.brokers.DefaultBroker;
import com.unsee.gaia.dal.entities.BaseEntity;
import com.unsee.tea.biz.entities.TeaSaleEntity;

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

	@Override
	protected void beforeUpdateEntity(SqlSession session, AbstractBroker broker, BaseEntity ent) throws Exception {
		DefaultBroker b = (DefaultBroker) broker;
		//删除
		TeaSaleEntity entity = (TeaSaleEntity) ent;
		if(GAIAEntity.NEW_DIRTY == entity.getDirty()){
			Map map = new HashMap();
			map.put("batch", ((TeaSaleEntity) ent).getBatch());
			b.removeEntity(map);
		}		
		super.beforeUpdateEntity(session, broker, ent);
	}

	public List<TeaSaleEntity> listSalesByBatch(String batch) throws Exception {
		SqlSession session = null;
		try{
			session = getSessionProvider().getSqlSession();
			return session.selectList("ListTeaSales",batch);
		}catch(Exception ex){
			throw ex;
		}finally{
			getSessionProvider().closeSqlSession();
		}
	}
}