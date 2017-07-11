package com.housekeeping.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.api.Constant;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;

import com.housekeeping.dao.IMessageDao;
import com.housekeeping.entity.Message;
import com.housekeeping.utils.MessageSend;

/**
 * 文件名: TestJobSpring.java
 * 编写者: 刘斌</br>
 * 编写日期: 2014年9月12日</br>
 * 简要描述: 消息推送以及短信通知服务</br>
 * 组件列表:
 * ********************  修改日志  **********************************
 * 修改人：           		  修改日期：
 * 修改内容：
 * 
 */
public class TestJobSpring {

	public static final String appKey = Constant.appKey;

	public static final String masterSecret = Constant.masterSecret;

	//PushClient _client = new PushClient(masterSecret, appKey);

	JPushClient _client = new JPushClient(masterSecret, appKey);

	private IMessageDao<Message> messageDao;

	public void setMessageDao(IMessageDao<Message> messageDao) {
		this.messageDao = messageDao;
	}

	public void test() {
		List<Message> messages = messageDao.getUnNotifyMessage();
		if (messages != null) {
			for (Message message : messages) {
				try {
					// 推送消息
					if (message.getMessageType() == 2) {
						System.out.println("推送消息");

						/*PushPayload payload = PushPayload.newBuilder()//
								.setPlatform(Platform.all())//
								.setAudience(Audience.tag(message.getPhoneNumber()))//
								.setNotification(Notification.alert(message.getParameters()))//							
								.setMessage(cn.jpush.api.push.model.Message.content(message.getParameters()))//
								.build();
						PushResult result = _client.sendPush(payload);*/
						Map<String, String> extras = new HashMap<String, String>();
						extras.put("orderNumber", message.getOrderNumber());
						PushResult result = _client.sendAndroidNotificationWithRegistrationID(message.getParameters(),
								message.getParameters(), extras, message.getPhoneNumber());
						if (!result.isResultOK()) {
							return;
						}
					}
					// 短信通知
					else if (message.getMessageType() == 1) {
						boolean success = MessageSend.sendSMSByTemplate(message.getPhoneNumber(),
								message.getTemplateId(), message.getParameters().split(","));
						if (!success) {
							return;
						}
					}
					System.out.println("发送消息内容为:" + message.getParameters());
					//更新消息为已经发送
					message.setFinish(true);
					messageDao.update(message);
				} catch (Exception e) {
					System.out.println("消息发送出错了");
					e.printStackTrace();
				}
			}
		}
	}
}
