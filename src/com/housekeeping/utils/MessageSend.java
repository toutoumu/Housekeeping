package com.housekeeping.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.housekeeping.entity.Captcha;
import com.housekeeping.entity.Message;
import com.housekeeping.entity.Order;

/**
 * 文件名: MessageSend.java</br>
 * 编写者: 刘斌</br>
 * 编写日期: 2014年9月12日</br>
 * 简要描述: 短信消息发送辅助类</br>
 * 密码:19891121li 用户名:18673507440
 * 组件列表:
 * ********************  修改日志  **********************************
 * 修改人：           		  修改日期：
 * 修改内容：
 * 
 */
public class MessageSend {

	/*private static final String serverIP = "sandboxapp.cloopen.com";

	private static final String port = "8883";

	private static final String accountSid = "8a48b5514767145d0147702bae7a0354";

	private static final String accountToken = "4bf49b49a88e4ab68af12f817afd6eb4";

	private static final String appId = "aaf98f8947d7c8680147ed2023291c50";

	private static final String templateId = "1";*/

	// 下面这个是生产环境用的正式发布时候使用
	private static final String serverIP = "app.cloopen.com";

	private static final String port = "8883";

	private static final String accountSid = "8a48b5514767145d0147702bae7a0354";

	private static final String accountToken = "4bf49b49a88e4ab68af12f817afd6eb4";

	private static final String appId = "aaf98f8947a0321a0147a1892cdb00b4";

	private static final String templateId = "3436";

