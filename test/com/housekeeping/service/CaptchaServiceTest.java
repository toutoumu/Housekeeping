package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Captcha;

public class CaptchaServiceTest extends junit.framework.TestCase {

	public final String CATEGORY_URL = "http://localhost:8080/Housekeeping/ws/CaptchaService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddCategory() {
		System.out.println("testAddCategory called with format " + format);
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Captcha/{phoneNumber}", "15989348952").accept(format).type(format);
		Captcha captcha = client.get(Captcha.class);
		if (captcha != null) {
			System.out.println(captcha.getMessage());
			System.out.println(captcha.getCode());
		}
	}

	public void testValidate() {
		Captcha captcha = new Captcha();
		captcha.setPhoneNumber("15989348952");
		captcha.setCode(139975);
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Captcha").accept(format).type(format);
		Response b = client.put(captcha, Response.class);
		System.out.println(b.getStatus());
	}
}
