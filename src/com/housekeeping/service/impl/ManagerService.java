package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IManagerDao;
import com.housekeeping.entity.Manager;
import com.housekeeping.entity.wrap.Managers;
import com.housekeeping.service.IManagerService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class ManagerService implements IManagerService {

	private IManagerDao<Manager> managerDao;

	public void setManagerDao(IManagerDao<Manager> managerDao) {
		this.managerDao = managerDao;
	}

	@Override
	public Response addManager(Manager manager) {
		if (manager != null) {
			int id = (int) managerDao.save(manager);
			manager.setId(id);
			return Response.ok(manager).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteManager(int id) {
		if (id != 0) {
			Manager manager = managerDao.get(id);
			if (manager != null) {
				managerDao.remove(manager);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("管理员不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除管理员时主键不能为空");
	}

	@Override
	public Response updateManager(Manager manager) {
		if (manager != null && manager.getId() != 0) {
			if (managerDao.update(manager)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新管理员时主键不能为空");
	}

	@Override
	public Manager getManager(int id) {
		if (id != 0) {
			return managerDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询管理员时主键不能为空");
	}

	@Override
	public Response login(String userName, String password) {
		if (userName == null || password == null) {
			throw ServiceErrorBuilder.badRequestError("用户名或密码错误");
		}
		String sql = " from   Manager m where m.userName =? and m.password =?";
		List<Manager> managers = (List<Manager>) managerDao.find(sql, userName, password);
		if (managers != null && managers.size() > 0) {
			return Response.ok("登陆成功").build();
		}
		throw ServiceErrorBuilder.badRequestError("用户名或密码错误");
	}

	@Override
	public Manager getManagerByUserName(String userName) {
		if (StringUtil.strIsEmpty(userName)) {
			throw ServiceErrorBuilder.badRequestError("查询管理员时账号不能为空");
		}
		return managerDao.findUniqueBy("userName", userName);
	}

	@Override
	public Managers getAll() {
		List<Manager> list = managerDao.getAll();
		if (list != null && list.size() > 0) {
			Managers managers = new Managers();
			managers.setManagers(list);
			return managers;
		}
		return null;
	}

	@Override
	public Managers getAuthorizedManagers(int menuId) {
		if (menuId != 0) {
			Managers managers = new Managers();
			String hql = "select emp from Manager  emp , Manager_Menu  bus" + " where  emp.id = bus.pk.managerId and"
					+ "  bus.pk.menuId=?";
			List<Manager> managersList = (List<Manager>) managerDao.find(hql, menuId);
			if (managersList == null) {
				return null;
			}
			managers.setManagers(managersList);
			return managers;
		}
		throw ServiceErrorBuilder.badRequestError("根据菜单ID查询管理员时菜单ID不能为空");
	}

	@Override
	public Managers getUnAuthorizedManagers(int menuId) {
		if (menuId != 0) {
			Managers managers = new Managers();
			String hql = "from Manager ma where ma.id not in (select mm.pk.managerId from Manager_Menu mm where mm.pk.menuId=?)";
			List<Manager> managersList = (List<Manager>) managerDao.find(hql, menuId);
			if (managersList == null) {
				return null;
			}
			managers.setManagers(managersList);
			return managers;
		}
		throw ServiceErrorBuilder.badRequestError("根据菜单ID查询管理员时菜单ID不能为空");
	}
}
