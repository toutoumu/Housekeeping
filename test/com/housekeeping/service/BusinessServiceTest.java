package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Business;
import com.housekeeping.entity.wrap.Businesss;

public class BusinessServiceTest extends junit.framework.TestCase {

	public final String CATEGORY_URL = "http://localhost:8080/Housekeeping/ws/BusinessService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddBusiness() {
		System.out.println("testAddBusiness called with format " + format);
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Business").accept(format).type(format);
		Business business = new Business();
		business.setBusinessCategoryId(1);
		business.setName("验证码");
		business = client.post(business, Business.class);
		if (business != null) {
			System.out.println(business.getId());
		}
	}

	public void testGetBusiness() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Business/{id}", 1).accept(format).type(format);
		Business business = client.get(Business.class);
		if (business != null) {
			System.out.println(business.getName());
		}
	}

	public void testGetBusinessByCategory() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Business/Businesss/{id}", 1).accept(format).type(format);
		Businesss businesss = client.get(Businesss.class);
		if (businesss != null) {
			for (Business business : businesss.getBusinesses()) {
				System.out.println(business.getName());
			}
		}
	}

	public void testUpdateBusiness() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Business").accept(format).type(format);
		Business business = new Business();
		business.setBusinessCategoryId(1);
		business.setName("验证码a ");
		business.setId(1);
		Response response = client.put(business);
		System.out.println(response.getStatus());
	}

	public void testDeleteBusiness() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Business/{id}", 1).accept(format).type(format);
		Response business = client.delete();
		if (business != null) {
			System.out.println(business.getStatus());
		}
	}

}
