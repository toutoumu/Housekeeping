package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Region;

public class RegionDaoTest extends TestCase {
	private IRegionDao<Region> regionDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		regionDao = (IRegionDao<Region>) ctx.getBean("regionDao");
	}

	public void testAddRegion() {
		Region region = new Region();
		region.setName("liubin1");
		region.setRegionType(-1);
		region.setParentId(1);
		int id = (int) regionDao.save(region);
		System.out.println(id);
	}

	public void testUpdateRegion() {
		Region region = new Region();
		region.setId(1);
		region.setName("liubin2");
		region.setParentId(-1);
		boolean b = regionDao.update (region);
		System.out.println(b);
	}

	public void testGetRegions() {
		List<Region> regions = regionDao.findBy("parentId", 1);
		if (regions != null) {
			System.out.println(regions.size());
		}
	}

	public void testDelteRegion() {
		System.out.println(regionDao.removeById(1));
	}

	public void testgetRegionId() {
		Region region = regionDao.get(2);
		if (region != null) {
			System.out.println(region.getName());
		}
	}
}
