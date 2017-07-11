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

import com.housekeeping.entity.Message;
import com.housekeeping.entity.wrap.Messages;

@Path("/Message")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface IMessageService {
	/**
	 * 添加消息
	 * @param message
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addMessage(Message message);

	/**
	 * 删除消息
	 * @param id 用户Id
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteMessage(@PathParam("id") int id);

	/**
	 * 更新消息
	 * @param message 用户的Id不能为空
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateMessage(Message message);

	/**
	 * 查询消息
	 * @param id 用户Id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Message getMessage(@PathParam("id") int id);

	/**
	 * 查询订单对应的消息
	 * @return 返回经过包装的用户列表{@link Message}
	 */
	@GET
	@Path("/Messages/{orderNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Messages getMessagesByOrderId(@PathParam("orderNumber") String orderNumber);

}
