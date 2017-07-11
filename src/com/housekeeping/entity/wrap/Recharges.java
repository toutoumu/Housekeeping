package com.housekeeping.entity.wrap;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.Recharge;

@XmlRootElement(name = "Recharges")
public class Recharges {
	private List<Recharge> recharges;

	public List<Recharge> getRecharges() {
		return recharges;
	}

	public void setRecharges(List<Recharge> recharges) {
		this.recharges = recharges;
	}
}
