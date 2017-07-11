package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Message;
import com.housekeeping.entity.wrap.Messages;

public class MessageServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/MessageService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddMessage() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddMessage called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Message").accept(format).type(format);
		Message message = new Message();
		message.setParameters("asdfkjalsdkf");
		message.setOrderNumber("asdf");
		Message responseMessage = client.post(message, Message.class);
		System.out.println(responseMessage.getId());
	}

	public void testUpdateMessage() {
		System.out.println("test updateMessage called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Message").accept(format).type(format);
		Message message = new Message();
		message.setId(5);
		message.setParameters("asdfkjalsdkf");
		message.setOrderNumber("asdf");
		Response responseMessage = client.put(message, Response.class);
		System.out.println(responseMessage.getStatus());
	}

	public void testGetMessage() {
		System.out.println("test getMessage called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Message/{id}", 5).accept(format).type(format);
		Message responseMessage = client.get(Message.class);
		if (responseMessage != null) {
			System.out.println(responseMessage.getOrderNumber());
		}
	}

	public void testGetMessagesByCategoryId() {
		System.out.println("test getMessages called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Message/Messages/{id}", "asdf").accept(format).type(format);

		Messages Messages = client.get(Messages.class);
		System.out.println(Messages == null);
		if (Messages.getMessages() != null) {
			for (Message Message : Messages.getMessages()) {
				System.out.println(Message.getParameters());
			}
		}
	}

	public void testDeleteMessage() {

		System.out.println("test deleteMessage called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Message/{id}", 1).accept(format).type(format);

		Response response = client.delete();
		System.out.println(response.getStatus());

	}

	public void testUpdateDefault() {

		System.out.println("test deleteMessage called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Message/UpdateDefault/{id}", 2).accept(format).type(format);

		Response response = client.get();
		System.out.println(response.getStatus());

	}

}
