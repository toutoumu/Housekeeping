package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Comment;

public class CommentDaoTest extends TestCase {
	private ICommentDao<Comment> commentDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		commentDao = (ICommentDao<Comment>) ctx.getBean("commentDao");
	}

	public void testAddComment() {
		Comment article = new Comment();
		article.setContent("neironga");
		article.setUserId(12);
		article.setEmployeeId(12);
		int id = (int) commentDao.save(article);
		System.out.println(id);
	}

	public void testUpdateComment() {
		Comment article = new Comment();
		article.setId(1);
		article.setUserId(12);
		article.setEmployeeId(12);
		article.setContent("新的内容啊");
		boolean b = commentDao.update(article);
		System.out.println(b);
	}

	public void testGetComment() {
		Comment article = null;
		article = commentDao.get(1);
		if (article != null) {
			System.out.println(article.getContent());
		}
	}

	public void testGetCommentsByCategory() {
		List<Comment> articles = commentDao.findBy("employeeId", 12);
		if (articles != null) {
			for (Comment article : articles) {
				System.out.println(article.getContent());
			}
		}
	}

	public void testDelteComment() {
		System.out.println(commentDao.removeById(1));
	}
}
