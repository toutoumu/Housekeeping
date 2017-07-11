package com.housekeeping.entity.pk;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Manager_Menu_PK")
public class Manager_Menu_PK implements Serializable {

	private static final long serialVersionUID = -6157760824788082524L;

	private int menuId;

	private int managerId;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

}
