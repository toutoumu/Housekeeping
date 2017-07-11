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

import com.housekeeping.entity.OrderDetail;
import com.housekeeping.entity.wrap.OrderDetails;

@Path("/OrderDetail")
public interface IOrderDetailService {

	/**
	 * 添加订单明细
	 * @param detail
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addOrderDetail(OrderDetail detail);

	/**
	 * 删除订单明细
	 * @param detailId
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteOrderDetail(@PathParam("id") int detailId);

	/**
	 * 更新订单明细
	 * @param detail
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateOrderDetail(OrderDetail detail);

	/**
	 * 根据{@link OrderDetail}的主键查询{@link OrderDetail}
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	OrderDetail getOrderDetail(@PathParam("id") int id);

	/**
	 * 获取订单对应的订单明细
	 * @param orderNumber
	 * @return
	 */
	@GET
	@Path("/OrderDetails/{orderNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	OrderDetails getOrderDetails(@PathParam("orderNumber") String orderNumber);

}
