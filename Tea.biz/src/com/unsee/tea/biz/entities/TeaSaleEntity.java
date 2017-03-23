package com.unsee.tea.biz.entities;

import com.unsee.gaia.biz.entities.GAIAEntity;
/*********************************************************************
* @author: UnSee Corp.
* @version: 1.0
* @date: 2017-02-25 21:39:26
* @table: tea_trace
*********************************************************************/
public class TeaSaleEntity extends GAIAEntity {
	/***
	/* 字段定义
	***/
	private String startTime;
	private String endTime;
	private String percentage;
	private String pictureLink;
	private String totalOutput;
	private String creator;
	private String batch;
	private String nodeId;
	
	/***
	* 字段访问方法定义
	***/
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
	public String getTotalOutput() {
		return totalOutput;
	}
	public void setTotalOutput(String totalOutput) {
		this.totalOutput = totalOutput;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getPictureLink() {
		return pictureLink;
	}
	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}
}