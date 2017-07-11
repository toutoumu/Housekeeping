package com.housekeeping.dao;

import java.io.Serializable;

import com.hibernate.dao.base.IBaseDao;
import com.housekeeping.entity.Order_Employee;
import com.housekeeping.entity.pk.Order_Employee_PK;

public interface IOrder_EmployeeDao<T> extends IBaseDao<T, Serializable> {

	/**
	 * 根据主键查询{@link Order_Employee}是否存在
	 * @param pk
	 * @return
	 */
	boolean IsExist(Order_Employee_PK pk);

}
