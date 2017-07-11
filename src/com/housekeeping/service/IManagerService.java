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

import com.housekeeping.entity.Manager;
import com.housekeeping.entity.Manager_Menu;
import com.housekeeping.entity.Menu;
import com.housekeeping.entity.wrap.Managers;

@Path("/Manager")
public interface IManagerService {

	/**
	 * 添加管理员{@link Manager}
	 * @param manager
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addManager(Manager manager);

	/**
	 * 删除管理员{@link Manager}
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteManager(@PathParam("id") int id);

	/**
	 * 更新管理员{@link Manager}
	 * @param manager
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateManager(Manager manager);

	@GET
	@Path("/login/{userName}/{password}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response login(@PathParam("userName") String userName, @PathParam("password") String password);

	/**
	 * 根据管理员{@link Manager}的主键查询管理员{@link Manager}
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Manager getManager(@PathParam("id") int id);

	/**
	 * 根据管理员用户名获取管理员
	 * @param userName
	 * @return
	 */
	@GET
	@Path("/ByUserName")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Manager getManagerByUserName(String userName);

	@GET
	@Path("/All")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Managers getAll();

	/**
	 * 查询菜单授权的管理员
	 * 关联{@link Manager_Menu}查询管理员{@link Manager},查询某个菜单{@link Menu}那些人有权限访问
	 * @param menuId
	 * @return
	 */
	@GET
	@Path("/Authorized/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Managers getAuthorizedManagers(@PathParam("id") int menuId);

	/**
	 * 查询菜单没有授权的管理员
	 * 关联{@link Manager_Menu}查询管理员{@link Manager},查询某个菜单{@link Menu}那些人有权限访问
	 * @param menuId
	 * @return
	 */
	@GET
	@Path("/UnAuthorized/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Managers getUnAuthorizedManagers(@PathParam("id") int menuId);
}
