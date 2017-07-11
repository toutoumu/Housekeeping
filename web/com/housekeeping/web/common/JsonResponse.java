package com.housekeeping.web.common;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse {

	/**
	 * 响应头
	 */
	private JsonResponseHeader header = new JsonResponseHeader();

	/**
	 * 响应参数
	 */
	private Map<String, String> parameter = new HashMap<String, String>();

	/**
	 * 响应实体
	 */
	private Map<String, Object> data = new HashMap<String, Object>();

	/**
	 * 添加返回参数
	 * @param key
	 * @param value
	 */
	public void setData(String key, Object value) {
		this.data.put(key, value);
	}

	/**
	 * 添加响应参数
	 * @param key
	 * @param value
	 */
	public void setParameter(String key, String value) {
		this.parameter.put(key, value);
	}

	/**
	 * 是指服务调用是否成功
	 * @param b
	 */
	public void setSuccess(boolean b) {
		this.header.setSuccess(b);
	}

	/**
	 * 设置响应消息
	 * @param message
	 */
	public void setMessage(String message) {
		this.header.setMessage(message);
	}

}
