package com.housekeeping.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.housekeeping.utils.DateAdapter;

@XmlRootElement(name = "User")
public class User {
	//九：客户：id，手机号，地址，城市，用户名、密码,等级、消费次数、余额。
	private int id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 地址
	 */
	//private String address;

	/**
	 * 电话
	 */
	private String phoneNumber;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 等级
	 */
	private int grade;

	/**
	 * 消费次数
	 */
	private int consumeTimes;

	/**
	 * 余额
	 */
	private double balance;

	/**
	 * 积分
	 */
	private int score;

	/**
	 * 创建日期
	 */
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}*/

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getConsumeTimes() {
		return consumeTimes;
	}

	public void setConsumeTimes(int consumeTimes) {
		this.consumeTimes = consumeTimes;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
