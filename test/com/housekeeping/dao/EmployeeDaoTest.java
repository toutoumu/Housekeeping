package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Employee;

public class EmployeeDaoTest extends TestCase {
	private IEmployeeDao<Employee> employeeDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		employeeDao = (IEmployeeDao<Employee>) ctx.getBean("employeeDao");
	}

	public void testAddEmployee() {
		Employee employee = new Employee();
		employee.setBusinessCategoryId(12);
		employee.setName("liubin");
		int id = (int) employeeDao.save(employee);
		if (employee != null) {
			System.out.println(employee.getBusinessCategoryId());
		}
	}

	public void testUpdateEmployee() {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setBusinessCategoryId(12);
		employee.setName("liubin");
		boolean b = employeeDao.update(employee);
		System.out.println(b);
	}

	public void testGetEmployee() {
		Employee employee = null;
		employee = employeeDao.get(1);
		if (employee != null) {
			System.out.println(employee.getBusinessCategoryId());
		}
	}

	public void testGetEmployeesByCategory() {
		List<Employee> employees = employeeDao.getEmployeeByDefaultCategory(12);
		if (employees != null) {
			for (Employee employee : employees) {
				System.out.println(employee.getBusinessCategoryId());
			}
		}
	}

	public void testgetEmployeeByBusinessCategoryId() {
		List<Employee> employees = employeeDao.getEmployeeByBusinessCategoryId(1);
		if (employees != null) {
			for (Employee employee : employees) {
				System.out.println(employee.getName());
			}
		}
	}

	public void testDelteEmployee() {
		System.out.println(employeeDao.removeById(1));
	}

	public void testgetEmployeeByOrderNumber() {
		List<Employee> employees = employeeDao.getEmployeeByOrderNumber("123");
		if (employees != null && employees.size() > 0) {
			System.out.println(employees.get(0).getBusinessCategoryId());
		}
	}

	public void testSearch() {
		List<Employee> employees = employeeDao.search("liu");
		if (employees != null) {
			for (Employee employee : employees) {
				System.out.println(employee.getName());
			}
		}
	}
}