	/**
	 * 发送验证码，如果{@link Captcha}的code属性为0表示发送错误
	 * @param phoneNumber 电话号码"号码1,号码2等"
	 * @param code 验证码
	 * @return
	 */
	public static Captcha sendSMS(String phoneNumber, int code) {
		Captcha captcha = new Captcha();
		captcha.setCreateTime(new Date());
		captcha.setPhoneNumber(phoneNumber);

		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(serverIP, port);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		//restAPI.setAccount("accountSid", "accountToken");// 初始化主帐号名称和主帐号令牌
		restAPI.setAccount(accountSid, accountToken);// 初始化主帐号名称和主帐号令牌
		//restAPI.setAppId("AppId");// 初始化应用ID
		restAPI.setAppId(appId);// 初始化应用ID
		//result = restAPI.sendTemplateSMS("号码1,号码2等", "模板Id", new String[] { "模板内容1", "模板内容2" });
		result = restAPI.sendTemplateSMS(phoneNumber, templateId, new String[] { String.valueOf(code) });

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if ("000000".equals(result.get("statusCode"))) {
			//正常返回输出data包体信息（map）
			HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				Object object = data.get(key);
				System.out.println(key + " = " + object);
			}
			captcha.setMessage("验证码发送成功");
			captcha.setCode(code);
			return captcha;
		} else {
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			captcha.setCode(0);
			captcha.setMessage(result.get("statusMsg").toString());
			return captcha;
		}
	}

	/**
	 * 发送短信通知
	 * @param phoneNumber 电话号码（"号码1,号码2等"）
	 * @param templateId 短信模版id
	 * @param para1 短信模版填充参数
	 * @return
	 */
	public static boolean sendSMSByTemplate(String phoneNumber, String templateId, String... para) {

		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(serverIP, port);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		//restAPI.setAccount("accountSid", "accountToken");// 初始化主帐号名称和主帐号令牌
		restAPI.setAccount(accountSid, accountToken);// 初始化主帐号名称和主帐号令牌
		//restAPI.setAppId("AppId");// 初始化应用ID
		restAPI.setAppId(appId);// 初始化应用ID
		//result = restAPI.sendTemplateSMS("号码1,号码2等", "模板Id", new String[] { "模板内容1", "模板内容2" });
		result = restAPI.sendTemplateSMS(phoneNumber, templateId, para);

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if ("000000".equals(result.get("statusCode"))) {
			//正常返回输出data包体信息（map）
			HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				Object object = data.get(key);
				System.out.println(key + " = " + object);
			}
			System.out.println("信息发送成功");
			return true;
		} else {
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			return false;
		}
	}

	/**
	 * 客户订单短信
	 * 1	家庭保洁
	*  2	新居开荒
	*  3	家电清洗
	*  4	洗护服务
	*  5	代驾
	 * @param key
	 * @return
	 */
	public static List<Message> getSMSMessages(Order order) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Message> messageList = new ArrayList<Message>();
		StringBuilder parameter = null;
		Message message = null;
		switch (order.getBusinessCategoryId()) {
			case 1:
				//保洁业务客户订单短信 "3438"	
				/*【微生活】尊敬的{1}，您好，您提交的家庭保洁业务订单{2}已经预定成功。
				保洁员：{3}；保洁员电话：{4}；预定服务时间：{5}；订单工时：{6}；支付方式：{7}。
				如有变动，请提前与保洁员联系协商，或者联系客服400-012-8022*/
				message = new Message();
				message.setMessageType(1);//短信
				message.setFinish(false);//未完成
				message.setOrderNumber(order.getOrderNumber()); // 订单编号
				message.setPhoneNumber(order.getPhoneNumber()); // 短信发送到此手机上
				message.setTime(order.getStartTime()); // 服务开始时间
				message.setTemplateId("3438");// 短信模板ID
				// 拼接参数
				parameter = new StringBuilder();
				parameter.append(order.getUserName());
				parameter.append(",");
				parameter.append(order.getOrderNumber());
				parameter.append(",");
				parameter.append(order.getEmployeeName());
				parameter.append(",");
				parameter.append(order.getEmployeePhoneNumber());
				parameter.append(",");
				parameter.append(format.format(order.getStartTime()));
				parameter.append(",");
				parameter.append(order.getWorkingTime());
				parameter.append(",");
				parameter.append(order.getPayway());
				message.setParameters(parameter.toString());// 短信模板参数
				messageList.add(message);

				// 保洁业务员工订单短信"3441"
				/*【微生活】您好，{1}保洁员。家庭保洁业务订单{2}已经预定成功。
				 客户：{3}；电话：{4}；地址：{5}；预定时间：{6}；支付方式：{7}；订单工时：{8}；
				客户需求：{9}。请提前上门，必须在预定时间之前赶到服务地址，如有疑问请提前联系客户或客服。*/
				message = new Message();
				message.setMessageType(1);//短信
				message.setFinish(false);//未完成
				message.setOrderNumber(order.getOrderNumber()); // 订单编号
				message.setPhoneNumber(order.getEmployeePhoneNumber()); // 短信发送到此手机上
				message.setTime(order.getStartTime()); // 服务开始时间
				message.setTemplateId("3441");// 短信模板ID
				// 拼接模板参数
				parameter = new StringBuilder();
				parameter.append(order.getEmployeeName());
				parameter.append(",");
				parameter.append(order.getOrderNumber());
				parameter.append(",");
				parameter.append(order.getUserName());
				parameter.append(",");
				parameter.append(order.getPhoneNumber());
				parameter.append(",");
				parameter.append(order.getAddress());
				parameter.append(",");
				parameter.append(format.format(order.getStartTime()));
				parameter.append(",");
				parameter.append(order.getPayway());
				parameter.append(",");
				parameter.append(order.getWorkingTime());
				parameter.append(",");
				parameter.append(order.getRemark());

				message.setParameters(parameter.toString());// 短信模板参数
				messageList.add(message);
				break;
			case 2:
				//新居开荒客户订单短信"3848"
				/*【微生活】尊敬的{1}，您好，您提交的新居开荒业务订单{2}已经预定成功。
				预定服务时间：{2}；服务面积：{3}；订单金额：{4}；支付方式：{5}。
				如有变动，请提前联系客服400-012-8022*/
				message = new Message();
				message.setMessageType(1);//短信
				message.setFinish(false);//未完成
				message.setOrderNumber(order.getOrderNumber()); // 订单编号
				message.setPhoneNumber(order.getPhoneNumber()); // 短信发送到此手机上
				message.setTime(order.getStartTime()); // 服务开始时间
				message.setTemplateId("3848");// 短信模板ID
				// 拼接参数
				parameter = new StringBuilder();
				parameter.append(order.getUserName());
				parameter.append(",");
				parameter.append(order.getOrderNumber());
				parameter.append(",");
				parameter.append(format.format(order.getStartTime()));
				parameter.append(",");
				parameter.append(order.getArea());
				parameter.append(",");
				parameter.append(order.getOrderPrice());
				parameter.append(",");
				parameter.append(order.getPayway());

				message.setParameters(parameter.toString());// 短信模板参数
				messageList.add(message);
				break;
			case 3:
				//家电清洗业务客户订单短信"3669"
				/*【微生活】尊敬的{1}，您好，您提交的家电清洗业务订单{2}已经预定成功。
				预定服务时间：{3}；订单金额：{4}；支付方式：{5}；。
				如有变动，请提前与保洁员联系协商，或者联系客服400-012-8022*/
				message = new Message();
				message.setMessageType(1);//短信
				message.setFinish(false);//未完成
				message.setOrderNumber(order.getOrderNumber()); // 订单编号
				message.setPhoneNumber(order.getPhoneNumber()); // 短信发送到此手机上
				message.setTime(order.getStartTime()); // 服务开始时间
				message.setTemplateId("3848");// 短信模板ID
				// 拼接参数
				parameter = new StringBuilder();
				parameter.append(order.getUserName());
				parameter.append(",");
				parameter.append(order.getOrderNumber());
				parameter.append(",");
				parameter.append(format.format(order.getStartTime()));
				parameter.append(",");
				parameter.append(order.getOrderPrice());
				parameter.append(",");
				parameter.append(order.getPayway());

				message.setParameters(parameter.toString());// 短信模板参数
				messageList.add(message);
				break;
			case 4:
				//干洗业务客户订单短信"3847"
				/*【微生活】尊敬的{1}，您好，您提交的洗护业务订单{2}已经预定成功。
				预定服务时间：{3}；订单金额：{4}；支付方式：{5}。
				如有变动，请提前联系客服400-012-8022*/
				message = new Message();
				message.setMessageType(1);//短信
				message.setFinish(false);//未完成
				message.setOrderNumber(order.getOrderNumber()); // 订单编号
				message.setPhoneNumber(order.getPhoneNumber()); // 短信发送到此手机上
				message.setTime(order.getStartTime()); // 服务开始时间
				message.setTemplateId("3848");// 短信模板ID
				// 拼接参数
				parameter = new StringBuilder();
				parameter.append(order.getUserName());
				parameter.append(",");
				parameter.append(order.getOrderNumber());
				parameter.append(",");
				parameter.append(format.format(order.getStartTime()));
				parameter.append(",");
				parameter.append(order.getOrderPrice());
				parameter.append(",");
				parameter.append(order.getPayway());

				message.setParameters(parameter.toString());// 短信模板参数
				messageList.add(message);
				break;
			default:
				break;
		}
		return messageList;
	}

	/**
	 * 获取时间通知短信
	 * 1	家庭保洁
	*  2	新居开荒
	*  3	家电清洗
	*  4	洗护服务
	*  5	代驾
	 * @param key
	 * @return
	 */
	public static List<Message> getDelaySMSMessages(Order order) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Message> messageList = new ArrayList<Message>();
		StringBuilder parameter = null;
		Message message = null;
		switch (order.getBusinessCategoryId()) {
			case 1:
				// 保洁业务服务时间通知短信"3851"
				/*【微生活】尊敬的{1}，您好，家庭保洁业务订单{2}还有一小时到达预定的服务时间：{3}，请注意时间安排和准备。
				如有变动，请提前与对方联系协商，或者联系客服400-012-8022*/
				message = new Message();
				message.setMessageType(1);//短信
				message.setFinish(false);//未完成
				message.setOrderNumber(order.getOrderNumber()); // 订单编号
				message.setPhoneNumber(order.getPhoneNumber()); // 短信发送到此手机上
				message.setTime(order.getStartTime()); // 服务开始时间
				message.setTemplateId("3851");// 短信模板ID
				// 拼接模板参数
				parameter = new StringBuilder();
				parameter.append(order.getUserName());
				parameter.append(",");
				parameter.append(order.getOrderNumber());
				parameter.append(",");
				parameter.append(format.format(order.getStartTime()));

				message.setParameters(parameter.toString());// 短信模板参数
				messageList.add(message);
				break;
			default:
				break;
		}
		return messageList;
	}
}
