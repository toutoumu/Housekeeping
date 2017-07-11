package com.housekeeping.entity;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.pk.Manager_Menu_PK;

@XmlRootElement(name = "Manager_Menu")
public class Manager_Menu {

	private Manager_Menu_PK pk;

	/**
	 * @return the pk
	 */
	public Manager_Menu_PK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Manager_Menu_PK pk) {
		this.pk = pk;
	}

}
