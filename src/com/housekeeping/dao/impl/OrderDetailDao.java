package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IOrderDetailDao;

public class OrderDetailDao<T> extends BaseDao<T, Serializable> implements IOrderDetailDao<T> {

	public OrderDetailDao(Class<T> type) {
		super(type);
 	}
}
