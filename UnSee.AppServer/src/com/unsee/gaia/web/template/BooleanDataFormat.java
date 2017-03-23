package com.unsee.gaia.web.template;

public class BooleanDataFormat implements IDataFormat {

	@Override
	public String format(Object value) {
		if (value == null)
			return "";
		
		if(value instanceof Integer) {
			return Integer.valueOf(value.toString()) != 0 ? "是" : "否";
		}
		
		return Boolean.valueOf(value.toString()) ? "是" : "否";
	}

}
