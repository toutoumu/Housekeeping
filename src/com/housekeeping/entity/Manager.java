package com.housekeeping.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Manager")
public class Manager implements Serializable {

	private static final long serialVersionUID = -5436370802294028847L;

	//十三：管理员：id，管理员账号，管理员密码，管理员类别(只有一个超级管理员）
	private int id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码md5
	 */
	private String password;

	/**
	 * 管理员类别
	 */
	private int category;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
}
