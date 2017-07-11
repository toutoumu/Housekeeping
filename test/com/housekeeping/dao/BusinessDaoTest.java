package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Business;

public class BusinessDaoTest extends TestCase {
	private IBusinessDao<Business> businessDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		businessDao = (IBusinessDao<Business>) ctx.getBean("businessDao");
	}

	public void testAddBusiness() {
		Business business = new Business();
		business.setBusinessCategoryId(2);
		business.setName("打扫卫生");
		business.setRemark("我靠你也打扫卫生");
		int id = (int) businessDao.save(business);
		if (business != null) {
			System.out.println(business.getRemark());
		}
	}

	public void testUpdateBusiness() {
		Business business = new Business();
		business.setId(1);
		business.setBusinessCategoryId(2);
		business.setName("打扫卫生");
		business.setRemark("我就不能打扫卫生了吗?");
		boolean b = businessDao.update(business);
		System.out.println(b);
	}

	public void testGetBusiness() {
		Business business = null;
		business = businessDao.get(1);
		if (business != null) {
			System.out.println(business.getRemark());
		}
	}

	public void testGetBusinesssByCategory() {
		List<Business> businesss = businessDao.findBy("businessCategoryId", 2);
		if (businesss != null) {
			for (Business business : businesss) {
				System.out.println(business.getRemark());
			}
		}
	}

	public void testDelteBusiness() {
		System.out.println(businessDao.removeById(1));
	}
}
