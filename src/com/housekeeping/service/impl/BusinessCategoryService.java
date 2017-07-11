package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IBusinessCategoryDao;
import com.housekeeping.entity.BusinessCategory;
import com.housekeeping.entity.wrap.BusinessCategorys;
import com.housekeeping.service.IBusinessCategoryService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class BusinessCategoryService implements IBusinessCategoryService {

	private IBusinessCategoryDao<BusinessCategory> businessCategoryDao;

	public void setBusinessCategoryDao(IBusinessCategoryDao<BusinessCategory> articleDao) {
		this.businessCategoryDao = articleDao;
	}

	@Override
	public Response addBusinessCategory(BusinessCategory category) {
		if (category != null /*&& category.getId() != 0*/) {
			category.setVisiblity(true);
			businessCategoryDao.save(category);
			return Response.ok(category).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加业务分类时，业务分类不能为空");
	}

	@Override
	public Response deleteBusinessCategory(int id) {
		if (id != 0) {
			BusinessCategory category = businessCategoryDao.get(id);
			if (category != null) {
				if (category.isDefault()) {
					throw ServiceErrorBuilder.badRequestError("默认分类不能删除");
				}
				businessCategoryDao.remove(category);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("业务分类不存在");
		}
		throw ServiceErrorBuilder.badRequestError("修改业务分类时Id不能为空");
	}

	@Override
	public Response updateBusinessCategory(BusinessCategory category) {
		if (category != null && category.getId() != 0) {
			/*if (category.isDefault()) {
				throw ServiceErrorBuilder.badRequestError("默认分类不能修改");
			}*/
			if (businessCategoryDao.update(category)) {
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("修改业务分类信息失败！");
		}
		throw ServiceErrorBuilder.badRequestError("修改业务分类时Id不能为空");
	}

	@Override
	public BusinessCategory getBusinessCategory(int id) {
		if (id != 0) {
			BusinessCategory retBusinessCategory = businessCategoryDao.get(id);
			return retBusinessCategory;
		}
		throw ServiceErrorBuilder.badRequestError("查询业务分类时Id不能为空！");
	}

	@Override
	public BusinessCategorys getBusinessCategorys() {
		List<BusinessCategory> businessCategories = businessCategoryDao.getAll();
		if (businessCategories == null) {
			return null;
		}
		BusinessCategorys categorys = new BusinessCategorys();
		categorys.setBusinessCategories(businessCategories);
		return categorys;
	}

	@Override
	public BusinessCategorys getAuthorizedCategorys(int employeeId) {
		if (employeeId != 0) {
			String hql = "select emp from BusinessCategory  emp , BusinessCategory_Employee  bus"
					+ " where  emp.id = bus.pk.businessCategoryId and" + "  bus.pk.employeeId=?";
			List<BusinessCategory> menus = (List<BusinessCategory>) businessCategoryDao.find(hql, employeeId);
			if (menus == null) {
				return null;
			}
			BusinessCategorys menus2 = new BusinessCategorys();
			menus2.setBusinessCategories(menus);
			return menus2;
		}
		throw ServiceErrorBuilder.badRequestError("根据员工查询员工类别时员工ID不能为空");
	}

	@Override
	public BusinessCategorys getUnAuthorizedCategorys(int employeeId) {
		if (employeeId != 0) {
			String hql = "from BusinessCategory menu where menu.id not in ( select mm.pk.businessCategoryId from BusinessCategory_Employee mm where mm.pk.employeeId = ?)";
			List<BusinessCategory> menus = (List<BusinessCategory>) businessCategoryDao.find(hql, employeeId);
			if (menus == null || menus.size() == 0) {
				return null;
			}
			BusinessCategorys menus2 = new BusinessCategorys();
			menus2.setBusinessCategories(menus);
			return menus2;
		}
		throw ServiceErrorBuilder.badRequestError("根据员工查询员工类别时员工ID不能为空");
	}
}
