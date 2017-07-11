package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Manager;
import com.housekeeping.entity.wrap.Managers;

public class ManagerServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/ManagerService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddManager() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddManager called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Manager").accept(format).type(format);
		Manager manager = new Manager();
		manager.setCategory(1);
		manager.setUserName("123");
		Manager responseManager = client.post(manager, Manager.class);
		System.out.println(responseManager.getId());
	}

	public void testUpdateManager() {
		System.out.println("test updateManager called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Manager").accept(format).type(format);
		Manager manager = new Manager();
		manager.setId(1);
		manager.setCategory(1);
		manager.setUserName("123333");
		Response responseManager = client.put(manager, Response.class);
		System.out.println(responseManager.getStatus());
	}

	public void testGetManager() {
		System.out.println("test getManager called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Manager/{id}", 1).accept(format).type(format);
		Manager responseManager = client.get(Manager.class);
		System.out.println(responseManager.getUserName());
	}

	public void testGetAuthorizedManagers() {
		System.out.println("test getManagers called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Manager/Authorized/{id}", 1).accept(format).type(format);

		Managers responseManager = client.get(Managers.class);
		System.out.println(responseManager.getManagers() == null);
		if (responseManager.getManagers() != null) {
			System.out.println(responseManager.getManagers().get(0).getUserName());
		}
	}

	public void testGetUnAuthorizedManagers() {
		System.out.println("test getManagers called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Manager/UnAuthorized/{id}", 1).accept(format).type(format);

		Managers responseManager = client.get(Managers.class);
		System.out.println(responseManager.getManagers() == null);
		if (responseManager.getManagers() != null) {
			System.out.println(responseManager.getManagers().get(0).getUserName());
		}
	}

	public void testisDeleteManager() {

		System.out.println("test updateManager called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Manager/{id}", 1).accept(format).type(format);

		Response responseManager = client.delete();
		System.out.println(responseManager.getStatus());

	}
}
