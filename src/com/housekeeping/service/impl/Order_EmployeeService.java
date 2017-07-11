package com.housekeeping.service.impl;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IOrder_EmployeeDao;
import com.housekeeping.entity.Order_Employee;
import com.housekeeping.entity.pk.Order_Employee_PK;
import com.housekeeping.service.IOrder_EmployeeService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class Order_EmployeeService implements IOrder_EmployeeService {
	private IOrder_EmployeeDao<Order_Employee> order_EmployeeDao;

	public void setOrder_EmployeeDao(IOrder_EmployeeDao<Order_Employee> order_EmployeeDao) {
		this.order_EmployeeDao = order_EmployeeDao;
	}

	@Override
	public Response addOrder_Employee(Order_Employee order_Employee) {
		if (order_Employee != null && order_Employee.getPk() != null) {
			order_EmployeeDao.save(order_Employee);
			if (order_Employee != null) {
				return Response.ok(order_Employee).build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteOrder_Employee(int employeeId, String orderNumber) {
		if (employeeId != 0 && !StringUtil.strIsEmpty(orderNumber)) {
			Order_Employee_PK pk = new Order_Employee_PK();
			pk.setEmployeeId(employeeId);
			pk.setOrderNumber(orderNumber);
			if (order_EmployeeDao.removeById(pk)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("删除订单-员工关系时主键不能为空");
	}

	@Override
	public Order_Employee getOrder_Employee(int employeeId, String orderNumber) {
		if (employeeId != 0 && !StringUtil.strIsEmpty(orderNumber)) {
			Order_Employee_PK pk = new Order_Employee_PK();
			pk.setEmployeeId(employeeId);
			pk.setOrderNumber(orderNumber);
			return order_EmployeeDao.get(pk);
		}
		throw ServiceErrorBuilder.badRequestError("查询订单-员工关系时主键不能为空");
	}

	@Override
	public boolean IsExist(int employeeId, String orderNumber) {
		if (employeeId != 0 && !StringUtil.strIsEmpty(orderNumber)) {
			Order_Employee_PK pk = new Order_Employee_PK();
			pk.setEmployeeId(employeeId);
			pk.setOrderNumber(orderNumber);
			return order_EmployeeDao.IsExist(pk);
		}
		throw ServiceErrorBuilder.badRequestError("查询订单-员工关系是否存在时主键不能为空");
	}
}
