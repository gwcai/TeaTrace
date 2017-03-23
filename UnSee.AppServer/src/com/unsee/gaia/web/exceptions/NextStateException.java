package com.unsee.gaia.web.exceptions;

public class NextStateException extends Exception {
	public NextStateException() {
		super("状态异常！");
	}
	
	public NextStateException(String message) {
		super(message);
	}
}
