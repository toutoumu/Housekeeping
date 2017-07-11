package com.housekeeping.service.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IUserDao;
import com.housekeeping.entity.User;
import com.housekeeping.entity.wrap.Users;
import com.housekeeping.service.IUserService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class UserService implements IUserService {

	private IUserDao<User> userDao;

	public void setUserDao(IUserDao<User> userDao) {
		this.userDao = userDao;
	}

	@Override
	public Response addUser(User user) {
		if (user != null && !StringUtil.strIsEmpty(user.getPhoneNumber())) {
			if (userDao.findUniqueBy("phoneNumber", user.getPhoneNumber()) != null) {
				throw ServiceErrorBuilder.serverError("手机号已经被注册");
			}
			user.setCreateTime(new Date());
			int id = (int) userDao.save(user);
			user.setId(id);
			return Response.ok(user).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加用户时，用户不能为空");
	}

	@Override
	public Response deleteUser(int id) {
		if (id != 0) {
			User user = userDao.get(id);
			if (user != null) {
				userDao.remove(user);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("该用户不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除用户时Id不能为空");
	}

	@Override
	public Response updateUser(User user) {
		if (user != null && user.getId() != 0) {
			if (userDao.update(user)) {
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("修改用户信息失败！");
		}
		throw ServiceErrorBuilder.badRequestError("修改用户时Id不能为空！");
	}

	@Override
	public User getUser(int id, String phoneNumber) {
		if (id != 0 || phoneNumber != null) {
			User retUser = userDao.getUser(id, phoneNumber);
			return retUser;
		}
		throw ServiceErrorBuilder.badRequestError("查询用户时Id和PhoneNumber不能同时为空！");
	}

	@Override
	public Users search(String searchContent) {
		if (!StringUtil.strIsEmpty(searchContent)) {
			List<User> userList = userDao.search(searchContent);
			if (userList != null) {
				Users users = new Users();
				users.setUsers(userList);
				return users;
			}
		}
		throw ServiceErrorBuilder.badRequestError("搜索内容不能为空");
	}

	@Override
	public Users getUsers(User user) {
		if (user == null) {
			throw ServiceErrorBuilder.badRequestError("查询用户时参数不能为空！");
		}
		List<User> userList = userDao.getUsers(user);
		if (userList == null) {
			return null;
		}
		Users users = new Users();
		users.setUsers(userList);
		return users;
	}

	@Override
	public boolean isExistUser(User user) {
		if (user == null) {
			return false;
		}
		return userDao.isExistUser(user);
	}
}
