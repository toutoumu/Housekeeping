package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.ICaptchaDao;
import com.housekeeping.entity.Captcha;

public class CaptchaDao<T> extends BaseDao<T, Serializable> implements ICaptchaDao<T> {

	public CaptchaDao(Class<T> type) {
		super(type);
	}

	public Captcha addCaptcha(Captcha captcha) {
		// 如果电话号码和验证码为空则返回null
		if (captcha == null || captcha.getPhoneNumber() == null || captcha.getPhoneNumber().trim().equals("")) {
			return null;
		}
		if (captcha.getCode() == 0) {
			return null;
		}
		// 查询是否已经存在验证码,如果存在则更新时间和验证码
		Captcha savedCaptcha = this.getHibernateTemplate().get(Captcha.class, captcha.getPhoneNumber());
		if (savedCaptcha != null) {
			savedCaptcha.setCreateTime(new Date());
			savedCaptcha.setCode(captcha.getCode());
			savedCaptcha.setMessage(captcha.getMessage());
			this.getHibernateTemplate().update(savedCaptcha);
			return savedCaptcha;
		}
		// 如果不存在则保存进去
		captcha.setCreateTime(new Date());
		this.getHibernateTemplate().save(captcha);
		return captcha;
	}

	@Override
	public boolean validateCaptcha(Captcha captcha) {
		// 如果电话号码或者验证码为空,返回false
		if (captcha == null || captcha.getPhoneNumber() == null || captcha.getPhoneNumber().trim().equals("")) {
			return false;
		}
		if (captcha.getCode() == 0) {
			return false;
		}
		// 获取保存的验证码,验证验证码是否正确,且时间在1分钟之内
		Captcha savedCaptcha = this.getHibernateTemplate().get(Captcha.class, captcha.getPhoneNumber());
		if (savedCaptcha != null) {
			if (savedCaptcha.getCode() == captcha.getCode()) {
				Calendar savedTime = Calendar.getInstance(Locale.getDefault());
				savedTime.setTime(savedCaptcha.getCreateTime());
				savedTime.add(Calendar.MINUTE, 1);
				Calendar currentTime = Calendar.getInstance(Locale.getDefault());
				currentTime.setTime(new Date());
				// 如果保存时间加上一分钟在当前时间之后那么返回true
				if (savedTime.after(currentTime)) {
					return true;
				}
			}
		}
		return false;
	}

}
