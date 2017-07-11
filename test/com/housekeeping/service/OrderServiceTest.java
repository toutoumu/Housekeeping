package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Order;
import com.housekeeping.entity.wrap.Orders;

public class OrderServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/OrderService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddOrder() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddOrder called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Order").accept(format).type(format);
		Order menu = new Order();
		menu.setOrderNumber("zhangdanbianhao");
		menu.setAddress("adfasdfasdf");
		Order responseOrder = client.post(menu, Order.class);
		System.out.println(responseOrder.getAddress());
	}

	public void testSearch() {
		System.out.println("test AddOrder called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Order/search/{content}", "深圳").accept(format).type(format);
		Orders orders = client.get(Orders.class);
		if (orders != null) {
			for (Order order : orders.getOrders()) {
				System.out.println(order.getAddress());
			}
		}
	}

	public void testUpdateOrder() {
		System.out.println("test updateOrder called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Order").accept(format).type(format);
		Order menu = new Order();
		menu.setOrderNumber("zhangdanbianhao");
		menu.setAddress("aaaaaaaaaaaaaaaaaaa");
		Response responseOrder = client.put(menu, Response.class);
		System.out.println(responseOrder.getStatus());
	}

	public void testGetOrder() {
		System.out.println("test getOrder called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Order/{id}", "zhangdanbianhao").accept(format).type(format);
		Order responseOrder = client.get(Order.class);
		if (responseOrder != null) {
			System.out.println(responseOrder.getAddress());
		}
	}

	public void testGetOrders() {
		System.out.println("test getOrders called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Order/Orders/{id}", "1").accept(format).type(format);

		Orders responseOrder = client.get(Orders.class);
		System.out.println(responseOrder.getOrders() == null);
		if (responseOrder.getOrders() != null) {
			System.out.println(responseOrder.getOrders().get(0).getAddress());
		}
	}

	public void testDelete() {

		System.out.println("test updateOrder called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Order/{id}", "123333").accept(format).type(format);

		Response responseOrder = client.delete();
		System.out.println(responseOrder.getStatus());

	}
}
