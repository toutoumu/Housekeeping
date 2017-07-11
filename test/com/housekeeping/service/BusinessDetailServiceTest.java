package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.BusinessDetail;
import com.housekeeping.entity.wrap.BusinessDetails;

public class BusinessDetailServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/BusinessDetailService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddBusinessDetail() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddBusinessDetail called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/BusinessDetail").accept(format).type(format);
		BusinessDetail detail = new BusinessDetail();
		detail.setName("asdfasdf");
		BusinessDetail responseBusinessDetail = client.post(detail, BusinessDetail.class);
		System.out.println(responseBusinessDetail.getId());
	}

	public void testUpdateBusinessDetail() {
		System.out.println("test updateBusinessDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessDetail").accept(format).type(format);
		BusinessDetail article = new BusinessDetail();
		article.setId(1);
		article.setBusinessId(1);
		article.setName("真正的内容");
		Response responseBusinessDetail = client.put(article, Response.class);
		System.out.println(responseBusinessDetail.getStatus());
	}

	public void testGetBusinessDetail() {
		System.out.println("test getBusinessDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessDetail/{id}", 1).accept(format).type(format);
		BusinessDetail responseBusinessDetail = client.get(BusinessDetail.class);
		if (responseBusinessDetail != null) {
			System.out.println(responseBusinessDetail.getName());
		}
	}

	public void testGetBusinessDetails() {
		System.out.println("test getBusinessDetails called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessDetail/BusinessDetails/{1}", 1).accept(format).type(format);

		BusinessDetails articles = client.get(BusinessDetails.class);
		System.out.println(articles.getDetails() == null);
		if (articles.getDetails() != null) {
			for (BusinessDetail article : articles.getDetails()) {
				System.out.println(article.getName());
			}
		}
	}

	public void testDeleteBusinessDetail() {

		System.out.println("test deleteBusinessDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessDetail/{id}", 1).accept(format).type(format);

		Response response = client.delete();
		System.out.println(response.getStatus());

	}

}
