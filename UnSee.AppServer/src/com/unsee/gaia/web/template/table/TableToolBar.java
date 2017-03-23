package com.unsee.gaia.web.template.table;

import java.util.List;
import java.util.ArrayList;

public class TableToolBar {
	private List<ToolItem> items = new ArrayList<ToolItem>();
	
	public void addToolItem(ToolItem item) {
		items.add(item);
	}
	
	public void clear() {
		items.clear();
	}
	
	public int getItemCount() {
		return items.size();
	}
	
	public ToolItem[] getItems() {
		return items.toArray(new ToolItem[]{});
	}
}
