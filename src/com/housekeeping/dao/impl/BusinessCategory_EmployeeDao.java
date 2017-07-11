package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IBusinessCategory_EmployeeDao;

public class BusinessCategory_EmployeeDao<T> extends BaseDao<T, Serializable> implements
		IBusinessCategory_EmployeeDao<T> {
	public BusinessCategory_EmployeeDao(Class<T> type) {
		super(type);
	}
}
