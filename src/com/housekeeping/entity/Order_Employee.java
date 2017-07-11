package com.housekeeping.entity;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.pk.Order_Employee_PK;

@XmlRootElement(name = "Order_Employee")
public class Order_Employee {
	Order_Employee_PK pk;

	/**
	 * @return the pk
	 */
	public Order_Employee_PK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Order_Employee_PK pk) {
		this.pk = pk;
	}
}
