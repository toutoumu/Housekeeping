package com.housekeeping.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文件名: Business.java</br>
 * 编写者: 刘斌</br>
 * 编写日期: 2014年7月26日</br>
 * 简要描述:业务类别</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@XmlRootElement(name = "BusinessCategory")
public class BusinessCategory implements Serializable {

	private static final long serialVersionUID = 5000948303733456639L;

	private int id;

	/**
	 * 服务分类
	 */
	private String name;

	/**
	 * 是否显示
	 */
	private boolean visiblity;

	/**
	 * 是否默认分类
	 */
	private boolean isDefault;

	/**
	 * 图片路径
	 */
	private String imagePath;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否推送消息
	 */
	private boolean pushMessage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVisiblity() {
		return visiblity;
	}

	public void setVisiblity(boolean visiblity) {
		this.visiblity = visiblity;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isPushMessage() {
		return pushMessage;
	}

	public void setPushMessage(boolean pushMessage) {
		this.pushMessage = pushMessage;
	}

}
