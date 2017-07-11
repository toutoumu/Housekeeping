package com.housekeeping.service.impl;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IManager_MenuDao;
import com.housekeeping.entity.Manager_Menu;
import com.housekeeping.entity.pk.Manager_Menu_PK;
import com.housekeeping.service.IManager_MenuService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class Manager_MenuService implements IManager_MenuService {
	private IManager_MenuDao<Manager_Menu> manager_MenuDao;

	public void setManager_MenuDao(IManager_MenuDao<Manager_Menu> manager_MenuDao) {
		this.manager_MenuDao = manager_MenuDao;
	}

	@Override
	public Response addManager_Menu(Manager_Menu manager_Menu) {
		if (manager_Menu != null) {
			manager_MenuDao.save(manager_Menu);
			return Response.ok(manager_Menu).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteManager_Menu(int menuId, int managerId) {
		if (menuId != 0 && managerId != 0) {
			Manager_Menu_PK pk = new Manager_Menu_PK();
			pk.setManagerId(managerId);
			pk.setMenuId(menuId);
			Manager_Menu mm = manager_MenuDao.get(pk);
			if (mm != null) {
				manager_MenuDao.remove(mm);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("管理员-菜单关系不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除管理员-菜单关系失败");
	}

	@Override
	public Manager_Menu getManager_Menu(int menuId, int managerId) {
		if (menuId != 0 && managerId != 0) {
			Manager_Menu_PK pk = new Manager_Menu_PK();
			pk.setMenuId(menuId);
			pk.setManagerId(managerId);
			return manager_MenuDao.get(pk);
		}
		throw ServiceErrorBuilder.badRequestError("查询管理员-菜单关系时主键不能为空");
	}

	@Override
	public boolean IsExist(Manager_Menu_PK pk) {
		if (pk != null && pk.getManagerId() != 0 && pk.getMenuId() != 0) {
			return manager_MenuDao.IsExist(pk);
		}
		throw ServiceErrorBuilder.badRequestError("查询管理员-菜单关系是否存在时主键不能为空");
	}

}
