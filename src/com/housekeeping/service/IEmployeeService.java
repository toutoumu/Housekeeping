package com.housekeeping.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.housekeeping.entity.BusinessCategory;
import com.housekeeping.entity.BusinessCategory_Employee;
import com.housekeeping.entity.Employee;
import com.housekeeping.entity.wrap.Employees;

@Path("/Employee")
public interface IEmployeeService {

	/**
	 * 添加员工{@link Employee}
	 * @param employee
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addEmployee(Employee employee);

	/**
	 * 根据员工{@link Employee}主键删除员工{@link Employee}
	 * @param id 员工{@link Employee}主键
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteEmployee(@PathParam("id") int id);

	/**
	 * 更新员工{@link Employee}
	 * @param employee
	 * @return
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateEmployee(Employee employee);

	/**
	 * 根据员工{@link Employee}主键查询员工{@link Employee}
	 * @param id 员工{@link Employee}主键
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Employee getEmployee(@PathParam("id") int id);

	/**
	 * 根据分类查找{@link Employee}，关联{@link BusinessCategory_Employee}查询
	 * @param businessCategoryId {@link BusinessCategory}的主键
	 * @return {@link Employee}
	 */
	@GET
	@Path("/EmployeeByBusinessCategoryId/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Employees getEmployeeByBusinessCategoryId(@PathParam("id") int businessCategoryId);

	/**
	 * 根据默认分类查找{@link Employee}，只查询查询{@link Employee}表
	 * @param categoryId {@link BusinessCategory}的主键
	 * @return {@link Employee}
	 */
	/*@GET
	@Path("/EmployeeByDefaultCategory/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Employees getEmployeeByDefaultCategory(@PathParam("id") int categoryId);*/

	/**
	 * 查询{@link Order}订单对应的{@link Employee}（阿姨/司机）
	 * @param orderNumber {@link Order}的主键
	 * @return
	 */
	@GET
	@Path("/EmployeeByOrderNumber/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Employees getEmployeeByOrderNumber(@PathParam("id") String orderNumber);

	/**
	 * 根据姓名、贯籍、身份证、电话、特长、备注搜索雇员
	 * @param searchContent
	 * @return
	 */
	@GET
	@Path("/search/{searchContent}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Employees search(@PathParam("searchContent") String searchContent);

	/**
	 * 查询雇员信息
	 * //姓名、贯籍、身份证、电话、特长、备注，员工状态，业务类别
	 * @param searchContent
	 * @return
	 */
	@PUT
	@Path("/query")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Employees query(Employee employee);

}
