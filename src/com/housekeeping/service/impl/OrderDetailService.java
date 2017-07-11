package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IOrderDetailDao;
import com.housekeeping.entity.OrderDetail;
import com.housekeeping.entity.wrap.OrderDetails;
import com.housekeeping.service.IOrderDetailService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class OrderDetailService implements IOrderDetailService {
	private IOrderDetailDao<OrderDetail> orderDetailDao;

	public void setOrderDetailDao(IOrderDetailDao<OrderDetail> orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	@Override
	public Response addOrderDetail(OrderDetail detail) {
		if (detail != null) {
			int id = (int) orderDetailDao.save(detail);
			detail.setId(id);
			return Response.ok(detail).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加订单明细失败");
	}

	@Override
	public Response deleteOrderDetail(int detailId) {
		if (detailId != 0) {
			OrderDetail detail = orderDetailDao.get(detailId);
			if (detail != null) {
				orderDetailDao.remove(detail);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("订单明细不存在");
		}
		throw ServiceErrorBuilder.badRequestError("订单明细ID不能为空");
	}

	@Override
	public Response updateOrderDetail(OrderDetail detail) {
		if (detail != null && detail.getId() != 0) {
			if (orderDetailDao.update(detail)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新订单明细时主键不能为空");
	}

	@Override
	public OrderDetail getOrderDetail(int id) {
		if (id != 0) {
			return orderDetailDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询订单明细时主键不能为空");
	}

	@Override
	public OrderDetails getOrderDetails(String orderNumber) {
		if (!StringUtil.strIsEmpty(orderNumber)) {
			List<OrderDetail> orderDetails = orderDetailDao.findBy("orderNumber", orderNumber);
			if (orderDetails == null) {
				return null;
			}
			OrderDetails details = new OrderDetails();
			details.setOrderDetails(orderDetails);
			return details;
		}
		throw ServiceErrorBuilder.badRequestError("根据订单编号查询订单明细时订单编号不能为空");
	}
}
