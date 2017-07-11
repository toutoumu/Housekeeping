package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Query;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IOrderDao;
import com.housekeeping.entity.Order;

public class OrderDao<T> extends BaseDao<T, Serializable> implements IOrderDao<T> {

	public OrderDao(Class<T> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrderByEmployeeId(int employeeId) {
		String hql = "select ord from Order ord, Order_Employee e where ord.orderNumber = e.pk.orderNumber and e.pk.employeeId=?";
		return (List<Order>) find(hql, employeeId);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrders(Order order) {
		if (order == null) {
			return null;
		}
		int paraIndex = 0;
		Map<Integer, Object> parasMap = new HashMap<Integer, Object>();
		StringBuilder hql = new StringBuilder("from Order ord where 1=1 ");
		//预定时间(工作时长？) 、可服务项目、下单时间、服务时间 、支付状态、订单状态

		// 预定时间，下单时间
		if (order.getOrderTime() != null) {
			// 只比较传入时间的yyyyMMddhh是否与数据库数据一致
			Calendar savedTime = Calendar.getInstance(Locale.getDefault());
			savedTime.setTime(order.getOrderTime());
			savedTime.set(Calendar.MINUTE, 0);
			savedTime.set(Calendar.SECOND, 0);
			savedTime.set(Calendar.HOUR, 0);
			Date startTime = savedTime.getTime();
			savedTime.add(Calendar.DATE, 1);
			Date endTime = savedTime.getTime();
			parasMap.put(paraIndex, startTime);
			paraIndex++;
			parasMap.put(paraIndex, endTime);
			paraIndex++;

			hql.append(" and ");
			hql.append(" ord.orderTime > ?");

			hql.append(" and ");
			hql.append(" ord.orderTime < ?");
		}

		// 服务开始时间
		if (order.getStartTime() != null) {
			// 只比较传入时间的yyyyMMddhh是否与数据库数据一致
			Calendar savedTime = Calendar.getInstance(Locale.getDefault());
			savedTime.setTime(order.getStartTime());
			savedTime.set(Calendar.MINUTE, 0);
			savedTime.set(Calendar.SECOND, 0);
			savedTime.set(Calendar.HOUR, 0);
			Date startTime = savedTime.getTime();
			savedTime.add(Calendar.DATE, 1);
			Date endTime = savedTime.getTime();

			parasMap.put(paraIndex, startTime);
			paraIndex++;
			parasMap.put(paraIndex, endTime);
			paraIndex++;

			hql.append(" and ");
			hql.append(" ord.startTime > ?");

			hql.append(" and ");
			hql.append(" ord.startTime < ?");
		}

		// 订单编号
		if (order.getOrderNumber() != null) {
			hql.append(" and ");
			hql.append(" ord.orderNumber like '%");
			hql.append(order.getOrderNumber());
			hql.append("%'");
		}
		// 支付状态
		if (order.getPaymentState() != 0) {
			hql.append(" and ");
			hql.append(" ord.paymentState = ");
			hql.append(order.getPaymentState());
		}
		// 订单状态
		if (order.getOrderState() != 0) {
			hql.append(" and ");
			hql.append(" ord.orderState =  ");
			hql.append(order.getOrderState());
		}
		// 工作时长
		if (order.getWorkingTime() != 0) {
			hql.append(" and ");
			hql.append(" ord.workingTime =  ");
			hql.append(order.getWorkingTime());
		}
		// 服务分类
		if (order.getBusinessCategoryId() != 0) {
			hql.append(" and ");
			hql.append(" ord.businessCategoryId = ");
			hql.append(order.getBusinessCategoryId());
		}
		// 服务地址
		if (order.getAddress() != null) {
			hql.append(" and ");
			hql.append(" ord.address like '%");
			hql.append(order.getAddress());
			hql.append("%'");
		}
		// 服务区域
		if (order.getArea() != 0) {
			hql.append(" and ");
			hql.append(" ord.area = ");
			hql.append(order.getArea());
		}

		if (order.getBusinessCategoryName() != null) {
			hql.append(" and ");
			hql.append(" ord.businessCategoryName like '%");
			hql.append(order.getBusinessCategoryName());
			hql.append("%'");
		}
		// 订单金额
		if (order.getOrderPrice() != 0) {
			hql.append(" and ");
			hql.append(" ord.orderPrice =  ");
			hql.append(order.getOrderPrice());
		}

		hql.append(" order by ord.startTime desc ord.orderTime desc");

		Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery(hql.toString());
		for (Integer integer : parasMap.keySet()) {
			query.setParameter(integer, parasMap.get(integer));
		}
		return query.list();
		//return (List<Order>) find(hql.toString(), null);
	}

	@Override
	public List<Order> search(String searchContent) {
		StringBuilder builder = new StringBuilder();
		builder.append("select o from Order o where ");
		// 订单编号
		builder.append("o.orderNumber like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 雇员姓名
		builder.append("o.employeeName like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 雇员电话
		builder.append("o.employeePhoneNumber like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 服务地址
		builder.append("o.address like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 客户名称
		builder.append("o.userName like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 客户电话
		builder.append("o.phoneNumber like '%");
		builder.append(searchContent);
		builder.append("%'");

		System.out.println(builder.toString());
		List<Order> orders = (List<Order>) find(builder.toString(), null);
		return orders;
	}
}
