package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IBusinessCategory_EmployeeDao;
import com.housekeeping.entity.BusinessCategory_Employee;
import com.housekeeping.entity.pk.BusinessCategory_Employee_PK;
import com.housekeeping.service.IBusinessCategory_EmployeeService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class BusinessCategory_EmployeeService implements IBusinessCategory_EmployeeService {

	private IBusinessCategory_EmployeeDao<BusinessCategory_Employee> businessCategory_EmployeeDao;

	/**
	 * @param businessCategory_EmployeeDao the businessCategory_EmployeeDao to set
	 */
	public void setBusinessCategory_EmployeeDao(
			IBusinessCategory_EmployeeDao<BusinessCategory_Employee> businessCategory_EmployeeDao) {
		this.businessCategory_EmployeeDao = businessCategory_EmployeeDao;
	}

	@Override
	public Response addBusinessCategory_Employee(BusinessCategory_Employee entity) {
		if (entity.getPk().getBusinessCategoryId() == 0 || entity.getPk().getEmployeeId() == 0) {
			throw ServiceErrorBuilder.badRequestError("主键不能为空");
		}
		if (isExist(entity.getPk().getBusinessCategoryId(), entity.getPk().getEmployeeId())) {
			throw ServiceErrorBuilder.badRequestError("已经存在");
		}
		BusinessCategory_Employee_PK pk = (BusinessCategory_Employee_PK) businessCategory_EmployeeDao.save(entity);
		entity.setPk(pk);
		return Response.ok(entity).build();
	}

	@Override
	public Response deleteBusinessCategory_Employee(int businessCategorId, int employeeId) {
		// 主键为空
		if (businessCategorId == 0 || employeeId == 0) {
			throw ServiceErrorBuilder.badRequestError("主键不能为空");
		}
		if (!isExist(businessCategorId, employeeId)) {
			System.out.println("要删除的对象不存在");
			throw ServiceErrorBuilder.badRequestError("要删除的对象不存在");
		}
		BusinessCategory_Employee_PK pk = new BusinessCategory_Employee_PK();
		pk.setBusinessCategoryId(businessCategorId);
		pk.setEmployeeId(employeeId);
		if (businessCategory_EmployeeDao.removeById(pk)) {
			return Response.ok().build();
		}
		throw ServiceErrorBuilder.badRequestError("删除失败");
	}

	@Override
	public boolean isExist(int businessCategorId, int employeeId) {
		if (businessCategorId == 0 || employeeId == 0) {
			return false;
		}
		String sql = " SELECT count(*) from   businesscategory_employee e where   e.businessCategoryId="
				+ businessCategorId + "  and e.employeeId=" + employeeId;
		List<?> list = businessCategory_EmployeeDao.executeNativeSql(sql);
		if (list != null) {
			return Integer.parseInt(list.get(0).toString()) > 0;
		}
		return false;
	}
}
