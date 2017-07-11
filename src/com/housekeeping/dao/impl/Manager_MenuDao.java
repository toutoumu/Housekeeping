package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IManager_MenuDao;
import com.housekeeping.entity.pk.Manager_Menu_PK;

public class Manager_MenuDao<T> extends BaseDao<T, Serializable> implements IManager_MenuDao<T> {

	public Manager_MenuDao(Class<T> type) {
		super(type);
	}

	@Override
	public boolean IsExist(Manager_Menu_PK pk) {
		if (pk == null) {
			return false;
		}
		StringBuilder builder = new StringBuilder(
				"select count(*) from Manager_Menu as ma where ma.pk.menuId=? and ma.pk.managerId=?");
		List<?> ret = find(builder.toString(), pk.getMenuId(), pk.getManagerId());
		if (ret != null) { 
			return Integer.parseInt(ret.get(0).toString()) > 0;
		}
		return false;
	}

}
