package com.housekeeping.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 文件名: Photo.java
 * 编写者: toutoumu
 * 编写日期: 2014年10月28日
 * 简要描述: 图片
 * 组件列表:
 * ********************  修改日志  **********************************
 * 修改人：           		  修改日期：
 * 修改内容：
 *
 */

@XmlRootElement(name = "Photo")
public class Photo {
	private int id;

	private String url;

	/**
	 * 图片类型 ，保留作扩张用
	 */
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
