package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.BusinessDetail;

public class BusinessDetailDaoTest extends TestCase {
	private IBusinessDetailDao<BusinessDetail> businessDetailDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		businessDetailDao = (IBusinessDetailDao<BusinessDetail>) ctx.getBean("businessDetailDao");
	}

	public void testAddBusinessDetail() {
		BusinessDetail detail = new BusinessDetail();
		detail.setBusinessId(1);
		detail.setName("打扫卫生");
		detail.setRemark("我靠你也打扫卫生");
		int id = (int) businessDetailDao.save(detail);
		if (detail != null) {
			System.out.println(detail.getRemark());
		}
	}

	public void testUpdateBusinessDetail() {
		BusinessDetail detail = new BusinessDetail();
		detail.setId(1);
		detail.setBusinessId(1);
		detail.setName("打扫卫生");
		detail.setRemark("我就不能打扫卫生了吗?");
		boolean b = businessDetailDao.update(detail);
		System.out.println(b);
	}

	public void testGetBusinessDetail() {
		BusinessDetail detail = null;
		detail = businessDetailDao.get(1);
		if (detail != null) {
			System.out.println(detail.getRemark());
		}
	}

	public void testGetBusinessDetailsByCategory() {
		List<BusinessDetail> businesss = businessDetailDao.findBy("businessId", 1);
		;
		if (businesss != null) {
			for (BusinessDetail detail : businesss) {
				System.out.println(detail.getRemark());
			}
		}
	}

	public void testDelteBusinessDetail() {
		System.out.println(businessDetailDao.removeById(1));
	}
}
