package com.housekeeping.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.housekeeping.entity.BusinessCategory;
import com.housekeeping.entity.BusinessCategory_Employee;
import com.housekeeping.entity.Employee;

/**
 * 文件名: IBusinessCategory_EmployeeDao.java</br>
 * 编写者: toutoumu</br>
 * 编写日期: 2014年7月28日</br>
 * 简要描述:业务--人员关系维护</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@Path("/BusinessCategory_Employee")
public interface IBusinessCategory_EmployeeService {

	/**
	 * 添加业务类别{@link BusinessCategory}与员工{@link Employee}之间的关系
	 * @param entity
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addBusinessCategory_Employee(BusinessCategory_Employee entity);

	/**
	 * 删除业务类别{@link BusinessCategory}与员工{@link Employee}之间的关系
	 * @param entity
	 * @return
	 */
	@DELETE
	@Path("{businessCategorId}/{employeeId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteBusinessCategory_Employee(@PathParam("businessCategorId") int businessCategorId,
			@PathParam("employeeId") int employeeId);//BusinessCategory_Employee entity);

	/**
	 * 判断业务类别{@link BusinessCategory}与员工{@link Employee}之间的关系是否存在
	 * @param pk
	 * @return
	 */
	@GET
	@Path("{businessCategorId}/{employeeId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	boolean isExist(@PathParam("businessCategorId") int businessCategorId, @PathParam("employeeId") int employeeId);//BusinessCategory_Employee_PK pk);
}
