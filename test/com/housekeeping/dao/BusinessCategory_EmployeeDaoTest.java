package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.BusinessCategory_Employee;
import com.housekeeping.entity.pk.BusinessCategory_Employee_PK;

public class BusinessCategory_EmployeeDaoTest extends TestCase {
	private IBusinessCategory_EmployeeDao businessCategory_EmployeeDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		businessCategory_EmployeeDao = (IBusinessCategory_EmployeeDao) ctx.getBean("businessCategory_EmployeeDao");
	}

	public void testAdd() {
		BusinessCategory_Employee employee = new BusinessCategory_Employee();
		BusinessCategory_Employee_PK pk = new BusinessCategory_Employee_PK();
		pk.setBusinessCategoryId(12);
		pk.setEmployeeId(12);
		employee.setPk(pk);
		System.out.println(businessCategory_EmployeeDao.save(employee));
	}

	public void testDelete() {
		BusinessCategory_Employee employee = new BusinessCategory_Employee();
		BusinessCategory_Employee_PK pk = new BusinessCategory_Employee_PK();
		pk.setBusinessCategoryId(12);
		pk.setEmployeeId(12);
		employee.setPk(pk);
		businessCategory_EmployeeDao.removeById(pk);
	}

	public void testIsExistUser() {
		int businessCategorId = 12;
		int employeeId = 12;

		String sql = " SELECT count(*) from   businesscategory_employee e where   e.businessCategoryId="
				+ businessCategorId + "  and e.employeeId=" + employeeId;
		List list = businessCategory_EmployeeDao.executeNativeSql(sql);
		if (list != null) {
			System.out.println(Integer.parseInt(list.get(0).toString()) > 0);
		}

	}
}
