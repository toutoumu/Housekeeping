package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IPhotoDao;
import com.housekeeping.entity.Photo;

public class PhotoDao<T> extends BaseDao<T, Serializable> implements IPhotoDao<T> {

	public PhotoDao(Class<T> type) {
		super(type);
	}

	@Override
	public List<Photo> get4Photo() {
		Query query = createQuery("from Photo order by id desc");
		query.setMaxResults(4);
		return  query.list();
	}
}
