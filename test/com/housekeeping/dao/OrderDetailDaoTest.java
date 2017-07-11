package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.OrderDetail;

public class OrderDetailDaoTest extends TestCase {
	private IOrderDetailDao<OrderDetail> orderDetailDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		orderDetailDao = (IOrderDetailDao<OrderDetail>) ctx.getBean("orderDetailDao");
	}

	public void testAddOrderDetail() {
		OrderDetail detail = new OrderDetail();
		detail.setOrderNumber("123");
		detail.setCount(23);
		int id = (int) orderDetailDao.save(detail);
		System.out.println(id);
	}

	public void testUpdateOrderDetail() {
		OrderDetail detail = new OrderDetail();
		detail.setId(1);
		detail.setOrderNumber("123");
		detail.setCount(23);
		detail.setBusinessDetailId(3);
		boolean b = orderDetailDao.update (detail);
		System.out.println(b);
	}

	public void testGetOrderDetail() {
		OrderDetail order = null;
		order = orderDetailDao.get (1);
		if (order != null) {
			System.out.println(order.getCount());
		}
	}

	public void testgetOrderDetail() {
		List<OrderDetail> details = orderDetailDao.findBy("orderNumber", "123");
		if (details != null) {
			for (OrderDetail orderDetail : details) {
				System.out.println(orderDetail.getBusinessDetailId());
			}
		}
	}

	public void testDelteOrderDetail() {

		System.out.println(orderDetailDao.removeById(1));
	}
}
