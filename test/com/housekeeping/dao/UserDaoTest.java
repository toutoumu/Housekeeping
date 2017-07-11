package com.housekeeping.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.User;

public class UserDaoTest extends TestCase {
	private IUserDao<User> userDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		userDao = (IUserDao<User>) ctx.getBean("userDao");
	}

	public void testFind1() {
		List<String> attrsList = new ArrayList<String>();
		attrsList.add("userName");
		attrsList.add("city");
		List<User> users = userDao.findBy(attrsList, new String[] { "userName", "深圳" }, new String[] { "userName",
				"city" }, null);
		//List<User> users = userDao.findBy("userName", "userName");
		if (users != null) {
			for (User user : users) {
				System.out.println(user.getUserName());
			}
		}
	}

	public void testFind() {
		List<String> attrsList = new ArrayList<String>();
		attrsList.add("userName");
		attrsList.add("city");
		List<User> users = userDao.findBy(attrsList, new String[] { "userName", "深圳" });
		//List<User> users = userDao.findBy("userName", "userName");
		if (users != null) {
			for (User user : users) {
				System.out.println(user.getUserName());
			}
		}
	}

	public void testAdd() {
		User user = new User();
		//user.setAddress("asdf");
		user.setCity("深圳");
		user.setPhoneNumber("15989348952");
		user.setUserName("userName");
		userDao.save(user);
	}

	public void testGetUser() {
		User user = userDao.getUser(1, "15989348952");
		if (user != null) {
			System.out.println(user.getPhoneNumber());
		}
	}

	public void testUpdateUser() {
		User user = new User();
		user.setId(1);
		//user.setAddress("asdf");
		user.setCity("深圳");
		user.setPhoneNumber("15989348952");
		user.setUserName("userName");
		System.out.println(userDao.update(user));
	}

	public void testGetUsers() {
		User user = new User();
		user.setId(1);
		//user.setAddress("asdf");
		user.setCity("深圳");
		user.setPhoneNumber("15989348952");
		user.setUserName("userName");
		user.setPassword(null);
		user.setScore(0);
		List<User> users = userDao.getUsers(user);
		if (users != null) {
			for (User u : users) {
				System.out.println(u.getUserName());
			}
		}
	}

	public void testIsExsist() {
		User user = new User();
		//user.setAddress("asdf");
		user.setCity("深圳");
		user.setPhoneNumber("15989348952");
		user.setUserName("userName");
		System.out.println(userDao.isExistUser(user));
	}
}
