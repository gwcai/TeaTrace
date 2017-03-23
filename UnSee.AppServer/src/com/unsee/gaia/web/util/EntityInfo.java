package com.unsee.gaia.web.util;

import com.unsee.gaia.dal.entities.BaseEntity;
import com.unsee.gaia.dal.services.AbstractCommonService;

public class EntityInfo {
	private String brokerName;
	private Class<?> classInfo;
	private Class<?> serviceInfo;

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public Class<?> getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(Class<?> classInfo) {
		this.classInfo = classInfo;
	}

	public EntityInfo(String brokerName, Class<?> classInfo,
			Class<?> serviceInfo) {
		this.brokerName = brokerName;
		this.classInfo = classInfo;
		this.serviceInfo = serviceInfo;
	}

	public BaseEntity createEntity() throws InstantiationException,
			IllegalAccessException {
		return (BaseEntity) this.classInfo.newInstance();
	}

	public AbstractCommonService createService() throws Exception {
		return (AbstractCommonService) this.serviceInfo.getConstructor(
				String.class).newInstance(this.brokerName);
	}
}
