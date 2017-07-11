package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Menu;

public class MenuDaoTest extends TestCase {
	private IMenuDao<Menu> menuDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		menuDao = (IMenuDao<Menu>) ctx.getBean("menuDao");
	}

	public void testsss() {
		String hql = "from Menu menu where menu.id not in ( select mm.pk.menuId from Manager_Menu mm where mm.pk.managerId = ?)";

		List<Menu> menus = (List<Menu>) menuDao.find(hql, 1);
	}

	public void testAddMenu() {
		Menu menu = new Menu();
		menu.setTitle("biaoti");
		menu.setUrl("http://");
		int id = (int) menuDao.save(menu);
		System.out.println(id);
	}

	public void testUpdateMenu() {
		Menu menu = new Menu();
		menu.setId(1);
		menu.setTitle("biaoti");
		menu.setUrl("http://www.baidu.com");
		boolean b = menuDao.update(menu);
		System.out.println(b);
	}

	public void testGetMenu() {
		Menu menu = null;
		menu = menuDao.get(1);
		if (menu != null) {
			System.out.println(menu.getTitle());
		}
	}

	public void testDelteMenu() {

		System.out.println(menuDao.removeById(1));
	}

}
