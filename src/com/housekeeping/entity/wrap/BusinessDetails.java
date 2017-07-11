package com.housekeeping.entity.wrap;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.BusinessDetail;

@XmlRootElement(name = "BusinessDetails")
public class BusinessDetails {
	private List<BusinessDetail> details;

	/**
	 * @return the details
	 */
	public List<BusinessDetail> getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(List<BusinessDetail> details) {
		this.details = details;
	}

}
