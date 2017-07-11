package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Article;
import com.housekeeping.entity.wrap.Articles;

public class ArticleServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/ArticleService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddArticle() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddArticle called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Article").accept(format).type(format);
		Article article = new Article();
		article.setCategoryId(2);
		article.setContent("contentaslkdjflaks");
		Article responseArticle = client.post(article, Article.class);
		System.out.println(responseArticle.getId());
	}

	public void testUpdateArticle() {
		System.out.println("test updateArticle called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Article").accept(format).type(format);
		Article article = new Article();
		article.setId(1);
		article.setCategoryId(2);
		article.setContent("真正的内容");
		Response responseArticle = client.put(article, Response.class);
		System.out.println(responseArticle.getStatus());
	}

	public void testGetArticle() {
		System.out.println("test getArticle called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Article/{id}", 1).accept(format).type(format);
		Article responseArticle = client.get(Article.class);
		if (responseArticle != null) {
			System.out.println(responseArticle.getContent());
		}
	}

	public void testGetArticlesByCategoryId() {
		System.out.println("test getArticles called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Article/Articles/{id}", 2).accept(format).type(format);

		Articles articles = client.get(Articles.class);
		System.out.println(articles.getArticles() == null);
		if (articles.getArticles() != null) {
			for (Article article : articles.getArticles()) {
				System.out.println(article.getContent());
			}
		}
	}

	public void testDeleteArticle() {

		System.out.println("test deleteArticle called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Article/{id}", 1).accept(format).type(format);

		Response response = client.delete();
		System.out.println(response.getStatus());

	}

}
