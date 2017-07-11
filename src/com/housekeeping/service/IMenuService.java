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

import com.housekeeping.entity.Manager_Menu;
import com.housekeeping.entity.Menu;
import com.housekeeping.entity.wrap.Menus;

@Path("/Menu")
public interface IMenuService {

	/**
	 * 添加菜单{@link Menu}
	 * @param menu
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addMenu(Menu menu);

	/**
	 * 删除菜单{@link Menu}
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteMenu(@PathParam("id") int id);

	/**
	 * 更新菜单
	 * @param menu
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateMenu(Menu menu);

	/**
	 * 查询菜单
	 * @param menu
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Menus queryMenus(Menu menu);

	/**
	 * 根据{@link Menu}的主键查询{@link Menu}
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Menu getMenu(@PathParam("id") int id);

	@GET
	@Path("/All")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Menus getMenues();

	/**
	 * 查询管理员已经授权的菜单
	 * 关联{@link Manager_Menu}查询菜单{@link Menu},查询某个人可以訪問哪些菜单{@link Menu}
	 * @param managerId 
	 * @return
	 */
	@GET
	@Path("/Authorized/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Menus getAuthorizedMenus(@PathParam("id") int managerId);

	/**
	 * 获取管理员未授权的菜单
	 * @param managerId
	 * @return
	 */
	@GET
	@Path("/UnAuthorized/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Menus getUnAuthorizedMenus(@PathParam("id") int managerId);

}
