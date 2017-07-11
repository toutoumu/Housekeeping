package com.housekeeping.dao;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Captcha;

public class CaptchaDaoTest extends TestCase {

	private ICaptchaDao<Captcha> captchaDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		captchaDao = (ICaptchaDao<Captcha>) ctx.getBean("captchaDao");
	}

	public void testAddCaptcha() {
		Captcha captcha = new Captcha();
		captcha.setCode(123457);
		captcha.setPhoneNumber("1124312");
		captchaDao.addCaptcha(captcha);
	}

	public void testGetCaptcha() {
		Captcha captcha = captchaDao.get("1124312");
		if (captcha != null) {
			System.out.println(captcha.getCode());
		}
	}

	public void testValidate() {
		Captcha captcha = new Captcha();
		captcha.setCode(123457);
		captcha.setPhoneNumber("1124312");
		System.out.println(captchaDao.validateCaptcha(captcha));
	}

}
