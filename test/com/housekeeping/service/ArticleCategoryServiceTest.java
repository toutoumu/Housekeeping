package com.housekeeping.service;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.ArticleCategory;
import com.housekeeping.entity.wrap.ArticleCategorys;

public class ArticleCategoryServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/ArticleCategoryService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddArticleCategory() {

		System.out.println("test AddArticleCategory called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/ArticleCategory").accept(format).type(format);
		ArticleCategory category = new ArticleCategory();
		category.setCreateTime(new Date());
		category.setName("第一个");
		ArticleCategory response = client.post(category, ArticleCategory.class);
		System.out.println(response.getId());
	}

	public void testUpdateArticleCategory() {
		System.out.println("test updateArticleCategory called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/ArticleCategory").accept(format).type(format);
		ArticleCategory category = new ArticleCategory();
		category.setId(1);
		category.setCreateTime(new Date());
		category.setName("第second个");
		Response responseArticleCategory = client.put(category, Response.class);
		System.out.println(responseArticleCategory.getStatus());
	}

	public void testGetArticleCategory() {
		System.out.println("test getArticleCategory called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/ArticleCategory/{id}", 2).accept(format).type(format);
		ArticleCategory responseArticleCategory = client.get(ArticleCategory.class);
		if (responseArticleCategory != null) {
			System.out.println(responseArticleCategory.getName());
		}
	}

	public void testGetArticleCategorys() {
		System.out.println("test getArticleCategorys called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/ArticleCategory/ArticleCategorys").accept(format).type(format);

		ArticleCategorys responseArticleCategory = client.get(ArticleCategorys.class);
		System.out.println(responseArticleCategory.getArticleCategories() == null);
		if (responseArticleCategory.getArticleCategories() != null) {
			for (ArticleCategory category : responseArticleCategory.getArticleCategories()) {
				System.out.println(category.getName());
			}
		}
	}

	public void testDeleteArticleCategory() {
		System.out.println("test deleteArticleCategory called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/ArticleCategory/{id}", 1).accept(format).type(format);
		Response response = client.delete();
		System.out.println(response.getStatus());

	}

}
