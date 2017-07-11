package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.IAddressDao;
import com.housekeeping.entity.Address;
import com.housekeeping.entity.wrap.Addresss;
import com.housekeeping.service.IAddressService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class AddressService implements IAddressService {
	private IAddressDao<Address> addressDao;

	public void setAddressDao(IAddressDao<Address> addressDao) {
		this.addressDao = addressDao;
	}

	@Override
	public Response addAddress(Address address) {
		if (address != null) {
			int id = (int) addressDao.save(address);
			address.setId(id);
			return Response.ok(address).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加地址失败");
	}

	@Override
	public Response deleteAddress(int id) {
		if (id != 0) {
			Address address = addressDao.get(id);
			if (address != null) {
				addressDao.remove(address);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("地址不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除地址时主键不能为空");
	}

	@Override
	public Response updateAddress(Address address) {
		if (address != null && address.getId() != 0) {
			if (addressDao.update(address)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新地址时主键不能为空");
	}

	@Override
	public Address getAddress(int id) {
		if (id != 0) {
			return addressDao.get(id);
		}
		throw ServiceErrorBuilder.badRequestError("查询地址时主键不能为空");
	}

	@Override
	public Addresss getAddressByUserId(int userId) {
		if (userId != 0) {
			List<Address> adddressList = addressDao.findBy("userId", userId);
			if (adddressList != null) {
				Addresss addresss = new Addresss();
				addresss.setAddresses(adddressList);
				return addresss;
			}
			return null;
		}
		throw ServiceErrorBuilder.badRequestError("查询地址时主键不能为空");
	}

	@Override
	public Response updateDefault(int id) {
		if (id != 0) {
			if (addressDao.updateDefault(id)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新地址时主键不能为空");
	}
}
