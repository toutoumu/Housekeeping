package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Recharge;
import com.housekeeping.entity.wrap.Recharges;

public class RechargeServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/RechargeService/";

	private String format = MediaType.APPLICATION_JSON;

	/**
	 * 添加充值记录
	 */
	public void testAddRecharge() {
		System.out.println("test AddRecharge called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Recharge").accept(format).type(format);
		Recharge recharge = new Recharge();
		recharge.setId("12315");
		recharge.setUserId(1);
		recharge.setAmount(2);
		Recharge responseRecharge = client.post(recharge, Recharge.class);
		System.out.println(responseRecharge.getId());
	}

	/**
	 * 根据主键查询充值记录
	 */
	public void testGetRecharge() {
		System.out.println("test getRecharge called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Recharge/{id}", "123").accept(format).type(format);
		Recharge responseRecharge = client.get(Recharge.class);
		System.out.println(responseRecharge.getAmount());
	}

	/**
	 * 根据用户ID查询充值记录
	 */
	public void testGetRecharges() {
		System.out.println("test getRecharges called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Recharge/RechargesByUserID/{id}", 1).accept(format).type(format);

		Recharges responseRecharge = client.get(Recharges.class);
		System.out.println(responseRecharge.getRecharges() == null);
		if (responseRecharge.getRecharges() != null) {
			System.out.println(responseRecharge.getRecharges().get(0).getAmount());
		}
	}

	/**
	 * 删除充值记录
	 */
	public void testDelete() {

		System.out.println("test updateRecharge called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Recharge/{id}", "123").accept(format).type(format);
		Response responseRecharge = client.delete();
		System.out.println(responseRecharge.getStatus());

	}
}
