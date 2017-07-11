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

import com.housekeeping.entity.Comment;
import com.housekeeping.entity.Employee;
import com.housekeeping.entity.wrap.Comments;

@Path("/Comment")
public interface ICommentService {

	/**
	 * 添加对{@link Employee}的评论
	 * @param comment
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addComment(Comment comment);

	/**
	 * 删除对{@link Employee}的评论
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteComment(@PathParam("id") int id);

	/**
	 * 修改对{@link Employee}的评论
	 * @param comment
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateComment(Comment comment);

	/**
	 * 查询对{@link Employee}的评论
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Comment getComment(@PathParam("id") int id);

	/**
	 * 根据{@link Employee}的主键， 查询{@link Employee}的评论
	 * @param employeeId {@link Employee}的主键
	 * @return
	 */
	@GET
	@Path("/Comments/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Comments getCommentByEmployeeId(@PathParam("id") int employeeId);

}
