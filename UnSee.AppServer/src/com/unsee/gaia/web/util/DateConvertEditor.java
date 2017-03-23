package com.unsee.gaia.web.util;

/*
 * spring mvc 日期转换用
 */
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.unsee.util.StringUtil;

public class DateConvertEditor extends PropertyEditorSupport {
	private DateFormat format;

	public DateConvertEditor() {
		//this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.format = new SimpleDateFormat("yyyy-MM-dd");
	}

	public DateConvertEditor(String format) {
		this.format = new SimpleDateFormat(format);
	}

	/** Date -> String */
	@Override
	public String getAsText() {
		if (getValue() == null)
			return "";
		return this.format.format(getValue());
	}

	/** String -> Date */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtil.isNullOrEmpty(text)) {
			setValue(null);
		} else {
			try {
				setValue(this.format.parse(text));
			} catch (ParseException e) {
				throw new IllegalArgumentException("不能被转换的日期字符串，请检查!", e);
			}
		}
	}
}
