package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IMessageDao;
import com.housekeeping.entity.Message;
import com.housekeeping.entity.wrap.Messages;
import com.housekeeping.service.IMessageService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class MessageService implements IMessageService {
	private IMessageDao<Message> messageDao;

	public void setMessageDao(IMessageDao<Message> messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public Response addMessage(Message message) {
		if (message != null) {
			int id = (int) messageDao.save(message);
			message.setId(id);
			return Response.ok(message).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加消息时消息不能为空");
	}

	@Override
	public Response deleteMessage(int id) {
		if (id != 0) {
			Message message = messageDao.get(id);
			if (message != null) {
				messageDao.remove(message);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("消息不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除消息时消息主键不能为空");
	}

	@Override
	public Response updateMessage(Message message) {
		if (message != null && message.getId() != 0) {
			if (messageDao.update(message)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新消息时消息主键不能为空");
	}

	@Override
	public Message getMessage(int id) {
		if (id != 0) {
			return messageDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询消息时消息主键不能为空");
	}

	@Override
	public Messages getMessagesByOrderId(String orderNumber) {
		if (!StringUtil.strIsEmpty(orderNumber)) {
			List<Message> msgList = messageDao.findBy("orderNumber", orderNumber);
			if (msgList != null) {
				Messages messages = new Messages();
				messages.setMessages(msgList);
				return messages;
			}
			return null;
		}
		throw ServiceErrorBuilder.badRequestError("根据订单查询消息时订单编号不能为空");
	}
}
