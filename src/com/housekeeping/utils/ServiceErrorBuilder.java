package com.housekeeping.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

public class ServiceErrorBuilder {
	/**
	 * 构造错误信息
	 * @param message
	 * @return
	 */
	public static WebApplicationException badRequestError(String message) {
		ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
		builder.type(MediaType.APPLICATION_JSON);
		builder.entity(message);
		builder.tag(message);
 		return new WebApplicationException(builder.build());
	}

	/**
	 * 构造服务器错误信息
	 * @param message
	 * @return
	 */
	public static WebApplicationException serverError(String message) {
		ResponseBuilder builder = Response.status(Status.INTERNAL_SERVER_ERROR);
		builder.type(MediaType.APPLICATION_JSON);
		builder.entity(message);
		return new WebApplicationException(builder.build());
	}

	/**
	 * 构造服务器错误信息
	 * @param message
	 * @return
	 */
	public static WebApplicationException notmodifyError(String message) {
		ResponseBuilder builder = Response.status(Status.NOT_MODIFIED);
		builder.type(MediaType.APPLICATION_JSON);
		builder.entity(message);
		return new WebApplicationException(builder.build());
	}

}
