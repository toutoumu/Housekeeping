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

import com.housekeeping.entity.Business;
import com.housekeeping.entity.BusinessDetail;
import com.housekeeping.entity.wrap.BusinessDetails;

@Path("/BusinessDetail")
public interface IBusinessDetailService {

	/**
	 * 添加业务明细{@link BusinessDetail}
	 * @param detail 业务明细{@link BusinessDetail}
	 * @return {@link Response}
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addBusinessDetail(BusinessDetail detail);

	/**
	 * 删除业务明细{@link BusinessDetail}
	 * @param id 业务明细{@link BusinessDetail}主键
	 * @return  {@link Response}
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteBusinessDetail(@PathParam("id") int id);

	/**
	 * 更新业务明细{@link BusinessDetail}
	 * @param detail 业务明细{@link BusinessDetail}
	 * @return  {@link Response}
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateBusinessDetail(BusinessDetail detail);

	/**
	 * 根据业务明细{@link BusinessDetail}的主键查询业务明细{@link BusinessDetail}
	 * @param id 业务明细{@link BusinessDetail}的主键
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	BusinessDetail getBusinessDetail(@PathParam("id") int id);

	/**
	 * 根据 {@link Business}的主键，查询业务明细{@link BusinessDetail}
	 * @param categoryId {@link Business}的主键
	 * @return {@link BusinessDetails}
	 */
	@GET
	@Path("/BusinessDetails/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	BusinessDetails getBusinessDetailByBusiness(@PathParam("id") int categoryId);
}
