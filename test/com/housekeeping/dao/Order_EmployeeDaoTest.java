package com.housekeeping.dao;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Order_Employee;
import com.housekeeping.entity.pk.Order_Employee_PK;

public class Order_EmployeeDaoTest extends TestCase {
	private IOrder_EmployeeDao<Order_Employee> order_EmployeeDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		order_EmployeeDao = (IOrder_EmployeeDao<Order_Employee>) ctx.getBean("order_EmployeeDao");
	}

	public void testAdd() {
		Order_Employee_PK pk = new Order_Employee_PK();
		pk.setEmployeeId(1);
		pk.setOrderNumber("123");
		Order_Employee menu = new Order_Employee();
		menu.setPk(pk);

		pk = (Order_Employee_PK) order_EmployeeDao.save(menu);
		System.out.println(menu.getPk().getEmployeeId());
	}

	public void testDelete() {
		Order_Employee_PK pk = new Order_Employee_PK();
		pk.setEmployeeId(1);
		pk.setOrderNumber("123");
		System.out.println(order_EmployeeDao.removeById(pk));
	}

	public void testGet() {
		Order_Employee_PK pk = new Order_Employee_PK();
		pk.setEmployeeId(1);
		pk.setOrderNumber("123");
		Order_Employee manager_Menu = order_EmployeeDao.get(pk);
		if (manager_Menu != null) {
			System.out.println(manager_Menu.getPk().getEmployeeId());
		}
	}

	public void testIsExist() {
		Order_Employee_PK pk = new Order_Employee_PK();
		pk.setEmployeeId(1);
		pk.setOrderNumber("123");
		System.err.println(order_EmployeeDao.IsExist(pk));
	}
}
