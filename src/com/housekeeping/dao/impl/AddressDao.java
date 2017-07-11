package com.housekeeping.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.hibernate.dao.base.BaseDao;
import com.housekeeping.dao.IAddressDao;
import com.housekeeping.entity.Address;

public class AddressDao<T> extends BaseDao<T, Serializable> implements IAddressDao<T> {

	public AddressDao(Class<T> type) {
		super(type);
	}

	@Override
	public boolean updateDefault(int id) {

		if (id != 0) {
			Address address = this.getHibernateTemplate().get(Address.class, id);
			if (address != null && address.getUserId() != 0) {
				String hql = "from Address a where a.userId=" + address.getUserId() + " and id !=" + id;
				List<Address> list = (List<Address>) find(hql, null);
				if (list != null) {
					for (Address address2 : list) {
						address2.setDefault(false);
						this.getHibernateTemplate().update(address2);
					}
				}
				address.setDefault(true);
				this.getHibernateTemplate().update(address);
				return true;
			}
		}
		return false;
	}
}
