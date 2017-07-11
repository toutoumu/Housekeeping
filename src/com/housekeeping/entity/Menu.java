package com.housekeeping.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Menu")
public class Menu {

	private int id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 节点类型
	 */
	private int type;

	/**
	 * url路径
	 */
	private String url;

	/**
	 * 父节点id
	 */
	private int parent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the parent
	 */
	public int getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(int parent) {
		this.parent = parent;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
