package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Article;

public class ArticleDaoTest extends TestCase {
	private IArticleDao<Article> articleDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		articleDao = (IArticleDao<Article>) ctx.getBean("articleDao");
	}

	public void testAddArticle() {
		Article article = new Article();
		article.setCategoryId(12);
		article.setTitle("biaoti");
		article.setContent("neironga");
		int id = (int) articleDao.save(article);
		System.out.println(id); 
	}

	public void testUpdateArticle() {
		Article article = new Article();
		article.setId(1);
		article.setCategoryId(12);
		article.setTitle("biaoti");
		article.setContent("新的内容啊");
		boolean b = articleDao.update(article);
		System.out.println(b);
	}

	public void testGetArticle() {
		Article article = null;
		article = articleDao.get(1);
		if (article != null) {
			System.out.println(article.getContent());
		}
	}

	public void testGetArticlesByCategory() {
		List<Article> articles = articleDao.findBy("categoryId", 12);
		if (articles != null) {
			for (Article article : articles) {
				System.out.println(article.getContent());
			}
		}
	}

	public void testDelteArticle() {
		System.out.println(articleDao.removeById(1));
	}
}
