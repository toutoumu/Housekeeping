package com.housekeeping.service;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.ScoreDetail;
import com.housekeeping.entity.wrap.ScoreDetails;

public class ScoreDetailServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/ScoreDetailService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddScoreDetail() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddScoreDetail called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/ScoreDetail").accept(format).type(format);
		ScoreDetail article = new ScoreDetail();
		article.setDescription("adsf");
		article.setScore(23);
		article.setTime(new Date());
		article.setUserId(2);
		ScoreDetail responseScoreDetail = client.post(article, ScoreDetail.class);
		System.out.println(responseScoreDetail.getId());
	}

	public void testUpdateScoreDetail() {
		System.out.println("test updateScoreDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/ScoreDetail").accept(format).type(format);
		ScoreDetail article = new ScoreDetail();
		article.setId(1);
		article.setDescription("dddddddddddddd");
		article.setScore(23);
		article.setTime(new Date());
		article.setUserId(2);
		Response responseScoreDetail = client.put(article, Response.class);
		System.out.println(responseScoreDetail.getStatus());
	}

	public void testGetScoreDetail() {
		System.out.println("test getScoreDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/ScoreDetail/{id}", 1).accept(format).type(format);
		ScoreDetail responseScoreDetail = client.get(ScoreDetail.class);
		if (responseScoreDetail != null) {
			System.out.println(responseScoreDetail.getDescription());
		}
	}

	public void testGetScoreDetailsByCategoryId() {
		System.out.println("test getScoreDetails called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/ScoreDetail/ScoreDetails/{id}", 2).accept(format).type(format);

		ScoreDetails articles = client.get(ScoreDetails.class);
		System.out.println(articles.getScoreDetails() == null);
		if (articles.getScoreDetails() != null) {
			for (ScoreDetail article : articles.getScoreDetails()) {
				System.out.println(article.getDescription());
			}
		}
	}

	public void testDeleteScoreDetail() {

		System.out.println("test deleteScoreDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/ScoreDetail/{id}", 1).accept(format).type(format);

		Response response = client.delete();
		System.out.println(response.getStatus());

	}

}
