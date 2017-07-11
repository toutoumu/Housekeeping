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
import com.housekeeping.entity.wrap.BusinessCategorys;

@Path("/BusinessCategory")
public interface IBusinessCategoryService {

	/**
	 * 添加服务类别{@link BusinessCategory}</br>
	 * 注意这里的ID需要手动分配
	 * @param category
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addBusinessCategory(BusinessCategory category);

	/**
	 * 删除服务类别{@link BusinessCategory}
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteBusinessCategory(@PathParam("id") int id);

	/**
	 * 更新服务类别{@link BusinessCategory}
	 * @param category
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateBusinessCategory(BusinessCategory category);

	/**
	 * 根据主键查询服务类别{@link BusinessCategory}
	 * @param id 主键
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	BusinessCategory getBusinessCategory(@PathParam("id") int id);

	/**
	 * 获取所有的服务类别{@link BusinessCategory}
	 * @return
	 */
	@GET
	@Path("/BusinessCategorys")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	BusinessCategorys getBusinessCategorys();

	/**
	 * 查询员工已经拥有的业务类别
	 * 关联{@link BusinessCategory_Employee}查询类别{@link BusinessCategory},查询员工拥有的业务类别{@link BusinessCategory}
	 * @param managerId 
	 * @return
	 */
	@GET
	@Path("/Authorized/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	BusinessCategorys getAuthorizedCategorys(@PathParam("id") int managerId);

	/**
	 * 获取员工没有的业务类别
	 * @param managerId
	 * @return
	 */
	@GET
	@Path("/UnAuthorized/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	BusinessCategorys getUnAuthorizedCategorys(@PathParam("id") int managerId);
}
