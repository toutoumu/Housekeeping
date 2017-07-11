package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IManagerDao;

public class ManagerDao<T> extends BaseDao<T, Serializable> implements IManagerDao<T> {

	public ManagerDao(Class<T> type) {
		super(type);
	}

}
