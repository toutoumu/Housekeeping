package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IMenuDao;
import com.housekeeping.entity.Menu;
import com.housekeeping.entity.wrap.Menus;
import com.housekeeping.service.IMenuService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class MenuService implements IMenuService {

	private IMenuDao<Menu> menuDao;

	public void setMenuDao(IMenuDao<Menu> menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public Response addMenu(Menu menu) {
		if (menu != null) {
			int id = (int) menuDao.save(menu);
			menu.setId(id);
			return Response.ok(menu).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加菜单时菜单不能为空");
	}

	@Override
	public Response deleteMenu(int id) {
		if (id != 0) {
			Menu menu = menuDao.get(id);
			if (menu != null) {
				menuDao.remove(menu);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("菜单不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除菜单时主键不能为空");
	}

	@Override
	public Response updateMenu(Menu menu) {
		if (menu != null && menu.getId() != 0) {
			if (menuDao.update(menu)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新菜单时主键不能为空");
	}

	@Override
	public Menu getMenu(int id) {
		if (id != 0) {
			return menuDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询菜单时主键不能为空");
	}

	@Override
	public Menus getAuthorizedMenus(int managerId) {
		if (managerId != 0) {
			String hql = "select emp from Menu  emp , Manager_Menu  bus" + " where  emp.id = bus.pk.menuId and"
					+ "  bus.pk.managerId=?";
			List<Menu> menus = (List<Menu>) menuDao.find(hql, managerId);
			if (menus == null) {
				return null;
			}
			Menus menus2 = new Menus();
			menus2.setMenus(menus);
			return menus2;
		}
		throw ServiceErrorBuilder.badRequestError("根据管理员查询菜单时管理员ID不能为空");
	}

	@Override
	public Menus getMenues() {
		List<Menu> list = menuDao.getAll();
		if (list != null) {
			Menus menus = new Menus();
			menus.setMenus(list);
			return menus;
		}
		return null;
	}

	@Override
	public Menus getUnAuthorizedMenus(int managerId) {
		if (managerId != 0) {
			String hql = "from Menu menu where menu.id not in ( select mm.pk.menuId from Manager_Menu mm where mm.pk.managerId = ?)";
			List<Menu> menus = (List<Menu>) menuDao.find(hql, managerId);
			if (menus == null || menus.size() == 0) {
				return null;
			}
			Menus menus2 = new Menus();
			menus2.setMenus(menus);
			return menus2;
		}
		throw ServiceErrorBuilder.badRequestError("根据管理员查询菜单时管理员ID不能为空");
	}

	@Override
	public Menus queryMenus(Menu menu) {
		StringBuilder hql = new StringBuilder("from Menu me where 1=1 ");
		if (menu.getType() != 0) {
			hql.append(" and ");
			hql.append("me.type = ");
			hql.append(menu.getType());
		}
		if (menu.getParent() != 0) {
			hql.append(" and ");
			hql.append("me.parent = ");
			hql.append(menu.getParent());
		}
		if (!StringUtil.strIsEmpty(menu.getTitle())) {
			hql.append(" and ");
			hql.append("me.title like '%");
			hql.append(menu.getTitle());
			hql.append("%'");
		}
		List<Menu> menuList = (List<Menu>) menuDao.find(hql.toString(), null);
		Menus menus = new Menus();
		menus.setMenus(menuList);
		return menus;

	}
}
