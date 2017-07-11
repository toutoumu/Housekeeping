package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IEmployeeDao;
import com.housekeeping.entity.Employee;
import com.housekeeping.entity.wrap.Employees;
import com.housekeeping.service.IEmployeeService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class EmployeeService implements IEmployeeService {
	private IEmployeeDao<Employee> employeeDao;

	public void setEmployeeDao(IEmployeeDao<Employee> employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public Response addEmployee(Employee employee) {
		if (employee != null) {
			employee.setState(4);
			int id = (int) employeeDao.save(employee);
			employee.setId(id);
			return Response.ok(employee).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteEmployee(int id) {
		if (id != 0) {
			Employee employee = employeeDao.get(id);
			if (employee != null) {
				employeeDao.remove(employee);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("员工不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除员工时主键不能为空");
	}

	@Override
	public Response updateEmployee(Employee employee) {
		if (employee != null && employee.getId() != 0) {
			if (employeeDao.update(employee)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新员工时主键不能为空");
	}

	@Override
	public Employee getEmployee(int id) {
		if (id != 0) {
			return employeeDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询员工时主键不能为空");
	}

	@Override
	public Employees getEmployeeByBusinessCategoryId(int businessCategoryId) {
		if (businessCategoryId == 0) {
			throw ServiceErrorBuilder.badRequestError("业务类别ID不能为空");
		}
		Employees employees = new Employees();
		List<Employee> employeesList = employeeDao.getEmployeeByBusinessCategoryId(businessCategoryId);
		if (employeesList == null || employeesList.size() == 0) {
			return null;
		}
		employees.setEmployees(employeesList);
		return employees;
	}

	/*@Override
	public Employees getEmployeeByDefaultCategory(int categoryId) {
		if (categoryId == 0) {
			throw ServiceErrorBuilder.badRequestError("业务类别ID不能为空");
		}
		Employees employees = new Employees();
		List<Employee> employeesList = employeeDao.getEmployeeByDefaultCategory(categoryId);
		if (employeesList == null) {
			return null;
		}
		employees.setEmployees(employeesList);
		return employees;
	}*/

	@Override
	public Employees getEmployeeByOrderNumber(String orderNumber) {
		if (StringUtil.strIsEmpty(orderNumber)) {
			throw ServiceErrorBuilder.badRequestError("订单编号不能为空");
		}
		Employees employees = new Employees();
		List<Employee> employeesList = employeeDao.getEmployeeByOrderNumber(orderNumber);
		if (employeesList == null) {
			return null;
		}
		employees.setEmployees(employeesList);
		return employees;
	}

	@Override
	public Employees search(String searchContent) {
		if (!StringUtil.strIsEmpty(searchContent)) {
			List<Employee> employeesList = employeeDao.search(searchContent);
			if (employeesList != null) {
				Employees employees = new Employees();
				employees.setEmployees(employeesList);
				return employees;
			}
		}
		throw ServiceErrorBuilder.badRequestError("搜索内容不能为空");
	}

	@Override
	public Employees query(Employee employee) {
		if (employee != null) {
			List<Employee> employeeList = employeeDao.query(employee);
			if (employeeList != null) {
				Employees employees = new Employees();
				employees.setEmployees(employeeList);
				return employees;
			}
			return null;
		}
		throw ServiceErrorBuilder.badRequestError("查询内容不能为空");
	}
}
