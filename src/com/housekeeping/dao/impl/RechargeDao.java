package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.Date;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IRechargeDao;
import com.housekeeping.entity.Recharge;

public class RechargeDao<T> extends BaseDao<T, Serializable> implements IRechargeDao<T> {

	public RechargeDao(Class<T> type) {
		super(type);
	}

	@Override
	public Recharge addRecharge(Recharge recharge) {
		/*if (recharge == null) {
			return null;
		}
		if (StringUtil.strIsEmpty(recharge.getId()) || recharge.getAmount() == 0 || recharge.getUserId() == 0) {
			return null;
		}
		// 检查充值记录是否已经存在
		String sql = "select count(*) from recharge where id = '" + recharge.getId() + "'";
		if (isExist(sql)) {
			System.out.println("充值记录已经存在");
			throw new RuntimeException("充值记录已经存在");
		}

		// 检查用户是否存在
		sql = "select count(*) from t_user where id =  " + recharge.getUserId();
		if (!isExist(sql)) {
			System.out.println("用户不存在");
			throw new RuntimeException("用户不存在");
		}*/

		// 更新用户金额
		String sql = "update t_user set balance = balance+" + recharge.getAmount() + " where id =  "
				+ recharge.getUserId();
		int count = executeNativeSqlUpdate(sql);
		if (count == 0) {
			System.out.println("更新余额失败");
			return null;
		}
		recharge.setTime(new Date());
		save((T) recharge);
		return recharge;
	}
}
