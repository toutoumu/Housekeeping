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

import com.housekeeping.entity.User;
import com.housekeeping.entity.wrap.Users;

@Path("/User")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface IUserService {
	/**
	 * 添加用户
	 * @param user
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addUser(User user);

	/**
	 * 删除用户
	 * @param id 用户Id
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteUser(@PathParam("id") int id);

	/**
	 * 更新用户
	 * @param user 用户的Id不能为空
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateUser(User user);

	/**
	 * 查询用户
	 * @param id 用户Id
	 * @return
	 */
	@GET
	@Path("{id}/{phoneNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	User getUser(@PathParam("id") int id, @PathParam("phoneNumber") String phoneNumber);
	
	/**
	 * 根据序号、会员名称、地址、手机号搜索会员
	 * @param searchContent
	 * @return
	 */
	@GET
	@Path("/search/{searchContent}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Users search(@PathParam("searchContent") String searchContent);
	
	/**
	 * 获取所有用户
	 * @return 返回经过包装的用户列表{@link Users}
	 */
	@PUT
	@Path("/users")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Users getUsers(User user);

	/**
	 * 根据传入参数的属性查询用户是否存在，如果某个字段不需要作为查询条件请保持原值
	 * @param user 
	 * @return {@link Boolean}
	 */
	@PUT
	@Path("/isExist")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	boolean isExistUser(User user);
}
