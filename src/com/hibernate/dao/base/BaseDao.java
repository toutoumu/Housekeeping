package com.hibernate.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.hibernate.dao.extend.HibernateEntityDao;
import com.hibernate.dao.generic.HibernateGenericDao;
import com.hibernate.dao.support.Page;

/**
 * 提供hibernate dao的所有操作,<br>
 * 实现类由spring注入HibernateEntityDao和HibernateGenericDao来实现
 * 
 */
public class BaseDao<T, PK extends Serializable> implements IBaseDao<T, PK> {
	protected Class<T> entityClass;// DAO所管理的Entity类型.

	private HibernateEntityDao<T, PK> entityDao;

	private HibernateGenericDao genericDao;

	public void setEntityDao(HibernateEntityDao<T, PK> entityDao) {
		this.entityDao = entityDao;
		this.entityDao.setEntityClass(entityClass);
	}

	public void setGenericDao(HibernateGenericDao genericDao) {
		this.genericDao = genericDao;
	}

	/**
	 *让spring提供构造函数注入
	 */
	public BaseDao(Class<T> type) {
		this.entityClass = type;
	}

	/**
	 * 清除所有对象缓存
	 */
	@Override
	public void clear() {
		genericDao.clear();
	}

	/**
	 * 创建Criteria对象.
	 * @param criterions 可变的Restrictions条件列表
	 */
	@Override
	public Criteria createCriteria(Criterion... criterions) {
		return entityDao.createCriteria(criterions);
	}

	@Override
	public Criteria createCriteria(List<Criterion> criterions) {
		return entityDao.createCriteria(criterions);
	}

	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 */
	@Override
	public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		return entityDao.createCriteria(orderBy, isAsc, criterions);
	}

	@Override
	public Criteria createCriteria(String[] asc, String[] desc, List<Criterion> criterions) {
		return entityDao.createCriteria(asc, desc, criterions);
	}

	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 调用方式如下：
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 *
	 * @param values 可变参数.
	 */
	@Override
	public Query createQuery(String hql, Object... values) {
		return genericDao.createQuery(hql, values);
	}

	/**
	 * @param hql 查询sql
	 * @param start 分页从哪一条数据开始
	 * @param pageSize 每一个页面的大小
	 * @param values 查询条件
	 * @return page对象
	 */
	@Override
	public Page dataQuery(String hql, int start, int pageSize, Object... values) {
		return genericDao.dataQuery(hql, start, pageSize, values);
	}

	/**
	 * 消除与 Hibernate Session 的关联
	 * @param entity
	 */
	@Override
	public void evit(T entity) {

		entityDao.evict(entity);
	}

	/**
	 * 执行本地sql语句获得标量数值列表
	 */
	@Override
	public List executeNativeSql(String sql) {

		return genericDao.executeNativeSql(sql);
	}

	/**
	 * 执行本地sql语句更新数据
	 */
	@SuppressWarnings("deprecation")
	public int executeNativeSqlUpdate(String sql) {
		return genericDao.executeNativeSqlUpdate(sql);
	}

	@Override
	public boolean isExist(String sql) {
		List<?> list = genericDao.executeNativeSql(sql);
		if (list != null) {
			return Integer.parseInt(list.get(0).toString()) > 0;
		}
		return false;
	}

	/**
	* 根据hql查询,直接使用HibernateTemplate的find函数.
	* @param values 可变参数
	*/
	@Override
	public List<?> find(String hql, Object... values) {
		return genericDao.find(hql, values);
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * @return 符合条件的对象列表
	 */
	@Override
	public List<T> findBy(String propertyName, Object value) {

		return entityDao.findBy(propertyName, value);
	}

	@Override
	public List<T> findBy(List<String> propertyNames, Object... value) {
		return entityDao.findBy(propertyNames, value);
	}

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	@Override
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {

		return entityDao.findBy(propertyName, value, orderBy, isAsc);
	}

	@Override
	public List<T> findBy(List<String> propertyNames, List<Object> value, String[] asc, String[] desc) {
		return entityDao.findBy(propertyNames, value, asc, desc);
	}

	/**
	 * 根据属性名和属性值查询唯一对象.
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	@Override
	public T findUniqueBy(String propertyName, Object value) {

		return entityDao.findUniqueBy(propertyName, value);
	}

	/**
	 * 执行一些必须的sql语句把内存中的对象同步到jdbc的链接中
	 */
	@Override
	public void flush() {

		genericDao.flush();
	}

	/**
	 * 根据Serializable类型的id获取实体对象<p/>
	 * 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 * @param id
	 */
	@Override
	public T get(PK id) {
		return entityDao.get(id);
	}

	/**
	 * 获取实体类型的全部对象
	 */
	@Override
	public List<T> getAll() {
		return entityDao.getAll();
	}

	/**
	 * 获取全部对象,带排序字段与升降序参数.
	 */
	@Override
	public List<T> getAll(String orderBy, boolean isAsc) {

		return entityDao.getAll(orderBy, isAsc);
	}

	@Override
	public List<T> getAll(String asc, String desc) {
		return entityDao.getAll(asc, desc);
	}

	/**
	 * 直接使用spring提供的HibernateTemplate
	 */
	@Override
	public HibernateTemplate getHibernateTemplate() {

		return genericDao.getHibernateTemplate();
	}

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 *
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	@Override
	public boolean isUnique(T entity, String uniquePropertyNames) {

		return entityDao.isUnique(entity, uniquePropertyNames);
	}

	/**
	 * 分页查询函数，使用hql.
	 *
	 * @param pageNo 页号,从1开始.
	 */
	@Override
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {

		return genericDao.pagedQuery(hql, pageNo, pageSize, values);
	}

	/**
	 * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	@Override
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {

		return entityDao.pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	@Override
	public Page pagedQuery(int pageNo, int pageSize, Criterion... criterions) {

		return entityDao.pagedQuery(pageNo, pageSize, criterions);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	@Override
	public Page pagedQuery(int pageNo, int pageSize, String orderBy, boolean isAsc, Criterion... criterions) {

		return entityDao.pagedQuery(pageNo, pageSize, orderBy, isAsc, criterions);
	}

	/**
	 * 删除对象.
	 */
	@Override
	public void remove(T entity) {

		entityDao.remove(entity);
	}

	/**
	 * 根据ID删除对象.
	 * @return 
	 */
	@Override
	public boolean removeById(PK id) {
		entityDao.removeById(id);
		return true;
	}

	/**
	 * 保存对象.<br>
	 * 如果对象已在本session中持久化了,不做任何事。<br>
	 * 如果另一个seesion拥有相同的持久化标识,抛出异常。<br>
	 * 如果没有持久化标识属性,调用save()。<br>
	 * 如果持久化标识表明是一个新的实例化对象,调用save()。<br>
	 * 如果是附带版本信息的(<version>或<timestamp>)且版本属性表明为新的实例化对象就save()。<br>
	 * 否则调用update()重新关联托管对象
	 * @return 
	 */
	@Override
	public Serializable save(T entity) {
		return entityDao.save(entity);
	}

	/**
	 * 在不同的session中关联修改过的托管对象
	 * @return 
	 */
	@Override
	public boolean update(T entity) {
		entityDao.update(entity);
		return true;
	}

}