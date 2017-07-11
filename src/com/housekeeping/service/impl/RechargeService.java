package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IRechargeDao;
import com.housekeeping.entity.Recharge;
import com.housekeeping.entity.wrap.Recharges;
import com.housekeeping.service.IRechargeService;
import com.housekeeping.utils.ServiceErrorBuilder;
import com.housekeeping.utils.StringUtil;

public class RechargeService implements IRechargeService {

	private IRechargeDao<Recharge> rechargeDao;

	public void setRechargeDao(IRechargeDao<Recharge> rechargeDao) {
		this.rechargeDao = rechargeDao;
	}

	@Override
	public Response addRecharge(Recharge recharge) {
		if (recharge == null) {
			throw ServiceErrorBuilder.badRequestError("充值记录不能为空");
		}
		if (StringUtil.strIsEmpty(recharge.getId())) {
			throw ServiceErrorBuilder.badRequestError("主键不能为空");
		}
		if (recharge.getAmount() == 0) {
			throw ServiceErrorBuilder.badRequestError("充值金额不能为0");
		}
		if (recharge.getUserId() == 0) {
			throw ServiceErrorBuilder.badRequestError("充值用户不能为空");
		}

		// 检查充值记录是否已经存在
		String sql = "select count(*) from recharge where id = '" + recharge.getId() + "'";
		if (rechargeDao.isExist(sql)) {
			throw ServiceErrorBuilder.badRequestError("充值记录已经存在");
		}

		// 检查用户是否存在
		sql = "select count(*) from t_user where id =  " + recharge.getUserId();
		if (!rechargeDao.isExist(sql)) {
			throw ServiceErrorBuilder.badRequestError("用户不存在");
		}

		// 添加充值记录并更新余额
		recharge = rechargeDao.addRecharge(recharge);
		return Response.ok(recharge).build();

	}

	@Override
	public Response deleteRecharge(String id) {
		if (StringUtil.strIsEmpty(id)) {
			throw ServiceErrorBuilder.badRequestError("主键不能为空");
		}
		Recharge recharge = rechargeDao.get(id);
		if (recharge != null) {
			rechargeDao.remove(recharge);
			return Response.ok().build();
		}
		throw ServiceErrorBuilder.badRequestError("充值记录不存在");
	}

	@Override
	public Recharge getRecharge(String id) {
		if (StringUtil.strIsEmpty(id)) {
			throw ServiceErrorBuilder.badRequestError("主键不能为空");
		}
		return rechargeDao.get(id);
	}

	@Override
	public Recharges getRechargesByUserID(int userId) {
		if (userId == 0) {
			throw ServiceErrorBuilder.badRequestError("主键不能为空");
		}
		List<Recharge> rechargeList = rechargeDao.findBy("userId", userId);
		if (rechargeList != null && rechargeList.size() > 0) {
			Recharges recharges = new Recharges();
			recharges.setRecharges(rechargeList);
			return recharges;
		}
		return null;
	}

}
