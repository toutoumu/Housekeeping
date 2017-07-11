package com.housekeeping.web.common;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.housekeeping.utils.JsonUtils;
import com.housekeeping.utils.StringUtil;

/**
 * @author 斌
 * <pre>
 {
	header : {
		className : "className",
		method : "method"
	},
	parameter : {
		parameter1 : "para",
		parameter2 : "para"
	},
	data : {
		data1 : {},
		data2 : {},
		data3 : {}
	}
}
</pre>
 */
public class JsonRequest {

	private String data;

	private Map<String, String> params;

	private JsonRequestHeader header;

	private HttpServletRequest request;

	public JsonRequestHeader getHeader() {
		return header;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 构造函数
	 * @param request
	 * @throws IOException
	 */
	public JsonRequest(HttpServletRequest request) throws IOException {
		this.request = request;
		String requestBody = JsonUtils.readAsString(this.request);
		if (StringUtil.strIsEmpty(requestBody)) {
			throw new RuntimeException("请求参数不能为空");
		}

		// 读取请求头
		JsonElement element = JsonUtils.getObject(requestBody, "header");
		if (element == null) {
			throw new RuntimeException("JSON请求头不能为空");
		}
		this.header = JsonUtils.fromJson(element.toString(), JsonRequestHeader.class);

		// 读取参数
		element = JsonUtils.getObject(requestBody, "parameter");
		if (element != null) {
			new TypeToken<Map<String, String>>() {
			}.getType();
			TypeToken<Map<String, String>> typeToken = new TypeToken<Map<String, String>>() {
			};
			params = JsonUtils.fromJson(element.toString(), typeToken);
		}
		// 读取消息体
		JsonElement dataStr = JsonUtils.getObject(requestBody, "data");
		if (dataStr != null) {
			this.data = dataStr.toString();
		}
		// = JsonUtils.getObject(requestBody, "data").toString();
	}

	/**
	 * 获取参数
	 * @param key 
	 * @return
	 */
	public String getParameter(String key) {
		if (params == null) {
			return null;
		}
		if (params.containsKey(key)) {
			return params.get(key);
		}
		return null;
	}

	/**
	 * 获取实体对象类型参数
	 * @param objectName 参数名称
	 * @param clazz 类名称
	 * @return
	 */
	public <T> T getEntity(String objectName, Class<T> clazz) {
		if (data == null || data.equals(JsonUtils.EMPTY_JSON)) {
			return null;
		}
		JsonElement element = JsonUtils.getObject(this.data, objectName);
		String str = element.toString();
		if (str == null || str.equals(JsonUtils.EMPTY_JSON)) {
			return null;
		}
		return JsonUtils.fromJson(str, clazz);
	}
}
