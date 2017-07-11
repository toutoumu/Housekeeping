package com.housekeeping.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文件名: Employee.java</br>
 * 编写者: 刘斌</br>
 * 编写日期: 2014年7月26日</br>
 * 简要描述:员工（阿姨/司机等等）</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@XmlRootElement(name = "Employee")
public class Employee {
	/*
	 * id， 星星评分（平均分）、距离（坐标）、服务次数、服务区域，
				  照片、名字、籍贯、身份证，相关证件，相关证件号码、手机号码，
				   服务分类id（阿姨/司机/取送人员），工作经验（驾龄），
				   亲友信息，亲友电话，亲友姓名，特长；备注；推荐值，工作状态（休假、空闲）。
	 */
	private int id;

	/**
	 * 服务分类id
	 */
	private int businessCategoryId;

	/**
	 * 星星评分
	 */
	private double grade;

	/**
	 * 年龄
	 */
	private int age;

	/**
	 * 坐标
	 */
	private String coordinat;

	/**
	 * 服务次数
	 */
	private int serviceTimes;

	/**
	 * 服务区域
	 */
	private String serviceArea;

	/**
	 * 照片路径
	 */
	private String photo;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 籍贯
	 */
	private String nativeSlace;

	/**
	 * 身份证
	 */
	private String idCard;

	/**
	 * 证件种类
	 */
	private String credentialsCategory;

	/**
	 * 证件号码
	 */
	private String credentialsNumber;

	/**
	 * 手机号码
	 */
	private String phoneNumber;

	/**
	 * 工作经验
	 */
	private int workExperience;

	//亲友信息，亲友电话，亲友姓名，特长；备注；推荐值，工作状态（休假、空闲）
	/**
	 * 亲友姓名
	 */
	private String relationName;

	/**
	 * 亲友电话
	 */
	private String relationPhoneNumber;

	/**
	 * 特长
	 */
	private String speciality;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 推荐值(排序)
	 */
	private int rank;

	/**
	 * 工作状态
	 */
	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCoordinat() {
		return coordinat;
	}

	public void setCoordinat(String coordinat) {
		this.coordinat = coordinat;
	}

	public int getServiceTimes() {
		return serviceTimes;
	}

	public void setServiceTimes(int serviceTimes) {
		this.serviceTimes = serviceTimes;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNativeSlace() {
		return nativeSlace;
	}

	public void setNativeSlace(String nativeSlace) {
		this.nativeSlace = nativeSlace;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCredentialsCategory() {
		return credentialsCategory;
	}

	public void setCredentialsCategory(String credentialsCategory) {
		this.credentialsCategory = credentialsCategory;
	}

	public String getCredentialsNumber() {
		return credentialsNumber;
	}

	public void setCredentialsNumber(String credentialsNumber) {
		this.credentialsNumber = credentialsNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getBusinessCategoryId() {
		return businessCategoryId;
	}

	public void setBusinessCategoryId(int businessCategoryId) {
		this.businessCategoryId = businessCategoryId;
	}

	public int getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(int workExperience) {
		this.workExperience = workExperience;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getRelationPhoneNumber() {
		return relationPhoneNumber;
	}

	public void setRelationPhoneNumber(String relationPhoneNumber) {
		this.relationPhoneNumber = relationPhoneNumber;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
