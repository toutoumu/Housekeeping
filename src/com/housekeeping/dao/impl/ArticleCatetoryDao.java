package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IArticleCategoryDao;

public class ArticleCatetoryDao<T> extends BaseDao<T, Serializable> implements IArticleCategoryDao<T> {
	public ArticleCatetoryDao(Class<T> type) {
		super(type);
	}

}
