package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Manager_Menu;
import com.housekeeping.entity.pk.Manager_Menu_PK;

public class Manager_MenuServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/Manager_MenuService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddManager_Menu() {

		System.out.println("test AddManager_Menu called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Manager_Menu").accept(format).type(format);
		Manager_Menu bEmployee = new Manager_Menu();
		Manager_Menu_PK pk = new Manager_Menu_PK();
		pk.setMenuId(2);
		pk.setManagerId(1);
		bEmployee.setPk(pk);

		Manager_Menu response = client.post(bEmployee, Manager_Menu.class);
		System.out.println(response.getPk().getManagerId());
	}

	public void testDeleteManager_Menu() {
		System.out.println("test updateManager_Menu called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Manager_Menu/{menuId}/{managerId}", 2, 1).accept(format).type(format);
		Response response = client.delete();
		System.out.println(response.getStatus());
	}

	public void testisgetUserManager_Menu() {
		System.out.println("test getManager_Menu called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Manager_Menu/{menuId}/{managerId}", 2, 1).accept(format).type(format);
		Manager_Menu manager_Menu = client.get(Manager_Menu.class);
		if (manager_Menu != null) {
			System.out.println(manager_Menu.getPk().getManagerId());

		}
	}

	public void testIsExist() {
		System.out.println("test getManager_Menu called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Manager_Menu").accept(format).type(format);
		Manager_Menu_PK pk = new Manager_Menu_PK();
		pk.setMenuId(1);
		pk.setManagerId(1);
		boolean manager_Menu = client.put(pk, boolean.class);
		System.out.println(manager_Menu);
	}

}
