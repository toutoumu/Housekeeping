package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.BusinessCategory;
import com.housekeeping.entity.wrap.BusinessCategorys;

public class BusinessCategoryServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/BusinessCategoryService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddBusinessCategory() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddBusinessCategory called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/BusinessCategory").accept(format).type(format);
		BusinessCategory category = new BusinessCategory();
		category.setName("asdfasdf");
		category.setId(2);
		BusinessCategory responseBusinessCategory = client.post(category, BusinessCategory.class);
		System.out.println(responseBusinessCategory.getId());
	}

	public void testUpdateBusinessCategory() {
		System.out.println("test updateBusinessCategory called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessCategory").accept(format).type(format);
		BusinessCategory article = new BusinessCategory();
		article.setId(1);
		article.setName("真正的内容");
		Response responseBusinessCategory = client.put(article, Response.class);
		System.out.println(responseBusinessCategory.getStatus());
	}

	public void testGetBusinessCategory() {
		System.out.println("test getBusinessCategory called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessCategory/{id}", 1).accept(format).type(format);
		BusinessCategory responseBusinessCategory = client.get(BusinessCategory.class);
		if (responseBusinessCategory != null) {
			System.out.println(responseBusinessCategory.getName());
		}
	}

	public void testGetBusinessCategorys() {
		System.out.println("test getBusinessCategorys called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessCategory/BusinessCategorys").accept(format).type(format);

		BusinessCategorys articles = client.get(BusinessCategorys.class);
		System.out.println(articles.getBusinessCategories() == null);
		if (articles.getBusinessCategories() != null) {
			for (BusinessCategory article : articles.getBusinessCategories()) {
				System.out.println(article.getName());
			}
		}
	}

	public void testDeleteBusinessCategory() {

		System.out.println("test deleteBusinessCategory called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessCategory/{id}", 1).accept(format).type(format);

		Response response = client.delete();
		System.out.println(response.getStatus());

	}

}
