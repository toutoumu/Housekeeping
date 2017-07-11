package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Order_Employee;
import com.housekeeping.entity.pk.Order_Employee_PK;

public class Order_EmployeeServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/Order_EmployeeService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddOrder_Employee() {

		System.out.println("test AddOrder_Employee called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Order_Employee").accept(format).type(format);
		Order_Employee bEmployee = new Order_Employee();
		Order_Employee_PK pk = new Order_Employee_PK();
		pk.setEmployeeId(26);
		pk.setOrderNumber("1");
		bEmployee.setPk(pk);

		Order_Employee response = client.post(bEmployee, Order_Employee.class);
		System.out.println(response.getPk().getOrderNumber());
	}

	public void testDeleteOrder_Employee() {
		System.out.println("test updateOrder_Employee called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Order_Employee/{employeeId}/{orderNumber}", 26, 1).accept(format).type(format);
		Response response = client.delete();
		System.out.println(response.getStatus());
	}

	public void testisgetUserOrder_Employee() {
		System.out.println("test getOrder_Employee called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Order_Employee/{employeeId}/{orderNumber}", 26, 1).accept(format).type(format);
		Order_Employee manager_Menu = client.get(Order_Employee.class);
		if (manager_Menu != null) {
			System.out.println(manager_Menu.getPk().getOrderNumber());

		}
	}

	public void testIsExist() {
		System.out.println("test getOrder_Employee called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Order_Employee/IsExist/{employeeId}/{orderNumber}", 1, 1).accept(format).type(format);

		boolean manager_Menu = client.get(boolean.class);
		System.out.println(manager_Menu);
	}

}
