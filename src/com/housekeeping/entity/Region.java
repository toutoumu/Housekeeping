package com.housekeeping.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Region")
public class Region {
	private int id;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 父节点ID
	 */
	private int parentId;

	/**
	 * 节点类型
	 */
	private int regionType;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the regionType
	 */
	public int getRegionType() {
		return regionType;
	}

	/**
	 * @param regionType the regionType to set
	 */
	public void setRegionType(int regionType) {
		this.regionType = regionType;
	}
}
