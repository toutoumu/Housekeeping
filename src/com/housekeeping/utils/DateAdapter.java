package com.housekeeping.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Date unmarshal(String v) throws Exception {
		try {
			return new Date(dateFormat.parse(v).getTime());
		} catch (ParseException pe) {
			throw new WebApplicationException(pe);
		}
	}

	@Override
	public String marshal(Date v) throws Exception {
		return dateFormat.format(v);
		//throw new UnsupportedOperationException("Not supported yet."); 
		//To change body of generated methods, choose Tools | Templates.
	}

}