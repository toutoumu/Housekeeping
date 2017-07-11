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

import com.housekeeping.entity.Employee;
import com.housekeeping.entity.Order_Employee;

@Path("/Order_Employee")
public interface IOrder_EmployeeService {

	/**
	 * 添加订单{@link Order}和员工{@link Employee}的关联关系
	 * @param {@link Order_Employee} 
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addOrder_Employee(Order_Employee order_Employee);

	/**
	 * 删除订单{@link Order}和员工{@link Employee}的关联关系
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{employeeId}/{orderNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteOrder_Employee(@PathParam("employeeId") int employeeId, @PathParam("orderNumber") String orderNumber);

	/**
	 * 根据主键查询{@link Order_Employee}
	 * @param id
	 * @return
	 */
	@GET
	@Path("{employeeId}/{orderNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Order_Employee getOrder_Employee(@PathParam("employeeId") int employeeId,
			@PathParam("orderNumber") String orderNumber);

	/**
	 * 根据主键查询{@link Order_Employee}是否存在
	 * @param pk
	 * @return
	 */
	@GET
	@Path("/IsExist/{employeeId}/{orderNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	boolean IsExist(@PathParam("employeeId") int employeeId, @PathParam("orderNumber") String orderNumber);

}
