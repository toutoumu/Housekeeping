package com.housekeeping.entity.wrap;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.Photo;

/**
 * 文件名: Photos.java
 * 编写者: toutoumu
 * 编写日期: 2014年10月28日
 * 简要描述: 图片包装类用于返回多张图片
 * 组件列表:
 * ********************  修改日志  **********************************
 * 修改人：           		  修改日期：
 * 修改内容：
 * 
 */
@XmlRootElement(name = "Photos")
public class Photos {
	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	private List<Photo> photos;

}
