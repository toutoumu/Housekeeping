package cn.jpush.api.push.remote;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 文件名: SingleTest.java
 * 编写者: toutoumu
 * 编写日期: 2014年8月9日
 * 简要描述: 注意这里继承了BaseRemoteTest类请先看BaseRemoteTest类
 * ********************  修改日志  **********************************
 * 修改人：           		  修改日期：
 * 修改内容：
 * 
 */
public class SingleTest extends BaseRemoteTest {

	@Test
	public void sendSimpleMessageAndNotification_Pall() throws Exception {
		PushPayload payload = PushPayload.newBuilder()//
				.setPlatform(Platform.all())//
				//.setAudience(Audience.all())//
				.setAudience(Audience.tag("159893489521"))//
				.setNotification(Notification.alert("Pall Nall Mall alert"))//
				.setMessage(Message.content("Pall Nall Mall msg")).build();
		PushResult result = _client.sendPush(payload);
		assertTrue(result.isResultOK());
	}

}
