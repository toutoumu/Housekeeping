package com.housekeeping.dao;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.IBaseDao;
import com.housekeeping.entity.User;

public interface IUserDao<T> extends IBaseDao<T, Serializable> {

	/**
	 * 查询用户(用户名和电话号码传一个即可）如果传id那么phoneNumber将被忽略
	 * @param id 主键
	 * @param phoneNumber 电话号码
	 * @return
	 */
	User getUser(int id, String phoneNumber);

	/**
	 * 获取所有用户
	 * @return
	 */
	List<User> getUsers(User user);

	/**
	 * 根据序号、会员名称、地址、手机号搜索会员
	 * @param searchContent
	 * @return
	 */
	List<User> search(String searchContent);

	/**
	 * 判断用户是否存在
	 * @param user
	 * @return
	 */
	boolean isExistUser(User user);

}
