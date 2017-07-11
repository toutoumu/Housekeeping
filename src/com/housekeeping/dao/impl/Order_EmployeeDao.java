package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IOrder_EmployeeDao;
import com.housekeeping.entity.pk.Order_Employee_PK;

public class Order_EmployeeDao<T> extends BaseDao<T, Serializable> implements IOrder_EmployeeDao<T> {

	public Order_EmployeeDao(Class<T> type) {
		super(type);
	}

	@Override
	public boolean IsExist(Order_Employee_PK pk) {
		if (pk == null) {
			return false;
		}
		StringBuilder builder = new StringBuilder(
				"select count(*) from Order_Employee as ma where ma.pk.orderNumber=? and ma.pk.employeeId=?");
		List<?> ret = find(builder.toString(), pk.getOrderNumber(), pk.getEmployeeId());
		if (ret != null) {
			return Integer.parseInt(ret.get(0).toString()) > 0;
		}
		return false;
	}

}
