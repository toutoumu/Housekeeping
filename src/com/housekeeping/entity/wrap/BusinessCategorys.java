package com.housekeeping.entity.wrap;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.BusinessCategory;

@XmlRootElement(name = "BusinessCategorys")
public class BusinessCategorys {
	private List<BusinessCategory> businessCategories;

	/**
	 * @return the businessCategories
	 */
	public List<BusinessCategory> getBusinessCategories() {
		return businessCategories;
	}

	/**
	 * @param businessCategories the businessCategories to set
	 */
	public void setBusinessCategories(List<BusinessCategory> businessCategories) {
		this.businessCategories = businessCategories;
	}

}
