package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IBusinessDetailDao;
import com.housekeeping.entity.BusinessDetail;
import com.housekeeping.entity.wrap.BusinessDetails;
import com.housekeeping.service.IBusinessDetailService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class BusinessDetailService implements IBusinessDetailService {

	private IBusinessDetailDao<BusinessDetail> businessDetailDao;

	public void setBusinessDetailDao(IBusinessDetailDao<BusinessDetail> businessDetailDao) {
		this.businessDetailDao = businessDetailDao;
	}

	@Override
	public Response addBusinessDetail(BusinessDetail detail) {
		if (detail != null) {
			int id = (int) businessDetailDao.save(detail);
			detail.setId(id);
			return Response.ok(detail).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteBusinessDetail(int id) {
		if (id != 0) {
			BusinessDetail detail = businessDetailDao.get(id);
			if (detail != null) {
				businessDetailDao.remove(detail);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("业务明细不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除业务明细时主键不能为空");
	}

	@Override
	public Response updateBusinessDetail(BusinessDetail detail) {
		if (detail != null && detail.getId() != 0) {
			if (businessDetailDao.update(detail)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新业务明细时主键不能为空");
	}

	@Override
	public BusinessDetail getBusinessDetail(int id) {
		if (id != 0) {
			return businessDetailDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询业务明细时主键不能为空");
	}

	@Override
	public BusinessDetails getBusinessDetailByBusiness(int categoryId) {
		List<BusinessDetail> businessDetails = businessDetailDao.findBy("businessId", categoryId);
		if (businessDetails == null) {
			return null;
		}
		BusinessDetails details = new BusinessDetails();
		details.setDetails(businessDetails);
		return details;
	}

}
