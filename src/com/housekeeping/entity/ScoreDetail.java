package com.housekeeping.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.housekeeping.utils.DateAdapter;

/**
 * 文件名: ScoreDetail.java
 * 编写者: toutoumu
 * 编写日期: 2014年10月16日
 * 简要描述:充值记录
 * 组件列表:
 * ********************  修改日志  **********************************
 * 修改人：           		  修改日期：
 * 修改内容：
 * 
 */
@XmlRootElement(name = "ScoreDetail")
public class ScoreDetail {

	/**
	 * 主键
	 */
	private int id;

	/**
	 * 外键
	 */
	private int userId;

	/**
	 * 描述：如注册积分
	 */
	private String description;

	/**
	 * 有效期
	 */
	private Date time;

	/**
	 * 积分
	 */
	private int score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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
}
