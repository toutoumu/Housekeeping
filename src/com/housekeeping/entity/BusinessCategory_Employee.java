package com.housekeeping.entity;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.pk.BusinessCategory_Employee_PK;

@XmlRootElement(name = "BusinessCategory_Employee")
public class BusinessCategory_Employee {
	private BusinessCategory_Employee_PK pk;

	/**
	 * @return the pk
	 */
	public BusinessCategory_Employee_PK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(BusinessCategory_Employee_PK pk) {
		this.pk = pk;
	}

}
