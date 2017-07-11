package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IBusinessCategoryDao;

public class BusinessCategoryDao<T> extends BaseDao<T, Serializable> implements IBusinessCategoryDao<T> {

	public BusinessCategoryDao(Class<T> type) {
		super(type);
	}

}
