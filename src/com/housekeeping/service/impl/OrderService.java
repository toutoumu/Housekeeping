package com.housekeeping.service.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IOrderDao;
import com.housekeeping.entity.Order;
import com.housekeeping.entity.wrap.Orders;
import com.housekeeping.service.IOrderService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class OrderService implements IOrderService {
	private IOrderDao<Order> orderDao;

	public void setOrderDao(IOrderDao<Order> orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	public Response addOrder(Order order) {
		if (order != null && !StringUtil.strIsEmpty(order.getOrderNumber())) {
			if (orderDao.findUniqueBy("orderNumber", order.getOrderNumber()) != null) {
				throw ServiceErrorBuilder.badRequestError("该订单已经存在");
			}
			order.setOrderTime(new Date());
			orderDao.save(order);
			if (order != null) {
				return Response.ok(order).build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteOrder(String orderNumber) {
		if (!StringUtil.strIsEmpty(orderNumber)) {
			Order order = orderDao.get(orderNumber);
			if (order != null) {
				orderDao.remove(order);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("订单不存在");
		}
		throw ServiceErrorBuilder.badRequestError("订单编号不能为空");
	}

	@Override
	public Response updateOrder(Order order) {
		if (order != null && !StringUtil.strIsEmpty(order.getOrderNumber())) {
			if (orderDao.update(order)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新订单时订单编号不能为空");
	}

	@Override
	public Order getOrder(String orderNumber) {
		if (!StringUtil.strIsEmpty(orderNumber)) {
			return orderDao.get(orderNumber);
		}
		throw ServiceErrorBuilder.badRequestError("订单编号不能为空");
	}

	@Override
	public Orders getOrderByUserId(int userId) {
		if (userId != 0) {
			List<Order> orders = orderDao.findBy("userId", userId);
			if (orders == null) {
				return null;
			}
			Orders orders2 = new Orders();
			orders2.setOrders(orders);
			return orders2;
		}
		throw ServiceErrorBuilder.badRequestError("用户ID不能为空");
	}

	@Override
	public Orders getOrderByEmployeeId(int employeeId) {
		if (employeeId != 0) {
			List<Order> orders = orderDao.getOrderByEmployeeId(employeeId);
			if (orders == null) {
				return null;
			}
			Orders orders2 = new Orders();
			orders2.setOrders(orders);
			return orders2;
		}
		throw ServiceErrorBuilder.badRequestError("员工编号不能为空");
	}

	@Override
	public Orders search(String searchContent) {
		if (!StringUtil.strIsEmpty(searchContent)) {
			List<Order> orderList = orderDao.search(searchContent);
			if (orderList != null) {
				Orders orders = new Orders();
				orders.setOrders(orderList);
				return orders;
			}
		}
		throw ServiceErrorBuilder.badRequestError("搜索内容不能为空");
	}

	@Override
	public Orders query(Order order) {
		if (order == null) {
			throw ServiceErrorBuilder.badRequestError("订单不能为空");
		}
		List<Order> orderList = orderDao.getOrders(order);
		if (orderList == null || orderList.size() == 0) {
			return null;
		}
		Orders orders = new Orders();
		orders.setOrders(orderList);
		return orders;
	}
}
