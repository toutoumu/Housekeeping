package com.housekeeping.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.housekeeping.entity.Captcha;

/**
 * 文件名: ICaptchaService.java</br>
 * 编写者: toutoumu</br>
 * 编写日期: 2014年7月25日</br>
 * 简要描述: 验证码服务</br>
 * 组件列表:</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 * 
 */
@Path("/Captcha")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface ICaptchaService {

	/**
	 * 获取验证码</br>
	 * 1.生成验证码</br>
	 * 2.发送验证码</br>
	 * 3.将验证码保存到数据库</br>
	 * 4.返回验证码</br>
	 * @param phoneNumber 手机号码
	 * @return  如果{@link Captcha}的{@link Captcha.getCode()}为0表示执行异常
	 */
	@GET
	@Path("/{phoneNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Captcha getCaptcha(@PathParam("phoneNumber") String phoneNumber);

	/**
	 * 验证验证码</br>
	 * 电话号码，验证码不能为空
	 * @param captcha {@link Captcha}
	 * @return {@link Response}
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Response validateCaptcha(Captcha captcha);

}
