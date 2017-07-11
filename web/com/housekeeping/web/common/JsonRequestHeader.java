package com.housekeeping.web.common;

/**
 * 请求头
 * @author 斌
 *
 */
public class JsonRequestHeader {
	private String className;

	private String method;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
