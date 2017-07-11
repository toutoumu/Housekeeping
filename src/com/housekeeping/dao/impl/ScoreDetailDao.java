package com.housekeeping.dao.impl;

import java.io.Serializable;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IScoreDetailDao;

public class ScoreDetailDao<T> extends BaseDao<T, Serializable> implements IScoreDetailDao<T> {

	public ScoreDetailDao(Class<T> type) {
		super(type);
 	}

}
