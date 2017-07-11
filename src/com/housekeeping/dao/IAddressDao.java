package com.housekeeping.dao;

import java.io.Serializable;

import com.hibernate.dao.base.IBaseDao;

public interface IAddressDao<T> extends IBaseDao<T, Serializable> {

	/**
	 * 将某个地址设置为默认
	 * @param id
	 * @return
	 */
	boolean updateDefault(int id);

}
