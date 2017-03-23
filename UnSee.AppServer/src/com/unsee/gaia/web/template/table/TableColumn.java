package com.unsee.gaia.web.template.table;

import java.util.ArrayList;
import java.util.List;

import com.unsee.gaia.biz.entities.SysObjectPropsEntity;
import com.unsee.gaia.biz.entities.SysObjectsEntity;
import com.unsee.gaia.web.template.DefaultDataFormat;
import com.unsee.gaia.web.template.IDataFormat;

public class TableColumn {
	private String name;
	private int sort;
	private boolean sortable;
	private String className;
	private String fieldName;
	private boolean visible;
	private IDataFormat dataFormat = new DefaultDataFormat();
	private boolean pk;

	public IDataFormat getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(IDataFormat dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDisplayValue(Object value) {
		return dataFormat.format(value);
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}
	
	public static List<TableColumn> convertSysObjectPropsToColumns(SysObjectsEntity soe) {
		List<TableColumn> columns = new ArrayList<TableColumn>();
		if(null != soe) {
			for(SysObjectPropsEntity sope : soe.getProperties()) {
	
				TableColumn column = new TableColumn();
				column.setName(sope.getCaption());
				column.setClassName(sope.getPropName());
				column.setFieldName(sope.isLookup()?sope.getLookupFieldName().toLowerCase():sope.getPropName().toLowerCase());
				column.setPk(sope.isPk());
				column.setVisible(sope.isVisible());
				
				columns.add(column);
			}
		}
		
		return columns;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
