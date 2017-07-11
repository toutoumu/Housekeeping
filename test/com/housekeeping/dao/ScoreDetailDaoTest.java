package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.ScoreDetail;

public class ScoreDetailDaoTest extends TestCase {
	private IScoreDetailDao<ScoreDetail> addressDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		addressDao = (IScoreDetailDao<ScoreDetail>) ctx.getBean("scoreDetailDao");
	}

	public void testAddAddress() {
		ScoreDetail address = new ScoreDetail();
		address.setDescription("adfasdf");
		address.setUserId(2);
		int id = (int) addressDao.save(address);
		System.out.println(id);
	}

	public void testUpdateAddress() {
		ScoreDetail address = new ScoreDetail();
		address.setId(1);
		address.setDescription("jjjjjjjjj");
		address.setUserId(2);
		System.out.println(addressDao.update(address));
	}

	public void testGetAddress() {
		ScoreDetail Address = null;
		Address = addressDao.get(1);
		if (Address != null) {
			System.out.println(Address.getDescription());
		}
	}

	 

	public void testGetAddresssByCategory() {
		List<ScoreDetail> Addresss = addressDao.findBy("userId", 2);

		if (Addresss != null) {
			for (ScoreDetail Address : Addresss) {
				System.out.println(Address.getDescription());
			}
		}
	}

	public void testDelteAddress() {
		System.out.println(addressDao.removeById(1));
	}
}
