package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IRegionDao;
import com.housekeeping.entity.Region;
import com.housekeeping.entity.wrap.Regions;
import com.housekeeping.service.IRegionService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class RegionService implements IRegionService {
	private IRegionDao<Region> regionDao;

	public void setRegionDao(IRegionDao<Region> regionDao) {
		this.regionDao = regionDao;
	}

	@Override
	public Response addRegion(Region region) {
		if (region != null) {
			int id = (int) regionDao.save(region);
			region.setId(id);
			if (region != null) {
				return Response.ok(region).build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteRegion(int id) {
		if (id != 0) {
			Region region = regionDao.get(id);
			if (region != null) {
				regionDao.remove(region);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("Region不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除Region时主键不能为空");
	}

	@Override
	public Response updateRegion(Region region) {
		if (region != null && region.getId() != 0) {
			if (regionDao.update(region)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新地区时主键不能为空");
	}

	@Override
	public Region getRegion(int id) {
		if (id != 0) {
			return regionDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询地区是主键不能为空");
	}

	@Override
	public Regions getRegions(int parentId) {
		List<Region> regions = regionDao.findBy("parentId", parentId);
		if (regions == null) {
			return null;
		}
		Regions regions2 = new Regions();
		regions2.setRegions(regions);
		return regions2;
	}

}
