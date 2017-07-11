package com.housekeeping.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.housekeeping.entity.Region;
import com.housekeeping.entity.wrap.Regions;

public class RegionServiceTest extends junit.framework.TestCase {

	public final String URL = "http://localhost:8080/Housekeeping/ws/RegionService/";

	private String format = MediaType.APPLICATION_JSON;

	public void testAddRegion() {
		//org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
		//org.apache.cxf.rs.security.saml.authorization.ClaimsAuthorizingInterceptor
		System.out.println("test AddRegion called with format " + format);
		WebClient client = WebClient.create(URL);

		client.path("/Region").accept(format).type(format);
		Region menu = new Region();
		menu.setName("中国");
		menu.setParentId(0);
		menu.setRegionType(1);
		Region responseRegion = client.post(menu, Region.class);
		System.out.println(responseRegion.getName());
	}

	public void testUpdateRegion() {
		System.out.println("test updateRegion called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Region").accept(format).type(format);
		Region menu = new Region();
		menu.setId(1);
		menu.setName("上海");
		menu.setParentId(0);
		menu.setRegionType(1);
		Response responseRegion = client.put(menu, Response.class);
		System.out.println(responseRegion.getStatus());
	}

	public void testGetRegion() {
		System.out.println("test getRegion called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Region/{id}", 1).accept(format).type(format);
		Region responseRegion = client.get(Region.class);
		if (responseRegion != null) {
			System.out.println(responseRegion.getName());
		}
	}

	public void testGetRegions() {
		System.out.println("test getRegions called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Region/Regions/{id}", "2").accept(format).type(format);

		Regions responseRegion = client.get(Regions.class);
		if (responseRegion != null) {
			if (responseRegion.getRegions() != null) {
				for (Region region : responseRegion.getRegions()) {
					System.out.println(region.getName());
				}
			}
		}
	}

	public void testDelete() {

		System.out.println("test updateRegion called with format " + format);
		WebClient client = WebClient.create(URL);
		client.path("/Region/{id}", "1").accept(format).type(format);

		Response responseRegion = client.delete();
		System.out.println(responseRegion.getStatus());

	}
}
