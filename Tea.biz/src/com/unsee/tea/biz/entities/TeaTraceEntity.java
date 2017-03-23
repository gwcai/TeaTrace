package com.unsee.tea.biz.entities;

import com.unsee.gaia.biz.entities.GAIAEntity;
/*********************************************************************
* @author: UnSee Corp.
* @version: 1.0
* @date: 2017-02-25 21:39:26
* @table: tea_trace
*********************************************************************/
public class TeaTraceEntity extends GAIAEntity {
	/***
	/* 字段定义
	***/
	private String nodeId;
	private String time;
	private String weather;
	private String temperature;
	private String dampness;
	private String location;
	private String longitude;//经度
	private String latitude;//纬度
	private String pictureLink;
	private String creator;
	private String batch;
	
	/***
	* 字段访问方法定义
	***/
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getDampness() {
		return dampness;
	}
	public void setDampness(String dampness) {
		this.dampness = dampness;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPictureLink() {
		return pictureLink;
	}
	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}