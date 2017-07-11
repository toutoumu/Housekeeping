package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IBusinessDetailDao;

public class BusinessDetailDao<T> extends BaseDao<T, Serializable> implements IBusinessDetailDao<T> {

	public BusinessDetailDao(Class<T> type) {
		super(type);
	}

}
