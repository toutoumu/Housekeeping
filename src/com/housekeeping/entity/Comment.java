package com.housekeeping.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.housekeeping.utils.DateAdapter;

/**
 * 文件名: Comment.java</br>
 * 编写者: 刘斌</br>
 * 编写日期: 2014年7月26日</br>
 * 简要描述:评论</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@XmlRootElement(name = "Comment")
public class Comment {
	//	三：用户评论： id，服务人员id，用户id，星星评分，时间，评论内容。
	private int id;

	private int employeeId;

	/**
	 * 雇员名称
	 */
	private String employeeName;

	/**
	 * 用户id
	 */
	private int userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 评分	
	 */
	private int grade;

	/**
	 * 评论时间
	 */
	private Date commentTime;

	/**
	 * 内容
	 */
	private String content;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * @return the commentTime
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getCommentTime() {
		return commentTime;
	}

	/**
	 * @param commentTime the commentTime to set
	 */
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
