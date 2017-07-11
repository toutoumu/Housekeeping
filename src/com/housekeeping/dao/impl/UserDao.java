package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.Assert;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IUserDao;
import com.housekeeping.entity.User;

public class UserDao<T> extends BaseDao<T, Serializable> implements IUserDao<T> {

	public UserDao(Class<T> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUser(int id, String phoneNumber) {
		if (id != 0) {
			return this.getHibernateTemplate().get(User.class, id);
		}
		Assert.hasText(phoneNumber);
		List<User> users = (List<User>) findBy("phoneNumber", phoneNumber);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(User user) {
		if (user == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder("select user from User as user where 1=1 ");
		if (user.getId() != 0) {
			builder.append(" and");
			builder.append(" user.id=");
			builder.append(user.getId());
		}
		/*if (user.getAddress() != null && !"".equals(user.getAddress())) {
			builder.append(" and");
			builder.append(" user.address=");
			builder.append("'");
			builder.append(user.getAddress());
			builder.append("'");
		}*/
		if (user.getCity() != null && !"".equals(user.getCity())) {
			builder.append(" and");
			builder.append(" user.city=");
			builder.append("'");
			builder.append(user.getCity());
			builder.append("'");
		}
		if (user.getPhoneNumber() != null && !"".equals(user.getPhoneNumber())) {
			builder.append(" and");
			builder.append(" user.phoneNumber=");
			builder.append("'");
			builder.append(user.getPhoneNumber());
			builder.append("'");
		}
		if (user.getUserName() != null && !"".equals(user.getUserName())) {
			builder.append(" and");
			builder.append(" user.userName=");
			builder.append("'");
			builder.append(user.getUserName());
			builder.append("'");
		}
		if (user.getPassword() != null && !"".equals(user.getPassword())) {
			builder.append(" and");
			builder.append(" user.password=");
			builder.append("'");
			builder.append(user.getPassword());
			builder.append("'");
		}
		if (user.getScore() != 0) {
			builder.append(" and");
			builder.append(" user.score=");
			builder.append(user.getScore());
		}
		if (user.getBalance() != 0) {
			builder.append(" and");
			builder.append(" user.balance=");
			builder.append(user.getBalance());
		}
		if (user.getGrade() != 0) {
			builder.append(" and");
			builder.append(" user.grade=");
			builder.append(user.getGrade());
		}
		return (List<User>) find(builder.toString(), null);
	}
	
	
	@Override
	public List<User> search(String searchContent) {
		//序号、会员名称、地址、手机号
		StringBuilder builder = new StringBuilder();
		builder.append("select e from User e, Address a where e.id = a.userId and(");
		// 序号
		builder.append("e.id like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 会员名称
		builder.append("e.userName like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 地址
		builder.append("a.address like '%");
		builder.append(searchContent);
		builder.append("%'");
		builder.append(" or ");

		// 手机号
		builder.append("e.phoneNumber like '%");
		builder.append(searchContent);
		builder.append("%')");
		//builder.append(" or ");
		System.out.println(builder.toString());
		return (List<User>) find(builder.toString(), null);
	}

	@Override
	public boolean isExistUser(User user) {
		if (user == null) {
			return false;
		}
		StringBuilder builder = new StringBuilder("select count(user) from User as user where 1=1 ");
		if (user.getId() != 0) {
			builder.append(" and");
			builder.append(" user.id=");
			builder.append(user.getId());
		}
		/*if (user.getAddress() != null && !"".equals(user.getAddress())) {
			builder.append(" and");
			builder.append(" user.address=");
			builder.append("'");
			builder.append(user.getAddress());
			builder.append("'");
		}*/
		if (user.getCity() != null && !"".equals(user.getCity())) {
			builder.append(" and");
			builder.append(" user.city=");
			builder.append("'");
			builder.append(user.getCity());
			builder.append("'");
		}
		if (user.getPhoneNumber() != null && !"".equals(user.getPhoneNumber())) {
			builder.append(" and");
			builder.append(" user.phoneNumber=");
			builder.append("'");
			builder.append(user.getPhoneNumber());
			builder.append("'");
		}
		if (user.getUserName() != null && !"".equals(user.getUserName())) {
			builder.append(" and");
			builder.append(" user.userName=");
			builder.append("'");
			builder.append(user.getUserName());
			builder.append("'");
		}
		if (user.getPassword() != null && !"".equals(user.getPassword())) {
			builder.append(" and");
			builder.append(" user.password=");
			builder.append("'");
			builder.append(user.getPassword());
			builder.append("'");
		}
		if (user.getScore() != 0) {
			builder.append(" and");
			builder.append(" user.score=");
			builder.append(user.getScore());
		}
		if (user.getBalance() != 0) {
			builder.append(" and");
			builder.append(" user.balance=");
			builder.append(user.getBalance());
		}
		if (user.getGrade() != 0) {
			builder.append(" and");
			builder.append(" user.grade=");
			builder.append(user.getGrade());
		}
		System.out.println(builder.toString());
		List<?> retCount = find(builder.toString(), null);
		if (retCount != null) {
			return Integer.parseInt(retCount.get(0).toString()) > 0;
		}
		return false;
	}
}
