package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.ArticleCategory;

public class ArticleCategoryDaoTest extends TestCase {
	private IArticleCategoryDao<ArticleCategory> articleCategory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		articleCategory = (IArticleCategoryDao<ArticleCategory>) ctx.getBean("articleCategoryDao");
	}

	public void testAddArticleCategory() {
		ArticleCategory article = new ArticleCategory();
		article.setName("日记本");
		int id = (int) articleCategory.save(article);
		article.setId(id);
		if (article != null) {
			System.out.println(article.getName());
		}
	}

	public void testUpdateArticleCategory() {
		ArticleCategory article = new ArticleCategory();
		article.setId(1);
		article.setName("新的日记本啊");
		boolean b = articleCategory.update(article);
		System.out.println(b);
	}

	public void testGetArticleCategory() {
		ArticleCategory article = null;
		article = articleCategory.get(1);
		if (article != null) {
			System.out.println(article.getName());
		}
	}

	public void testGetArticleCategorys() {
		List<ArticleCategory> articles = articleCategory.getAll();
		if (articles != null) {
			for (ArticleCategory article : articles) {
				System.out.println(article.getName());
			}
		}
	}

	public void testDelteArticleCategory() {
		System.out.println(articleCategory.removeById(1));
	}
}
