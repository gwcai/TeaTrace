package com.unsee.gaia.web.template.table;

public class EditorDialog {
	private String id;
	private String title;
	private String templatePath;
	private String getActionUrl;
	private String removeActionUrl;
	private String updateActionUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	public String getGetActionUrl() {
		return getActionUrl;
	}

	public void setGetActionUrl(String getActionUrl) {
		this.getActionUrl = getActionUrl;
	}

	public String getRemoveActionUrl() {
		return removeActionUrl;
	}

	public void setRemoveActionUrl(String removeActionUrl) {
		this.removeActionUrl = removeActionUrl;
	}

	public String getUpdateActionUrl() {
		return updateActionUrl;
	}

	public void setUpdateActionUrl(String updateActionUrl) {
		this.updateActionUrl = updateActionUrl;
	}
}