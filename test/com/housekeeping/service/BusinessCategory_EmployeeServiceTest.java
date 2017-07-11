package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.BusinessCategory_Employee;
import com.housekeeping.entity.pk.BusinessCategory_Employee_PK;

public class BusinessCategory_EmployeeServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/BusinessCategory_EmployeeService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddBusinessCategory_Employee() {

		System.out.println("test AddBusinessCategory_Employee called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/BusinessCategory_Employee").accept(format).type(format);
		BusinessCategory_Employee bEmployee = new BusinessCategory_Employee();
		BusinessCategory_Employee_PK pk = new BusinessCategory_Employee_PK();
		pk.setBusinessCategoryId(3);
		pk.setEmployeeId(1);
		bEmployee.setPk(pk);

		BusinessCategory_Employee response = client.post(bEmployee, BusinessCategory_Employee.class);
		System.out.println(response.getPk().getBusinessCategoryId());
	}

	public void testDeleteBusinessCategory_Employee() {
		System.out.println("test updateBusinessCategory_Employee called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessCategory_Employee/{businessCategorId}/{employeeId}", 1, 1).accept(format).type(format);
		Response response = client.delete();
		System.out.println(response.getStatus());
	}

	public void testisExistUserBusinessCategory_Employee() {
		System.out.println("test getBusinessCategory_Employee called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/BusinessCategory_Employee/{businessCategorId}/{employeeId}", 1, 1).accept(format).type(format);
		System.out.println(client.get(boolean.class));
	}

}
