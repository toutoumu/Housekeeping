package com.housekeeping.service.impl;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.ICaptchaDao;
import com.housekeeping.entity.Captcha;
import com.housekeeping.service.ICaptchaService;
import com.housekeeping.utils.MessageSend;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class CaptchaService implements ICaptchaService {

	private ICaptchaDao<Captcha> captchaDao;

	public void setCaptchaDao(ICaptchaDao<Captcha> captchaDao) {
		this.captchaDao = captchaDao;
	}

	@Override
	public Captcha getCaptcha(String phoneNumber) {
		// 生成验证码
		int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
		// 发送验证码
		Captcha captcha = MessageSend.sendSMS(phoneNumber, mobile_code);
		// 保存验证码到数据库
		if (captcha != null && captcha.getCode() != 0) {
			captcha = captchaDao.addCaptcha(captcha);
		}
		// 返回验证码
		return captcha;
	}

	@Override
	public Response validateCaptcha(Captcha captcha) {
		if (captcha != null && captcha.getCode() != 0 && !StringUtil.strIsEmpty(captcha.getPhoneNumber())) {
			if (captchaDao.validateCaptcha(captcha)) {
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("验证失败");
		}
		throw ServiceErrorBuilder.badRequestError("验证码和手机号不能为空");
	}

}
