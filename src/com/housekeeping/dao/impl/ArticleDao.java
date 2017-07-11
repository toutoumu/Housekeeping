package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IArticleDao;

public class ArticleDao<T> extends BaseDao<T, Serializable> implements IArticleDao<T> {

	public ArticleDao(Class<T> type) {
		super(type);
 	}
}
