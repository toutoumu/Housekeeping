package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.ICommentDao;

public class CommentDao<T> extends BaseDao<T, Serializable> implements ICommentDao<T> {

	public CommentDao(Class<T> type) {
		super(type);
	}
}
