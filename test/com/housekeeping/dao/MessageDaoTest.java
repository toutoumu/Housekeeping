package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Message;

public class MessageDaoTest extends TestCase implements ITestBase {
	private IMessageDao<Message> messageDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		messageDao = (IMessageDao<Message>) ctx.getBean("messageDao");
	}

	@Override
	public void testSave() {
		Message message = new Message();
		message.setParameters("asdf");
		message.setTemplateId("asd");
		System.out.println(messageDao.save(message));
	}

	@Override
	public void testUpdate() {
		Message message = new Message();
		message.setId(1);
		message.setParameters("asdf");
		message.setTemplateId("asd");
		messageDao.update(message);
	}

	@Override
	public void testGet() {
		Message message = messageDao.get(1);
		if (message != null) {
			System.out.println(message.getParameters());
		}
	}

	@Override
	public void testGetAll() {
		List<Message> messages = messageDao.getAll();
		if (messages != null) {
			for (Message message : messages) {
				System.out.println(message.getParameters());
			}
		}
	}

	@Override
	public void testRemove() {
		Message message = messageDao.get(1);
		if (message != null) {
			messageDao.remove(message);
		}
	}

	@Override
	public void testRemoveById() {
		messageDao.removeById(1);
	}

	@Override
	public void testFindBy() {
		List<Message> messages = messageDao.findBy("parameters", "asdf");
		if (messages != null) {
			for (Message message : messages) {
				System.out.println(message.getParameters());
			}
		}
	}

	@Override
	public void testFindUniqueBy() {
		Message message = messageDao.findUniqueBy("parameters", "asdf");
		if (message != null) {
			System.out.println(message.getParameters());

		}
	}

	@Override
	public void testFind() {
		List<Message> messages = (List<Message>) messageDao.find("from Message", null);
		if (messages != null) {
			for (Message message : messages) {
				System.out.println(message.getParameters());
			}
		}
	}

	@Override
	public void testExecuteNativeSql() {
		List list = messageDao.executeNativeSql("select count(*) from message");
		if (list != null) {
			for (Object object : list) {
				System.out.println(object);
			}
		}
	}

	public void testAA() {
		List<Message> messages = messageDao.getUnNotifyMessage();
		if (messages != null) {
			for (Message message : messages) {
				System.out.println(message.getId());
			}
		}
	}
}
