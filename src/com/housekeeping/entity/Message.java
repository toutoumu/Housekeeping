package com.housekeeping.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.housekeeping.utils.DateAdapter;

@XmlRootElement(name = "Message")
public class Message {
	/**
	 * 消息Id
	 */
	private int id;

	/**
	 * 订单编号
	 */
	private String orderNumber;

	/**
	 * 消息模版Id
	 */
	private String templateId;

	/**
	 * 消息参数,短信(短信参数以,分割),推送消息(消息内容)
	 */
	private String parameters;

	/**
	 * 电话号码,推送时候的tag
	 */
	private String phoneNumber;

	/**
	 * 发送时间
	 */
	private Date time;

	/**
	 * 消息类型，1:短信/2:推送
	 */
	private int messageType;

	/**
	 * 消息是否发送完成
	 */
	private boolean isFinish;

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
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the parameters
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the time
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the messageType
	 */
	public int getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the isFinish
	 */
	public boolean isFinish() {
		return isFinish;
	}

	/**
	 * @param isFinish the isFinish to set
	 */
	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
}
