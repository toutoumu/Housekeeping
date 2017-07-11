package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IScoreDetailDao;
import com.housekeeping.entity.ScoreDetail;
import com.housekeeping.entity.wrap.ScoreDetails;
import com.housekeeping.service.IScoreDetailService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class ScoreDetailService implements IScoreDetailService {

	IScoreDetailDao<ScoreDetail> scoreDetailDao;

	public void setScoreDetailDao(IScoreDetailDao<ScoreDetail> scoreDetailDao) {
		this.scoreDetailDao = scoreDetailDao;
	}

	@Override
	public Response addScoreDetail(ScoreDetail scoreDetail) {
		if (scoreDetail == null) {
			throw ServiceErrorBuilder.badRequestError("不能为空");
		}
		if (scoreDetail.getUserId() == 0) {
			throw ServiceErrorBuilder.badRequestError("用户ID不能为空");
		}
		int id = (int) scoreDetailDao.save(scoreDetail);
		scoreDetail.setId(id);
		return Response.ok(scoreDetail).build();
	}

	@Override
	public Response deleteScoreDetail(int id) {
		if (id != 0) {
			ScoreDetail detail = scoreDetailDao.get(id);
			if (detail != null) {
				scoreDetailDao.remove(detail);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("明细不存在");
		}
		throw ServiceErrorBuilder.badRequestError("主键不能为空");
	}

	@Override
	public Response updateScoreDetail(ScoreDetail scoreDetail) {
		if (scoreDetail != null & scoreDetail.getId() != 0) {
			if (scoreDetailDao.update(scoreDetail)) {
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("修改明细信息失败");
		}
		throw ServiceErrorBuilder.badRequestError("修改明细时Id不能为空");

	}

	@Override
	public ScoreDetail getScoreDetail(int id) {
		if (id != 0) {
			ScoreDetail detail = scoreDetailDao.get(id);
			return detail;
		}
		throw ServiceErrorBuilder.badRequestError("主键不能为空");
	}

	@Override
	public ScoreDetails getScoreDetailByUserId(int userId) {
		List<ScoreDetail> detailList = scoreDetailDao.findBy("userId", userId);
		if (detailList == null) {
			return null;
		}
		ScoreDetails details = new ScoreDetails();
		details.setScoreDetails(detailList);
		return details;
	}

}
