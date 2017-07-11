package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.BusinessCategory;

public class BusinessCategoryDaoTest extends TestCase {
	private IBusinessCategoryDao<BusinessCategory> businessCategoryDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		businessCategoryDao = (IBusinessCategoryDao<BusinessCategory>) ctx.getBean("businessCategoryDao");
	}

	public void testAddBusinessCategory() {
		BusinessCategory business = new BusinessCategory();
		business.setId(1);
		business.setName("打扫卫生");
		business.setRemark("我靠你也打扫卫生");
		System.out.println(businessCategoryDao.save(business));

	}

	public void testUpdateBusinessCategory() {
		BusinessCategory business = new BusinessCategory();
		business.setId(1);
		business.setName("打扫卫生");
		business.setRemark("我就不能打扫卫生了吗?");

		boolean b = businessCategoryDao.update(business);
		System.out.println(b);
	}

	public void testGetBusinessCategory() {
		BusinessCategory business = null;
		business = businessCategoryDao.get(1);
		if (business != null) {
			System.out.println(business.getRemark());
		}
	}

	public void testGetBusinessCategorys() {
		List<BusinessCategory> businesss = businessCategoryDao.getAll();
		if (businesss != null) {
			for (BusinessCategory business : businesss) {
				System.out.println(business.getRemark());
			}
		}
	}

	public void testDelteBusinessCategory() {
		System.out.println(businessCategoryDao.removeById(1));
	}
}
