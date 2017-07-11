package com.housekeeping.entity.pk;

import java.io.Serializable;

public class BusinessCategory_Employee_PK implements Serializable {
	
	private static final long serialVersionUID = 4112127669190687965L;

	/**
	 * 业务分类id
	 */
	private int businessCategoryId;

	/**
	 * 雇员id
	 */
	private int employeeId;

	public int getBusinessCategoryId() {
		return businessCategoryId;
	}

	public void setBusinessCategoryId(int businessCategoryId) {
		this.businessCategoryId = businessCategoryId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}
