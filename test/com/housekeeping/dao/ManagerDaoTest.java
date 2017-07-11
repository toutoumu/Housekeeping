package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Manager;
import com.housekeeping.entity.wrap.Managers;

public class ManagerDaoTest extends TestCase {
	private IManagerDao<Manager> managerDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		managerDao = (IManagerDao<Manager>) ctx.getBean("managerDao");
	}

	public void testAddManager() {
		Manager manager = new Manager();
		manager.setUserName("镠珌");
		manager.setCategory(12);
		manager.setPassword("1241242143");
		int id = (int) managerDao.save(manager);
		System.out.println(id);
	}

	public void testUpdateManager() {
		Manager manager = new Manager();
		manager.setId(1);
		manager.setUserName("镠珌");
		manager.setCategory(12);
		manager.setPassword("1241242143");
		boolean b = managerDao.update(manager);
		System.out.println(b);
	}

	public void testGetManager() {
		Manager manager = null;
		manager = managerDao.get(1);
		if (manager != null) {
			System.out.println(manager.getPassword());
		}
	}

	public void testgetAuthorizedManagers() {
		Managers managers = new Managers();
		String hql = "select emp from Manager  emp , Manager_Menu  bus" + " where  emp.id = bus.pk.managerId and"
				+ "  bus.pk.menuId=?";
		List<Manager> managersList = (List<Manager>) managerDao.find(hql, 1);

	}

	public void testgetUnAuthorizedManagers() {
		Managers managers = new Managers();
		String hql = "from Manager ma where ma.id not in (select mm.pk.managerId from Manager_Menu mm where mm.pk.menuId=?)";
		List<Manager> managersList = (List<Manager>) managerDao.find(hql, 1);
	}

	public void testDelteManager() {

		System.out.println(managerDao.removeById(1));
	}
}
