package com.housekeeping.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.housekeeping.utils.DateAdapter;

/**
 * 文件名: Recharge.java</br>
 * 编写者: toutoumu</br>
 * 编写日期: 2014年9月4日</br>
 * 简要描述: 充值记录</br>
 * 组件列表:</br>
 * ********************  修改日志  **********************************
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@XmlRootElement(name = "Recharge")
public class Recharge {

	/**
	 * 主键,填写支付宝支付编号
	 */
	private String id;

	/**
	 * 用户ID
	 */
	private int userId;

	/**
	 * 充值金额
	 */
	private double amount;

	/**
	 * 充值时间
	 */
	private Date time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
