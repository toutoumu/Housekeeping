package com.housekeeping.service;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.User;
import com.housekeeping.entity.wrap.Users;

public class UserServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/UserService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddUser() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddUser called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/User").accept(format).type(format);
		User requestuUser = new User();
		//requestuUser.setAddress("adsfasdf");
		requestuUser.setCity("sz");
		requestuUser.setPhoneNumber("1241242134");
		requestuUser.setCreateTime(new Date());
		User responseUser = client.post(requestuUser, User.class);
		System.out.println(responseUser.getId());
	}

	public void testUpdateUser() {
		System.out.println("test updateUser called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/User").accept(format).type(format);
		User requestuUser = new User();
		requestuUser.setId(2);
		//requestuUser.setAddress("adsfasdf");
		requestuUser.setCity("sz");
		requestuUser.setUserName(URL);
		requestuUser.setPhoneNumber("1241242134");
		Response responseUser = client.put(requestuUser, Response.class);
		System.out.println(responseUser.getStatus());
	}

	public void testGetUser() {
		System.out.println("test getUser called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/User/{id}/{phoneNumber}", 0, "1241242134").accept(format).type(format);
		User responseUser = client.get(User.class);
		if (responseUser != null) {
			System.out.println(responseUser.getUserName());
		}
	}

	public void testGetUsers() {
		System.out.println("test getUsers called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/User/users").accept(format).type(format);
		User requestuUser = new User();
		requestuUser.setId(2);
		//requestuUser.setAddress("adsfasdf");
		requestuUser.setCity("sz");
		requestuUser.setUserName(URL);
		requestuUser.setPhoneNumber("1241242134");
		Users responseUser = client.put(requestuUser, Users.class);
		System.out.println(responseUser.getUsers() == null);
		if (responseUser.getUsers() != null) {
			System.out.println(responseUser.getUsers().get(0).getUserName());
		}
	}

	public void testisExistUser() {

		System.out.println("test updateUser called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/User/isExist").accept(format).type(format);
		User requestuUser = new User();
		requestuUser.setId(2);
		//requestuUser.setAddress("adsfasdf");
		requestuUser.setCity("sz");
		requestuUser.setUserName(URL);
		requestuUser.setPhoneNumber("1241242134");

		boolean responseUser = client.put(requestuUser, boolean.class);
		System.out.println(responseUser);

	}
}
