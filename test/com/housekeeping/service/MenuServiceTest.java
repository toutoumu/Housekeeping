package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Menu;
import com.housekeeping.entity.wrap.Menus;

public class MenuServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/MenuService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddMenu() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddMenu called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Menu").accept(format).type(format);
		Menu menu = new Menu();
		menu.setTitle("biaoti");
		menu.setUrl("/http");
		Menu responseMenu = client.post(menu, Menu.class);
		System.out.println(responseMenu.getId());
	}

	public void testUpdateMenu() {
		System.out.println("test updateMenu called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Menu").accept(format).type(format);
		Menu menu = new Menu();
		menu.setId(1);
		menu.setTitle("中文标题");
		menu.setUrl("/http");
		Response responseMenu = client.put(menu, Response.class);
		System.out.println(responseMenu.getStatus());
	}

	public void testGetMenu() {
		System.out.println("test getMenu called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Menu/{id}", 1).accept(format).type(format);
		Menu responseMenu = client.get(Menu.class);
		System.out.println(responseMenu.getTitle());
	}

	public void testGetAuthorizedMenus() {
		System.out.println("test getMenus called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Menu/Authorized/{id}", 5).accept(format).type(format);

		Menus responseMenu = client.get(Menus.class);
		System.out.println(responseMenu.getMenus() == null);
		if (responseMenu.getMenus() != null) {
			System.out.println(responseMenu.getMenus().get(0).getTitle());
		}
	}

	public void testGetUnAuthorizedMenus() {
		System.out.println("test getMenus called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Menu/UnAuthorized/{id}", 5).accept(format).type(format);

		Menus responseMenu = client.get(Menus.class);
		System.out.println(responseMenu.getMenus() == null);
		if (responseMenu.getMenus() != null) {
			System.out.println(responseMenu.getMenus().get(0).getTitle());
		}
	}

	public void testDelete() {

		System.out.println("test updateMenu called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Menu/{id}", 1).accept(format).type(format);
		Menu menu = new Menu();
		menu.setId(1);
		menu.setTitle("biaoti");
		menu.setUrl("/http");
		Response responseMenu = client.delete();
		System.out.println(responseMenu.getStatus());

	}
}
