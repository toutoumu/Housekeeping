package com.housekeeping.web.common;

public class JsonResponseHeader {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * 设置响应：是否成功，成功为True ，否则为False
	 * @param isSuccess
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	private boolean isSuccess;

}
