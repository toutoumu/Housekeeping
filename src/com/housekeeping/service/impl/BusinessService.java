package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IBusinessDao;
import com.housekeeping.entity.Business;
import com.housekeeping.entity.wrap.Businesss;
import com.housekeeping.service.IBusinessService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class BusinessService implements IBusinessService {

	private IBusinessDao<Business> businessDao;

	public void setBusinessDao(IBusinessDao<Business> businessDao) {
		this.businessDao = businessDao;
	}

	@Override
	public Response addBusiness(Business business) {
		if (business != null) {
			int id = (int) businessDao.save(business);
			business.setId(id);
			return Response.ok(business).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteBusiness(int id) {
		if (id != 0) {
			Business business = businessDao.get(id);
			if (business != null) {
				businessDao.remove(business);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("业务不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除业务时主键不能为空");
	}

	@Override
	public Response updateBusiness(Business business) {
		if (business != null) {
			if (businessDao.update(business)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新业务时主键不能为空");
	}

	@Override
	public Business getBusiness(int id) {
		if (id != 0) {
			return businessDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询业务时主键不能为空");
	}

	@Override
	public Businesss getBusinessByCategory(int categoryId) {
		Businesss businesss = new Businesss();
		List<Business> business = businessDao.findBy("businessCategoryId", categoryId);
		if (business == null) {
			return null;
		}
		businesss.setBusinesses(business);
		return businesss;
	}
}
