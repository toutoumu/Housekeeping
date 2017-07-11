package com.housekeeping.service;

import java.awt.Menu;

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

import com.housekeeping.entity.Manager_Menu;
import com.housekeeping.entity.pk.Manager_Menu_PK;
import com.mchange.v2.resourcepool.ResourcePool.Manager;

@Path("/Manager_Menu")
public interface IManager_MenuService {

	/**
	 * 添加管理员{@link Manager}和菜单{@link Menu}的关联关系
	 * @param manager_Menu 
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addManager_Menu(Manager_Menu manager_Menu);

	/**
	 * 删除管理员{@link Manager}和菜单{@link Menu}的关联关系
	 * @param pk
	 * @return
	 */
	@DELETE
	@Path("{menuId}/{managerId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteManager_Menu(@PathParam("menuId") int menuId, @PathParam("managerId") int managerId);

	/**
	 * 查询管理员{@link Manager}和菜单{@link Menu}的关联关系
	 * @param pk 
	 * @return
	 */
	@GET
	@Path("{menuId}/{managerId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Manager_Menu getManager_Menu(@PathParam("menuId") int menuId, @PathParam("managerId") int managerId);

	/**
	 * 查询管理员{@link Manager}和菜单{@link Menu}的关联关系是否存在
	 * @param pk {@link Manager_Menu_PK}
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	boolean IsExist(Manager_Menu_PK pk);

}
