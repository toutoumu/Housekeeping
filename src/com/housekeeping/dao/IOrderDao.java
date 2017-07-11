package com.housekeeping.dao;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.IBaseDao;
import com.housekeeping.entity.Order;

public interface IOrderDao<T> extends IBaseDao<T, Serializable> {

	/**
	 * 查询员工的订单
	 * @param employeeId
	 * @return
	 */
	List<Order> getOrderByEmployeeId(int employeeId);

	/**
	 * 查询订单,根据传入的查询条件查询订单
	 * @param order {@link Order}
	 * @return
	 */
	List<Order> getOrders(Order order);

	/**
	 * 根据关键字搜索</br>
	 * 搜索订单编号、服务地址、服务人员、服务人员电话、客户电话等字段内容</br>
	 * @param searchContent
	 * @return
	 */
	List<Order> search(String searchContent);

}
