package com.housekeeping.dao;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Order;

public class OrderDaoTest extends TestCase {
	private IOrderDao<Order> orderDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		orderDao = (IOrderDao<Order>) ctx.getBean("orderDao");
	}

	public void testAddOrder() {
		Order order = new Order();
		order.setAddress("shenzhen");
		order.setOrderNumber("zhangdanbianhao");

		String id = (String) orderDao.save(order);
		System.out.println(id);
	}

	public void testUpdateOrder() {
		Order order = new Order();
		order.setAddress("深圳");
		order.setOrderNumber("zhangdanbianhao");
		order.setUserId(1);
		boolean b = orderDao.update(order);
		System.out.println(b);

	}

	public void testGetOrder() {
		Order order = null;
		order = orderDao.get("zhangdanbianhao");
		if (order != null) {
			System.out.println(order.getAddress());
		}
	}

	public void testgetOrderByUserId() {
		List<Order> orders = orderDao.findBy("userId", 1);
		if (orders != null) {
			for (Order order : orders) {
				System.out.println(order.getAddress());
			}
		}
	}

	public void testgetOrders() {
		Order order = new Order();
		order.setAddress("深圳");
		order.setOrderNumber("zhangdanbianhao");
		order.setUserId(1);
		order.setOrderState(1);
		order.setBusinessCategoryId(1);
		order.setBusinessCategoryName("asd");
		order.setArea(1);
		order.setUserId(2);
		order.setUserName("asdf");
		order.setOrderPrice(12);
		order.setOrderTime(new Date());
		order.setStartTime(new Date());
		List<Order> orders = orderDao.getOrders(order);
		if (orders != null) {
			for (Order o : orders) {
				System.out.println(o.getAddress());
			}
		}
	}

	public void testgetOrderByEmployeeId() {
		List<Order> orders = orderDao.getOrderByEmployeeId(1);
		if (orders != null) {
			for (Order order : orders) {
				System.out.println(order.getAddress());
			}
		}
	}

	public void testDelteOrder() {
		System.out.println(orderDao.removeById("dingdan"));
	}

	public void testSearch() {
		List<Order> orders = (List<Order>) orderDao.search("深圳");
		if (orders != null) {
			for (Order order : orders) {
				System.out.println(order.getAddress());
			}
		}
	}
}
