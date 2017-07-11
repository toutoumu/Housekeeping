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

import com.housekeeping.entity.Order;
import com.housekeeping.entity.wrap.Orders;

@Path("/Order")
public interface IOrderService {

	/**
	 * 添加订单
	 * @param order
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addOrder(Order order);

	/**
	 * 删除订单
	 * @param orderNumber
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteOrder(@PathParam("id") String orderNumber);

	/**
	 * 更新订单
	 * @param order
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateOrder(Order order);

	/**
	 * 查询订单
	 * @param order
	 * @return
	 */
	@PUT
	@Path("/query")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Orders query(Order order);

	/**
	 * 搜索订单根据</br>
	 * 搜索订单编号、服务地址、服务人员、服务人员电话、客户电话等字段内容</br>
	 * @param order
	 * @return
	 */
	@GET
	@Path("/search/{content}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Orders search(@PathParam("content") String searchContent);

	/**
	 * 根据{@link Order}的主键查询{@link Order}
	 * @param orderNumber
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Order getOrder(@PathParam("id") String orderNumber);

	/**
	 * 查询用户所有订单
	 * @param userId
	 * @return
	 */
	@GET
	@Path("/Orders/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Orders getOrderByUserId(@PathParam("id") int userId);

	/**
	 * 查询员工的订单
	 * @param employeeId
	 * @return
	 */
	@GET
	@Path("/Employee/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Orders getOrderByEmployeeId(@PathParam("id") int employeeId);

}
