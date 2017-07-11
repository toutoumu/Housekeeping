package com.housekeeping.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.housekeeping.utils.DateAdapter;

/**
 * 文件名: Captcha.java</br>
 * 编写者: toutoumu</br>
 * 编写日期: 2014年7月25日</br>
 * 简要描述: 验证码</br>
 * 组件列表:</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@XmlRootElement(name = "Captcha")
public class Captcha {

	private String phoneNumber;

	private int code;

	private String message;

	private Date createTime;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	};
}
