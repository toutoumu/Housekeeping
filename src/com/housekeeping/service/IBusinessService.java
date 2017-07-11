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
import com.housekeeping.entity.BusinessCategory;
import com.housekeeping.entity.wrap.Businesss;

@Path("/Business")
public interface IBusinessService {

	/**
	 * 添加业务{@link Response}
	 * @param business
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addBusiness(Business business);

	/**
	 * 删除业务{@link Business}
	 * @param id 主键
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteBusiness(@PathParam("id") int id);

	/**
	 * 更新业务{@link Business}
	 * @param business
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateBusiness(Business business);

	/**
	 * 根据主键查询业务{@link Business}
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Business getBusiness(@PathParam("id") int id);

	/**
	 * 根据{@link BusinessCategory}的主键，查询业务{@link Business}
	 * @param categoryId {@link BusinessCategory}的主键
	 * @return {@link Business's}
	 */
	@GET
	@Path("/Businesss/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Businesss getBusinessByCategory(@PathParam("id") int categoryId);
}
