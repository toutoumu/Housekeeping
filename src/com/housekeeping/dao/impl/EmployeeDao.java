package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IEmployeeDao;
import com.housekeeping.entity.Employee;
import com.housekeeping.utils.StringUtil;

public class EmployeeDao<T> extends BaseDao<T, Serializable> implements IEmployeeDao<T> {

	public EmployeeDao(Class<T> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeByBusinessCategoryId(int businessCategoryId) {
		// TODO 此处原来是查询默认类别为businessCategoryId的员工和关联BusinessCategory_Employee查询到的员工,现在更改为关联BusinessCategory_Employee查询
		/*String hql = "FROM Employee emp where emp.businessCategoryId =?"
				+ " or emp.id in ( SELECT bus.pk.employeeId from  BusinessCategory_Employee bus where bus.pk.businessCategoryId =?)";
		return (List<Employee>) find(hql, businessCategoryId, businessCategoryId);*/
		String hql = "FROM Employee emp where "
				+ " emp.state = 1 and emp.id in ( SELECT bus.pk.employeeId from  BusinessCategory_Employee bus where bus.pk.businessCategoryId =?)";
		return (List<Employee>) find(hql, businessCategoryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeByDefaultCategory(int categoryId) {
		String hql = "from Employee emp where emp.businessCategoryId=?";
		return (List<Employee>) find(hql, categoryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeByOrderNumber(String orderNumber) {
		String hql = "select emp from Employee emp ,Order_Employee oe where emp.id = oe.pk.employeeId"
				+ " and oe.pk.orderNumber=?";
		return (List<Employee>) find(hql, orderNumber);
	}

	@Override
	public List<Employee> search(String searchContent) {
		//姓名、贯籍、身份证、电话、特长、备注
		StringBuilder builder = new StringBuilder();
		builder.append(" from Employee e where ");
		// 姓名
		builder.append("e.name like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 籍贯
		builder.append("e.nativeSlace like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 身份证
		builder.append("e.idCard like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 电话
		builder.append("e.phoneNumber like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 特长
		builder.append("e.speciality like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 备注
		builder.append("e.remark like '%");
		builder.append(searchContent);
		builder.append("%'");
		//builder.append(" or ");
		System.out.println(builder.toString());
		return (List<Employee>) find(builder.toString(), null);
	}

	@Override
	public List<Employee> query(Employee employee) {
		//姓名、贯籍、身份证、电话、特长、备注，员工状态，业务类别,服务区域,相关证件,相关证件号码
		StringBuilder builder = new StringBuilder();
		builder.append(" from Employee e where 1=1 ");
		// 姓名
		if (!StringUtil.strIsEmpty(employee.getName())) {
			builder.append(" and ");
			builder.append("e.name like '%");
			builder.append(employee.getName());
			builder.append("%'");
		}

		// 相关证件
		if (!StringUtil.strIsEmpty(employee.getCredentialsCategory())) {
			builder.append(" and ");
			builder.append("e.credentialsCategory like '%");
			builder.append(employee.getCredentialsCategory());
			builder.append("%'");
		}

		// 相关证件号码
		if (!StringUtil.strIsEmpty(employee.getCredentialsNumber())) {
			builder.append(" and ");
			builder.append("e.credentialsNumber like '%");
			builder.append(employee.getCredentialsNumber());
			builder.append("%'");
		}

		// 服务区域
		if (!StringUtil.strIsEmpty(employee.getServiceArea())) {
			builder.append(" and ");
			builder.append("e.serviceArea like '%");
			builder.append(employee.getServiceArea());
			builder.append("%'");

		}
		// 员工状态
		if (employee.getState() != 0) {
			builder.append(" and ");
			builder.append("e.state = ");
			builder.append(employee.getState());
		}

		// 工作经验
		if (employee.getWorkExperience() != 0) {
			builder.append(" and ");
			builder.append("e.workExperience = ");
			builder.append(employee.getWorkExperience());
		}

		// 籍贯
		if (!StringUtil.strIsEmpty(employee.getNativeSlace())) {
			builder.append(" and ");
			builder.append("e.nativeSlace like '%");
			builder.append(employee.getNativeSlace());
			builder.append("%'");
		}
		// 身份证
		if (!StringUtil.strIsEmpty(employee.getIdCard())) {
			builder.append(" and ");
			builder.append("e.idCard like '%");
			builder.append(employee.getIdCard());
			builder.append("%'");
		}
		// 电话
		if (!StringUtil.strIsEmpty(employee.getPhoneNumber())) {
			builder.append(" and ");
			builder.append("e.phoneNumber like '%");
			builder.append(employee.getPhoneNumber());
			builder.append("%'");
		}
		// 特长
		if (!StringUtil.strIsEmpty(employee.getSpeciality())) {
			builder.append(" and ");
			builder.append("e.speciality like '%");
			builder.append(employee.getSpeciality());
			builder.append("%'");
		}
		// 备注
		if (!StringUtil.strIsEmpty(employee.getRemark())) {
			builder.append(" and ");
			builder.append("e.remark like '%");
			builder.append(employee.getRemark());
			builder.append("%'");
		}
		// 星星评分
		if (employee.getGrade() != 0) {
			builder.append(" and ");
			builder.append("e.grade = ");
			builder.append(employee.getGrade());
		}
		// 推荐至
		if (employee.getRank() != 0) {
			builder.append(" and ");
			builder.append("e.rank = ");
			builder.append(employee.getRank());
		}

		// 业务类别
		if (employee.getBusinessCategoryId() != 0) {
			builder.append(" and ");
			builder.append(" e.id in ( SELECT bus.pk.employeeId from  BusinessCategory_Employee bus where bus.pk.businessCategoryId = "
					+ employee.getBusinessCategoryId() + ")");
		}
		return (List<Employee>) find(builder.toString(), null);
	}
}
