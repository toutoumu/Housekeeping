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

import com.housekeeping.entity.ScoreDetail;
import com.housekeeping.entity.User;
import com.housekeeping.entity.wrap.ScoreDetails;

@Path("/ScoreDetail")
public interface IScoreDetailService {

	/**
	 * 添加地址
	 * @param ScoreDetail
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addScoreDetail(ScoreDetail scoreDetail);

	/**
	 * 删除地址
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteScoreDetail(@PathParam("id") int id);

	/**
	 * 更新地址
	 * @param ScoreDetail 
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updateScoreDetail(ScoreDetail scoreDetail);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	ScoreDetail getScoreDetail(@PathParam("id") int id);

	/**
	 * 根据{@link User}的主键查询{@link ScoreDetail}
	 * @param id
	 * @return
	 */
	@GET
	@Path("/ScoreDetails/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	ScoreDetails getScoreDetailByUserId(@PathParam("id") int userId);

}
