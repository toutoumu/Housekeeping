package com.housekeeping.dao;

import com.hibernate.dao.generic.HibernateGenericDao;

public interface ITestBase {
	/**
	 * 保存对象.
	 */
	public void testSave();

	/**
	 * 在不同的session中关联修改过的托管对象
	 * @return 
	 */
	public void testUpdate();

	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 */
	public void testGet();

	/**
	 * 获取全部对象
	 * 
	 * @see HibernateGenericDao#getAll(Class)
	 */
	public void testGetAll();

	/**
	 * 删除对象.
	 */
	public void testRemove();

	/**
	 * 根据ID移除对象.
	 * @return 
	 */
	public void testRemoveById();

	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public void testFindBy();

	/**
	 * 根据属性名和属性值查询单个对象.
	 * 
	 * @return 符合条件的唯一对象 or null
	 */
	public void testFindUniqueBy();

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 */
	public void testFind();

	public void testExecuteNativeSql();

}