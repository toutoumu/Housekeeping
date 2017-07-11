package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IBusinessDao;

public class BusinessDao<T> extends BaseDao<T, Serializable> implements IBusinessDao<T> {

	public BusinessDao(Class<T> type) {
		super(type);
	}
}
