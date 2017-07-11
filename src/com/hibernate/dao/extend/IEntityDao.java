package com.hibernate.dao.extend;

import java.io.Serializable;
import java.util.List;

/**
 * 针对单个Entity对象的CRUD操作定义.
 */
public interface IEntityDao<T, PK extends Serializable> {
	/**
	 * 根据主键查找对象
	 * @param id 主键
	 * @return
	 */
	T get(PK id);

	/**
	 * 获取所有对象
	 * @return
	 */
	List<T> getAll();

	/**
	 * 保存对象并返回主键
	 * @param entity java对象
	 * @return
	 */
	Serializable save(T entity);

	/**
	 * 删除java对象
	 * @param entity
	 */
	void remove(T entity);

	/**
	 * 根据主键删除java对象
	 * @param id
	 */
	void removeById(PK id);

	/**
	 * 更新java对象
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 获取Entity对象的主键名.
	 */
	String getIdName(Class<T> clazz);
}
