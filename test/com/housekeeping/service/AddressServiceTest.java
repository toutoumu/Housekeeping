package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Address;
import com.housekeeping.entity.wrap.Addresss;

public class AddressServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/AddressService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddAddress() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddAddress called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Address").accept(format).type(format);
		Address address = new Address();
		address.setAddress("地址啊");
		Address responseAddress = client.post(address, Address.class);
		System.out.println(responseAddress.getId());
	}

	public void testUpdateAddress() {
		System.out.println("test updateAddress called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Address").accept(format).type(format);
		Address address = new Address();
		address.setId(2);
		address.setDefault(true);
		address.setUserId(1);
		address.setAddress("地址啊啊啊啊");
		Response responseAddress = client.put(address, Response.class);
		System.out.println(responseAddress.getStatus());
	}

	public void testGetAddress() {
		System.out.println("test getAddress called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Address/{id}", 2).accept(format).type(format);
		Address responseAddress = client.get(Address.class);
		if (responseAddress != null) {
			System.out.println(responseAddress.getAddress());
		}
	}

	public void testGetAddresssByCategoryId() {
		System.out.println("test getAddresss called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Address/Addresss/{id}", 1).accept(format).type(format);

		Addresss Addresss = client.get(Addresss.class);
		System.out.println(Addresss == null);
		if (Addresss.getAddresses() != null) {
			for (Address Address : Addresss.getAddresses()) {
				System.out.println(Address.getAddress());
			}
		}
	}

	public void testDeleteAddress() {
		System.out.println("test deleteAddress called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Address/{id}", 1).accept(format).type(format);		
		Response response = client.delete();
		System.out.println(response.getStatus());

	}

	public void testUpdateDefault() {

		System.out.println("test deleteAddress called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Address/UpdateDefault/{id}", 2).accept(format).type(format);

		Response response = client.get();
		System.out.println(response.getStatus());

	}

}
