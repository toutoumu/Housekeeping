package com.housekeeping.dao;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.IBaseDao;
import com.housekeeping.entity.BusinessCategory;
import com.housekeeping.entity.BusinessCategory_Employee;
import com.housekeeping.entity.Employee;

public interface IEmployeeDao<T> extends IBaseDao<T, Serializable> {

	/**
	 * 根据分类查找{@link Employee}，关联{@link BusinessCategory_Employee}查询
	 * @param businessCategoryId {@link BusinessCategory}的主键
	 * @return {@link Employee}
	 */
	List<Employee> getEmployeeByBusinessCategoryId(int businessCategoryId);

	/**
	 * 根据默认分类查找{@link Employee}，只查询查询{@link Employee}表
	 * @param categoryId {@link BusinessCategory}的主键
	 * @return {@link Employee}
	 */
	List<Employee> getEmployeeByDefaultCategory(int categoryId);

	/**
	 * 查询{@link Order}订单对应的{@link Employee}（阿姨/司机）
	 * @param orderNumber {@link Order}的主键
	 * @return
	 */
	List<Employee> getEmployeeByOrderNumber(String orderNumber);

	/**
	 * 根据姓名、贯籍、身份证、电话、特长、备注搜索雇员
	 * @param searchContent
	 * @return
	 */
	List<Employee> search(String searchContent);

	/**
	 * 查询雇员
	 * //姓名、贯籍、身份证、电话、特长、备注，员工状态，业务类别
	 * @return
	 */
	List<Employee> query(Employee employee);

}
