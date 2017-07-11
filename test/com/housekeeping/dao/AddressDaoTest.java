package com.housekeeping.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.housekeeping.entity.Address;

public class AddressDaoTest extends TestCase {
	private IAddressDao<Address> addressDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		addressDao = (IAddressDao<Address>) ctx.getBean("addressDao");
	}

	public void testAddAddress() {
		Address address = new Address();
		address.setAddress("adfasdf");
		address.setUserId(2);
		int id = (int) addressDao.save(address);
		System.out.println(id);
	}

	public void testUpdateAddress() {
		Address address = new Address();
		address.setId(1);
		address.setAddress("adfasdf");
		System.out.println(addressDao.update(address));
	}

	public void testGetAddress() {
		Address Address = null;
		Address = addressDao.get(1);
		if (Address != null) {
			System.out.println(Address.getAddress());
		}
	}

	public void testSetDefault() {
		System.out.println(addressDao.updateDefault(1));
	}

	public void testGetAddresssByCategory() {
		List<Address> Addresss = addressDao.findBy("userId", 2);

		if (Addresss != null) {
			for (Address Address : Addresss) {
				System.out.println(Address.getAddress());
			}
		}
	}

	public void testDelteAddress() {
		System.out.println(addressDao.removeById(1));
	}
}
