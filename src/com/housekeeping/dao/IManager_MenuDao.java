package com.housekeeping.dao;

import java.awt.Menu;
import java.io.Serializable;

import com.hibernate.dao.base.IBaseDao;
import com.housekeeping.entity.pk.Manager_Menu_PK;
import com.mchange.v2.resourcepool.ResourcePool.Manager;

public interface IManager_MenuDao<T> extends IBaseDao<T, Serializable> {

	/**
	 * 查询管理员{@link Manager}和菜单{@link Menu}的关联关系是否存在
	 * @param pk {@link Manager_Menu_PK}
	 * @return
	 */
	boolean IsExist(Manager_Menu_PK pk);

}
