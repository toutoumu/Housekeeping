package com.housekeeping.dao;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.IBaseDao;
import com.housekeeping.entity.Photo;

public interface IPhotoDao<T> extends IBaseDao<T, Serializable> {

	List<Photo> get4Photo();
}
