package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IRegionDao;

public class RegionDao<T> extends BaseDao<T, Serializable> implements IRegionDao<T> {
	public RegionDao(Class<T> type) {
		super(type);
	}
}
