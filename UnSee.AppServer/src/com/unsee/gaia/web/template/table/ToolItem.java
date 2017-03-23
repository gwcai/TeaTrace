package com.unsee.gaia.web.template.table;

public class ToolItem {
	private String id;
	private String title;
	private String icon;
	private String action;

	public String getId() {
		return id;
	}

	public ToolItem setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public ToolItem setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public ToolItem setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public String getAction() {
		return action;
	}

	public ToolItem setAction(String action) {
		this.action = action;
		return this;
	}
}
