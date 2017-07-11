package com.housekeeping.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.housekeeping.entity.Recharge;
import com.housekeeping.entity.wrap.Recharges;

/**
 * 
 * 文件名: IRechargeService.java</br>
 * 编写者: toutoumu</br>
 * 编写日期: 2014年9月4日</br>
 * 简要描述: 充值管理服务</br>
 * 组件列表:
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 *
 */
@Path("Recharge")
public interface IRechargeService {

	/**
	 * 添加充值记录,并更新余额
	 * @param Recharge
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response addRecharge(Recharge recharge);

	/**
	 * 删除充值记录
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response deleteRecharge(@PathParam("id") String id);

	/**
	 * 根据主键查询充值记录
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Recharge getRecharge(@PathParam("id") String id);

	/**
	 * 查询用户的充值记录
	 * @param id
	 * @return
	 */
	@GET
	@Path("/RechargesByUserID/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Recharges getRechargesByUserID(@PathParam("id") int userId);
}
