package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IMessageDao;

public class MessageDao<T> extends BaseDao<T, Serializable> implements IMessageDao<T> {

	public MessageDao(Class<T> type) {
		super(type);
	}

	@Override
	public List<T> getUnNotifyMessage() {
		Date startTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		Date endTime = calendar.getTime();
		String hql = "from Message as tr where tr.time >= ?  and tr.time <=? and tr.isFinish =?";
		return (List<T>) find(hql, startTime, endTime, false);
	}
}
