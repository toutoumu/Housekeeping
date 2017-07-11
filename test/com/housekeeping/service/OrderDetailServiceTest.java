package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.OrderDetail;
import com.housekeeping.entity.wrap.OrderDetails;

public class OrderDetailServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/OrderDetailService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddOrderDetail() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddOrderDetail called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/OrderDetail").accept(format).type(format);
		OrderDetail menu = new OrderDetail();
		menu.setOrderNumber("124");
		menu.setBusinessDetailId(1);
		menu.setBusinessId(2);
		OrderDetail responseOrderDetail = client.post(menu, OrderDetail.class);
		System.out.println(responseOrderDetail.getId());
	}

	public void testUpdateOrderDetail() {
		System.out.println("test updateOrderDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/OrderDetail").accept(format).type(format);
		OrderDetail menu = new OrderDetail();
		menu.setId(1);
		menu.setOrderNumber("124");
		menu.setBusinessDetailId(1);
		menu.setBusinessId(2);
		Response responseOrderDetail = client.put(menu, Response.class);
		System.out.println(responseOrderDetail.getStatus());
	}

	public void testGetOrderDetail() {
		System.out.println("test getOrderDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/OrderDetail/{id}", 1).accept(format).type(format);
		OrderDetail responseOrderDetail = client.get(OrderDetail.class);
		System.out.println(responseOrderDetail.getBusinessDetailId());
	}

	public void testGetOrderDetails() {
		System.out.println("test getOrderDetails called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/OrderDetail/OrderDetails/{id}", "124").accept(format).type(format);

		OrderDetails responseOrderDetail = client.get(OrderDetails.class);
		System.out.println(responseOrderDetail.getOrderDetails() == null);
		if (responseOrderDetail.getOrderDetails() != null) {
			System.out.println(responseOrderDetail.getOrderDetails().get(0).getBusinessDetailId());
		}
	}

	public void testDelete() {

		System.out.println("test updateOrderDetail called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/OrderDetail/{id}", 1).accept(format).type(format);

		Response responseOrderDetail = client.delete();
		System.out.println(responseOrderDetail.getStatus());

	}
}
