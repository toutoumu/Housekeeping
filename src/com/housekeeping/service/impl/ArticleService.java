package com.housekeeping.service.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IArticleDao;
import com.housekeeping.entity.Article;
import com.housekeeping.entity.wrap.Articles;
import com.housekeeping.service.IArticleService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class ArticleService implements IArticleService {

	private IArticleDao<Article> articleDao;

	public void setArticleDao(IArticleDao<Article> articleDao) {
		this.articleDao = articleDao;
	}

	@Override
	public Response addArticle(Article retArticle) {
		if (retArticle != null) {
			retArticle.setCreateTime(new Date());
			retArticle.setUpdateTime(new Date());
			int id = (int) articleDao.save(retArticle);
			retArticle.setId(id);
			return Response.ok(retArticle).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加文章时，文章不能为空");
	}

	@Override
	public Response deleteArticle(int id) {
		if (id != 0) {
			Article article = articleDao.get(id);
			if (article != null) {
				articleDao.remove(article);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("文章不存在");
		}
		throw ServiceErrorBuilder.badRequestError("修改文章时Id不能为空");
	}

	@Override
	public Response updateArticle(Article user) {
		if (user != null & user.getId() != 0) {
			user.setUpdateTime(new Date());
			if (articleDao.update(user)) {
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("修改文章信息失败");
		}
		throw ServiceErrorBuilder.badRequestError("修改文章时Id不能为空");
	}

	@Override
	public Article getArticle(int id) {
		if (id != 0) {
			Article retArticle = articleDao.get(id);
			return retArticle;
		}
		throw ServiceErrorBuilder.badRequestError("查询文章时Id不能为空！");
	}

	@Override
	public Articles getArticleByCategory(int categoryId) {
		List<Article> articles2 = articleDao.findBy("categoryId", categoryId);
		if (articles2 == null) {
			return null;
		}
		Articles articles = new Articles();
		articles.setArticles(articles2);
		return articles;
	}

	@Override
	public Articles query(Article article) {
		if (article == null) {
			throw ServiceErrorBuilder.badRequestError("输入参数有误！");
		}
		StringBuilder hql = new StringBuilder("from Article ar where 1=1 ");
		if (article.getCategoryId() != 0) {
			hql.append(" and ");
			hql.append(" ar.categoryId = ");
			hql.append(article.getCategoryId());
		}

		if (!StringUtil.strIsEmpty(article.getTitle())) {
			hql.append(" and ");
			hql.append(" ar.title like '%");
			hql.append(article.getTitle());
			hql.append("%'");
		}
		List<Article> articleList = (List<Article>) articleDao.find(hql.toString(), null);
		Articles articles = new Articles();
		articles.setArticles(articleList);
		return articles;
	}
}
