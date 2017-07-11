package com.housekeeping.dao;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.IBaseDao;

public interface IMessageDao<T> extends IBaseDao<T, Serializable> {
	/**
	 * 获取没有发送通知的数据
	 * @return
	 */
	public List<T> getUnNotifyMessage();
}
