package com.housekeeping.entity.wrap;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.Business;

@XmlRootElement(name = "Businesss")
public class Businesss {
	private List<Business> businesses;

	/**
	 * @return the businesses
	 */
	public List<Business> getBusinesses() {
		return businesses;
	}

	/**
	 * @param businesses the businesses to set
	 */
	public void setBusinesses(List<Business> businesses) {
		this.businesses = businesses;
	}
}
