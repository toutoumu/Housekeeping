package com.housekeeping.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OrderDetail")
public class OrderDetail {
	//十：订单明细：id，订单id，服务明细id，业务id，价格，面积/数量,单位。（上门取送费生成订单明细）
	private int id;

	/**
	 * 订单编号
	 */
	private String orderNumber;

	/**
	 * 业务明细id
	 */
	private int businessDetailId;

	/**
	 * 业务明细名称
	 */
	private String businessDetailName;

	/**
	 * 业务id
	 */
	private int businessId;

	/**
	 * 单价
	 */
	private double price;

	/**
	 * 数量
	 */
	private int count;

	/**
	 * 单位
	 */
	private String unit;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getBusinessDetailId() {
		return businessDetailId;
	}

	public void setBusinessDetailId(int businessDetailId) {
		this.businessDetailId = businessDetailId;
	}

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBusinessDetailName() {
		return businessDetailName;
	}

	public void setBusinessDetailName(String businessDetailName) {
		this.businessDetailName = businessDetailName;
	}

}
