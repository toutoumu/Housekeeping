package com.housekeeping.dao;

import java.io.Serializable;

import com.hibernate.dao.base.IBaseDao;
import com.housekeeping.entity.Captcha;

public interface ICaptchaDao<T> extends IBaseDao<T, Serializable> {
	/**
	 * 添加验证码如果手机号对应的验证码已经存在那么更新
	 * @param captcha
	 * @return
	 */
	Captcha addCaptcha(Captcha captcha);

	boolean validateCaptcha(Captcha captcha);
}
