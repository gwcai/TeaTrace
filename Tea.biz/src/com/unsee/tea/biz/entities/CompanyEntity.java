package com.unsee.tea.biz.entities;

import com.unsee.gaia.biz.entities.GAIAEntity;
/*********************************************************************
* @author: UnSee Corp.
* @version: 1.0
* @date: 2017-02-25 21:39:26
* @table: tea_trace
*********************************************************************/
public class CompanyEntity extends GAIAEntity {
	/***
	/* 字段定义
	***/
	private String name;
	private String logo;
	private String qrcode;
	private String contact;
	
	/***
	* 字段访问方法定义
	***/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
}