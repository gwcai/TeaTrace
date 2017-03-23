package com.unsee.gaia.web.template.table;

import java.util.List;

import com.unsee.gaia.web.util.Pagination;

public class TableTemplate {
	private String title;
	private String subTitle;
	private List<TableColumn> columns;
	private List<?> rows;
	private EditorDialog dialog;
	private Pagination pagination;
	private boolean multiSelection;
	private String rowTemplate;
	private TableToolBar toolBar = new TableToolBar();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public List<TableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<TableColumn> columns) {
		this.columns = columns;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public EditorDialog getDialog() {
		return dialog;
	}

	public void setDialog(EditorDialog dialog) {
		this.dialog = dialog;
	}

	public boolean hasPK() {
		boolean result = false;

		if (columns == null)
			return result;

		for (TableColumn column : columns) {
			if (column.isPk()) {
				result = true;
				break;
			}
		}

		return result;
	}

	public String getPKField() {
		for (TableColumn column : columns) {
			if (column.isPk())
				return column.getFieldName();
		}

		return null;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public TableToolBar getToolBar() {
		return toolBar;
	}

	public boolean isMultiSelection() {
		return multiSelection;
	}

	public void setMultiSelection(boolean multiSelection) {
		this.multiSelection = multiSelection;
	}

	public String getRowTemplate() {
		return rowTemplate;
	}

	public void setRowTemplate(String rowTemplate) {
		this.rowTemplate = rowTemplate;
	}
}
