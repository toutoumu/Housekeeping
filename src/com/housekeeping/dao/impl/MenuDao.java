package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IMenuDao;

public class MenuDao<T> extends BaseDao<T, Serializable> implements IMenuDao<T> {

	public MenuDao(Class<T> type) {
		super(type);
	}

}
