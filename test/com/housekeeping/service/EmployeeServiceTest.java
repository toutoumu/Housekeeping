package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Employee;
import com.housekeeping.entity.wrap.Employees;

public class EmployeeServiceTest extends junit.framework.TestCase {

	public final String CATEGORY_URL = "http://localhost:8080/Housekeeping/ws/EmployeeService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddEmployee() {
		System.out.println("testAddEmployee called with format " + format);
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Employee").accept(format).type(format);
		Employee employee = new Employee();
		employee.setBusinessCategoryId(1);
		employee.setName("验证码");
		employee = client.post(employee, Employee.class);
		if (employee != null) {
			System.out.println(employee.getId());
		}
	}

	public void testGetEmployee() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Employee/{id}", 1).accept(format).type(format);
		Employee employee = client.get(Employee.class);
		if (employee != null) {
			System.out.println(employee.getName());
		}
	}

	public void testGetEmployeeByBusinessCategoryId() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Employee/EmployeeByBusinessCategoryId/{id}", 1).accept(format).type(format);
		Employees employees = client.get(Employees.class);
		if (employees != null) {
			for (Employee employee : employees.getEmployees()) {
				System.out.println(employee.getName());
			}
		}
	}

	public void testGetEmployeeByDefaultCategory() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Employee/EmployeeByDefaultCategory/{id}", 1).accept(format).type(format);
		Employees employees = client.get(Employees.class);
		if (employees != null) {
			for (Employee employee : employees.getEmployees()) {
				System.out.println(employee.getName());
			}
		}
	}

	public void testGetEmployeeByOrderNumber() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Employee/EmployeeByOrderNumber/{id}", 1).accept(format).type(format);
		Employees employees = client.get(Employees.class);
		if (employees != null) {
			for (Employee employee : employees.getEmployees()) {
				System.out.println(employee.getName());
			}
		}
	}

	public void testUpdateEmployee() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Employee").accept(format).type(format);
		Employee employee = new Employee();
		employee.setGrade(2.0f);
		employee.setName("验证码a ");
		employee.setId(1);
		Response response = client.put(employee);
		System.out.println(response.getStatus());
	}

	public void testDeleteEmployee() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Employee/{id}", 1).accept(format).type(format);
		Response employee = client.delete();
		if (employee != null) {
			System.out.println(employee.getStatus());
		}
	}

	public void testSearch() {
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/Employee/search/{id}", "liu").accept(format).type(format);
		Employees employees = client.get(Employees.class);
		if (employees != null) {
			for (Employee employee : employees.getEmployees()) {
				System.out.println(employee.getName());
			}
		}
	}

}
