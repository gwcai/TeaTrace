package com.unsee.gaia.web.template;

public class DefaultDataFormat implements IDataFormat {

	@Override
	public String format(Object value) {
		return value != null ? value.toString() : "";
	}

}
