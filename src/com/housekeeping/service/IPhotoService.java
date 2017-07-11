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

import com.housekeeping.entity.Photo;
import com.housekeeping.entity.wrap.Photos;

@Path("/Photo")
public interface IPhotoService {

	/**
	 * 添加照片
	 * @param Photo
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addPhoto(Photo address);

	/**
	 * 删除照片
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deletePhoto(@PathParam("id") int id);

	/**
	 * 更新照片
	 * @param Photo
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response updatePhoto(Photo address);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Photo getPhoto(@PathParam("id") int id);

	/**
	 * 获取最新四张图片
	 * @param id
	 * @return
	 */
	@GET
	@Path("/get4Photo")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Photos get4Photo();

	/**
	 * 获取最新四张图片
	 * @param id
	 * @return
	 */
	@GET
	@Path("/Photos")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Photos getPhotos();

}
