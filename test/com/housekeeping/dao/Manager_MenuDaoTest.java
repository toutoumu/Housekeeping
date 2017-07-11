package com.housekeeping.dao;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Manager_Menu;
import com.housekeeping.entity.pk.Manager_Menu_PK;

public class Manager_MenuDaoTest extends TestCase {
	private IManager_MenuDao<Manager_Menu> manager_MenuDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		manager_MenuDao = (IManager_MenuDao<Manager_Menu>) ctx.getBean("manager_MenuDao");
	}

	public void testAdd() {
		Manager_Menu_PK pk = new Manager_Menu_PK();
		pk.setManagerId(12);
		pk.setMenuId(12);
		Manager_Menu menu = new Manager_Menu();
		menu.setPk(pk);

		System.out.println(manager_MenuDao.save(menu));
	}

	public void testDelete() {
		Manager_Menu_PK pk = new Manager_Menu_PK();
		pk.setManagerId(12);
		pk.setMenuId(12);
		manager_MenuDao.removeById(pk);
	}

	public void testGet() {
		Manager_Menu_PK pk = new Manager_Menu_PK();
		pk.setManagerId(12);
		pk.setMenuId(12);
		Manager_Menu manager_Menu = manager_MenuDao.get(pk);
		if (manager_Menu != null) {
			System.out.println(manager_Menu.getPk().getManagerId());
		}
	}

	public void testIsExist() {
		Manager_Menu_PK pk = new Manager_Menu_PK();
		pk.setManagerId(12);
		pk.setMenuId(12);
		System.err.println(manager_MenuDao.IsExist(pk));
	}
}
