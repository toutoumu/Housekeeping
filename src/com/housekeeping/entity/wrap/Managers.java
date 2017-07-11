package com.housekeeping.entity.wrap;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.Manager;

@XmlRootElement(name = "Managers")
public class Managers {
	private List<Manager> managers;

	/**
	 * @return the managers
	 */
	public List<Manager> getManagers() {
		return managers;
	}

	/**
	 * @param managers the managers to set
	 */
	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}

}
