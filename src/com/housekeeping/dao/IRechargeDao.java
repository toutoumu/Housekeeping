package com.housekeeping.dao;

import java.io.Serializable;

import com.hibernate.dao.base.IBaseDao;
import com.housekeeping.entity.Recharge;

/**
 * 文件名: IRechargeDao.java</br>
 * 编写者: toutoumu</br>
 * 编写日期: 2014年9月4日</br>
 * 简要描述: 充值管理</br>
 * 组件列表:</br>
 * ********************  修改日志  **********************************</br>
 * 修改人：           		  修改日期：</br>
 * 修改内容：</br>
 *
 */
public interface IRechargeDao<T> extends IBaseDao<T, Serializable> {
	/**
	 * 添加充值记录并更新用户余额
	 * @param recharge
	 * @return
	 */
	Recharge addRecharge(Recharge recharge);
}
