package com.housekeeping.service.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IArticleCategoryDao;
import com.housekeeping.entity.ArticleCategory;
import com.housekeeping.entity.wrap.ArticleCategorys;
import com.housekeeping.service.IArticleCategoryService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class ArticleCategoryService implements IArticleCategoryService {

	private IArticleCategoryDao<ArticleCategory> articleCategoryDao;

	@Override
	public Response addArticleCategory(ArticleCategory articleCategory) {
		if (articleCategory != null) {
			articleCategory.setCreateTime(new Date());
			int id = (int) articleCategoryDao.save(articleCategory);
			articleCategory.setId(id);
			return Response.ok(articleCategory).build();
		}
		throw ServiceErrorBuilder.badRequestError("输入内容为空");
	}

	@Override
	public Response deleteArticleCategory(int id) {
		if (id != 0) {
			ArticleCategory category = articleCategoryDao.get(id);
			if (category != null) {
				articleCategoryDao.remove(category);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("文章分类不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除文章分类时主键不能为空");
	}

	@Override
	public Response updateArticleCategory(ArticleCategory category) {
		if (category != null) {
			if (articleCategoryDao.update(category)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新文章分类时主键不能为空");
	}

	@Override
	public ArticleCategory getArticleCategory(int id) {
		if (id == 0) {
			throw ServiceErrorBuilder.badRequestError("查询文章分类时主键不能为空");
		}
		ArticleCategory category = articleCategoryDao.get(id);
		return category;
	}

	@Override
	public ArticleCategorys getArticleCategorys() {
		ArticleCategorys categorys = new ArticleCategorys();
		List<ArticleCategory> articleCategories = articleCategoryDao.getAll();
		if (articleCategories == null) {
			return null;
		}
		categorys.setArticleCategories(articleCategories);
		return categorys;
	}

	public void setArticleCategoryDao(IArticleCategoryDao<ArticleCategory> articleCategoryDao) {
		this.articleCategoryDao = articleCategoryDao;
	}

	@Override
	public ArticleCategorys search(String name) {

		if (StringUtil.strIsEmpty(name)) {
			ArticleCategorys categorys = new ArticleCategorys();
			List<ArticleCategory> articleCategories = articleCategoryDao.getAll();
			if (articleCategories == null) {
				return null;
			}
			categorys.setArticleCategories(articleCategories);
			return categorys;
		} else {
			String hql = "from ArticleCategory ac where ac.name like '%" + name + "%'";
			List<ArticleCategory> articleCategories = (List<ArticleCategory>) articleCategoryDao.find(hql, null);
			ArticleCategorys categorys = new ArticleCategorys();
			if (articleCategories == null) {
				return null;
			}
			categorys.setArticleCategories(articleCategories);
			return categorys;
		}
	}
}
