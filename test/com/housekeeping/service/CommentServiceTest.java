package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Comment;
import com.housekeeping.entity.wrap.Comments;

public class CommentServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/CommentService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddComment() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddComment called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Comment").accept(format).type(format);
		Comment article = new Comment();
		article.setEmployeeId(1);
		article.setUserId(1);
		article.setContent("contentaslkdjflaks");
		Comment responseComment = client.post(article, Comment.class);
		System.out.println(responseComment.getId());
	}

	public void testUpdateComment() {
		System.out.println("test updateComment called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Comment").accept(format).type(format);
		Comment article = new Comment();
		article.setId(1);
		article.setUserId(1);
		article.setEmployeeId(1);
		article.setContent("真正的内容");
		Response responseComment = client.put(article, Response.class);
		System.out.println(responseComment.getStatus());
	}

	public void testGetComment() {
		System.out.println("test getComment called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Comment/{id}", 1).accept(format).type(format);
		Comment responseComment = client.get(Comment.class);
		if (responseComment != null) {
			System.out.println(responseComment.getContent());
		}
	}

	public void testGetCommentsByCategoryId() {
		System.out.println("test getComments called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Comment/Comments/{id}", 1).accept(format).type(format);

		Comments articles = client.get(Comments.class);
		System.out.println(articles.getComments() == null);
		if (articles.getComments() != null) {
			for (Comment article : articles.getComments()) {
				System.out.println(article.getContent());
			}
		}
	}

	public void testDeleteComment() {

		System.out.println("test deleteComment called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Comment/{id}", 1).accept(format).type(format);

		Response response = client.delete();
		System.out.println(response.getStatus());

	}

}
