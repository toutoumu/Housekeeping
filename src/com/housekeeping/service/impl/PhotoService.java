package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IPhotoDao;
import com.housekeeping.entity.Photo;
import com.housekeeping.entity.wrap.Photos;
import com.housekeeping.service.IPhotoService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class PhotoService implements IPhotoService {
	private IPhotoDao<Photo> photoDao;

	public void setPhotoDao(IPhotoDao<Photo> addressDao) {
		this.photoDao = addressDao;
	}

	@Override
	public Response addPhoto(Photo address) {
		if (address != null) {
			int id = (int) photoDao.save(address);
			address.setId(id);
			return Response.ok(address).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加照片失败");
	}

	@Override
	public Response deletePhoto(int id) {
		if (id != 0) {
			Photo address = photoDao.get(id);
			if (address != null) {
				photoDao.remove(address);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("照片不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除照片时主键不能为空");
	}

	@Override
	public Response updatePhoto(Photo address) {
		if (address != null && address.getId() != 0) {
			if (photoDao.update(address)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新照片时主键不能为空");
	}

	@Override
	public Photo getPhoto(int id) {
		if (id != 0) {
			return photoDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询照片时主键不能为空");
	}

	@Override
	public Photos get4Photo() {
		List<Photo> photoList = photoDao.get4Photo();
		if (photoList == null || photoList.size() == 0) {
			return null;
		}
		Photos photos = new Photos();
		photos.setPhotos(photoList);
		return photos;
	}

	@Override
	public Photos getPhotos() {

		List<Photo> photoList = photoDao.getAll();
		if (photoList == null || photoList.size() == 0) {
			return null;
		}
		Photos photos = new Photos();
		photos.setPhotos(photoList);
		return photos;
	}
}
