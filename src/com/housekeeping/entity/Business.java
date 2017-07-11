package com.housekeeping.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文件名: Business.java</br>
 * 编写者: 刘斌</br>
 * 编写日期: 2014年7月26日</br>
 * 简要描述:业务</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@XmlRootElement(name = "Business")
public class Business implements Serializable {

	private static final long serialVersionUID = -474298012838488195L;

	private int id;

	/**
	 * 模块Id外键
	 */
	private int businessCategoryId;

	/**
	 * 服务名称
	 */
	private String name;

	/**
	 * 图片路径
	 */
	private String imagePath;

	/**
	 * 备注
	 */
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBusinessCategoryId() {
		return businessCategoryId;
	}

	public void setBusinessCategoryId(int businessCategoryId) {
		this.businessCategoryId = businessCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
