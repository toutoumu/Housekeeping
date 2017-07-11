package com.housekeeping.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.housekeeping.utils.DateAdapter;

/**
 * 文件名: Order.java</br>
 * 编写者: toutoumu</br>
 * 编写日期: 2014年7月28日</br>
 * 简要描述:订单</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@XmlRootElement(name = "Order")
public class Order {
	/**
	 * 外键：雇员Id
	 */
	private int employeeId;

	/**
	 * 雇员电话
	 */
	private String employeePhoneNumber;

	/**
	 * 雇员姓名
	 */
	private String employeeName;

	/*
	 *八：订单：订单编号，下单时间，工作时长，服务分类id，服务分类名称，服务时间，服务地址，
	客户id（外键），客户用户名（手机），客户电话，
	订单金额，订单状态(订单分类)（待确定，已确定，已完成，已取消），
	支付状态（（已支付，上门收款），
	备注，房屋大小 
	 */
	/**
	 * 订单编号
	 */
	private String orderNumber;

	/**
	 * 下单时间
	 */
	private Date orderTime;

	/**
	 * 工作时长
	 */
	private double workingTime;

	/**
	 * 服务分类id
	 */
	private int businessCategoryId;

	/**
	 * 服务分类名称
	 */
	private String businessCategoryName;

	/**
	 * 服务开始时间
	 */
	private Date startTime;

	/**
	 * 地址	
	 */
	private String address;

	/**
	 * 用户id
	 */
	private int userId;

	/*客户用户名（手机），客户电话，
	订单金额，订单状态(订单分类)（待确定，已确定，已完成，已取消），
	支付状态（（已支付，上门收款），
	备注，房屋大小 */
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 客户手机
	 */
	private String phoneNumber;

	/**
	 * 订单金额
	 */
	private double orderPrice;

	/**
	 * 订单状态   1.待确认；2.已确认；3.已完成 4.已取消
	 */
	private int orderState;

	/**
	 * 支付状态
	 */
	private int paymentState;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 面积
	 */
	private double area;

	/**
	 * 支付方式
	 */
	private int payway;

	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return the employeePhoneNumber
	 */
	public String getEmployeePhoneNumber() {
		return employeePhoneNumber;
	}

	/**
	 * @param employeePhoneNumber the employeePhoneNumber to set
	 */
	public void setEmployeePhoneNumber(String employeePhoneNumber) {
		this.employeePhoneNumber = employeePhoneNumber;
	}

	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getPayway() {
		return payway;
	}

	public void setPayway(int payway) {
		this.payway = payway;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public double getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(double workingTime) {
		this.workingTime = workingTime;
	}

	public int getBusinessCategoryId() {
		return businessCategoryId;
	}

	public void setBusinessCategoryId(int businessCategoryId) {
		this.businessCategoryId = businessCategoryId;
	}

	public String getBusinessCategoryName() {
		return businessCategoryName;
	}

	public void setBusinessCategoryName(String businessCategoryName) {
		this.businessCategoryName = businessCategoryName;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public int getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(int paymentState) {
		this.paymentState = paymentState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}
}
