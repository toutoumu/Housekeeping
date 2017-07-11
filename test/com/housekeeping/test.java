package com.housekeeping;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.housekeeping.entity.Address;
import com.housekeeping.entity.Employee;
import com.housekeeping.entity.User;
import com.housekeeping.entity.wrap.Addresss;
import com.housekeeping.entity.wrap.Employees;
import com.housekeeping.utils.JsonUtils;

public class test extends TestCase {

	public void testOOOO() {
		//"20142-12 12:12:22";
		Date date = new Date();
		System.out.println(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(date));
	}

	public void testusers() {
		Addresss users = new Addresss();
		List<Address> users2 = new ArrayList<Address>();
		Address user = new Address();
		user.setAddress("adsfasdf");
		users2.add(user);
		users.setAddresses(users2);
		System.out.println(JsonUtils.toJson(users));
	}

	public void testAAAA() {
		// 解析为对应的实体

		String json = "{\"Employees\":{\"employees\":[{\"businessCategoryId\":1,\"coordinat\":213,\"credentialsCategory\":\"健康证\",\"credentialsNumber\":\"0987654321\",\"grade\":4,\"id\":1,\"idCard\":222222222222222222,\"name\":\"傻逼服务员\",\"nativeSlace\":\"伊拉克\",\"phoneNumber\":13888899998,\"photo\":\"QQWE\",\"rank\":1,\"relationName\":\"亲友\",\"relationPhoneNumber\":123121123211,\"remark\":\"无备注\",\"serviceArea\":\"南山\",\"serviceTimes\":2,\"speciality\":\"吃饭\",\"state\":2,\"workExperience\":1},{\"businessCategoryId\":1,\"coordinat\":23,\"credentialsCategory\":\"健康证\",\"credentialsNumber\":\"0987654321\",\"grade\":2,\"id\":2,\"idCard\":222222222222222222,\"name\":"
				+ "\"傻逼服务员2\",\"nativeSlace\":\"伊拉克\",\"phoneNumber\":13888899998,\"photo\":21312,\"rank\":1,\"relationName\":\"亲友\",\"relationPhoneNumber\":123121123211,\"remark\":\"无备注\",\"serviceArea\":\"南山\",\"serviceTimes\":2,\"speciality\":\"吃饭\",\"state\":2,\"workExperience\":1}]}}";
		System.out.println(JsonUtils.getObject(json, "Employees"));
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);

		JsonElement obj = element.getAsJsonObject().get(Employees.class.getSimpleName());
		Gson gson = new Gson();
		Employees employees = gson.fromJson(obj.toString(), Employees.class);
		System.out.println(employees.getEmployees().get(0).getName());
	}

	public void testEmplyees() {
		Employees employees = new Employees();
		List<Employee> list = new ArrayList<Employee>();
		Employee employee = new Employee();
		employee.setName("adfasdf");
		list.add(employee);
		employee = new Employee();
		employee.setName("adfasdf");
		list.add(employee);
		employees.setEmployees(list);
		System.out.println(JsonUtils.toJson(employees));

		String jsonString = "{\"Employees\":{\"employees\":[{\"businessCategoryId\":1,\"coordinat\":213,\"credentialsCategory\":\"健康证\",\"credentialsNumber\":\"0987654321\",\"grade\":4,\"id\":1,\"idCard\":222222222222222222,\"name\":\"傻逼服务员\",\"nativeSlace\":\"伊拉克\",\"phoneNumber\":13888899998,\"photo\":\"QQWE\",\"rank\":1,\"relationName\":\"亲友\",\"relationPhoneNumber\":123121123211,\"remark\":\"无备注\",\"serviceArea\":\"南山\",\"serviceTimes\":2,\"speciality\":\"吃饭\",\"state\":2,\"workExperience\":1},{\"businessCategoryId\":1,\"coordinat\":23,\"credentialsCategory\":\"健康证\",\"credentialsNumber\":\"0987654321\",\"grade\":2,\"id\":2,\"idCard\":222222222222222222,\"name\":"
				+ "\"傻逼服务员2\",\"nativeSlace\":\"伊拉克\",\"phoneNumber\":13888899998,\"photo\":21312,\"rank\":1,\"relationName\":\"亲友\",\"relationPhoneNumber\":123121123211,\"remark\":\"无备注\",\"serviceArea\":\"南山\",\"serviceTimes\":2,\"speciality\":\"吃饭\",\"state\":2,\"workExperience\":1}]}}";
		System.out.println(JsonUtils.getObject(jsonString, "Employees"));
		employees = JsonUtils.fromJson(JsonUtils.getObject(jsonString, "Employees").toString(), Employees.class);
		System.out.println(employees.getEmployees().get(0).getName());
	}

	public void testMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "asdfasdf");
		map.put("b", "asdfasdf");
		map.put("c", "casldkjf");
		System.out.println(JsonUtils.toJson(map, false));
		String mm = JsonUtils.toJson(map, false);
		System.out.println(JsonUtils.getAttr(mm, "a"));
	}

	public void testUser() {
		String json = "{\"User\":{\"balance\":23,\"consumeTimes\":23,\"createTime\":\"2014-08-21 09:47:51\",\"grade\":123,\"id\":14,\"phoneNumber\":33335,\"score\":0,\"userName\":\"adfasdf\"}}";

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");// "EEE MMM dd HH:mm:ss zzz yyyy";
		JsonElement obj = element.getAsJsonObject().get("User");
		User user = builder.create().fromJson(obj.toString(), User.class);

	}

	public void testDatea() {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL);
		System.out.println(format.format(new Date()));
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		System.out.println(new Date());
		System.out.println(gson.toJson(new Date()));
		System.out.println(gson.toJson(new Date()));
		String sssString = "2014-08-20T16:26:30.139+08:00";
		// "createTime":"2014-08-20T16:26:30.139+08:00";
	}

	public void testHttpGet() throws Exception, IOException {

		HttpClient client = new HttpClient();

		// PostMethod postMethod = new PostMethod("http://10.111.125.32:8080/Housekeeping/ws/UserService/User");
		GetMethod postMethod = new GetMethod("http://localhost:8080/Housekeeping/ws/UserService/User/2/23");
		// PostMethod postMethod = new PostMethod("http://10.111.125.32:8080/Housekeeping/ManagerServlet.do");
		postMethod.setRequestHeader("Content-Type", "application/json");
		int statusCode = client.executeMethod(postMethod);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + postMethod.getStatusLine());
		}
		byte[] responseBody = postMethod.getResponseBody();
		System.out.println(new String(responseBody));

	}

	public void testHttpGet1() throws Exception, IOException {
		HttpClient client = new HttpClient();

		/*GetMethod postMethod = new GetMethod(
				"http://localhost:8080/Housekeeping/ws/EmployeeService/Employee/EmployeeByBusinessCategoryId/1");*/
		GetMethod postMethod = new GetMethod(
				"http://115.28.138.57:8088/Housekeeping/ws/EmployeeService/Employee/EmployeeByBusinessCategoryId/1");
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("ACCEPT", "application/json");
		int statusCode = client.executeMethod(postMethod);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + postMethod.getStatusLine());
		}
		byte[] responseBody = postMethod.getResponseBody();
		System.out.println(new String(responseBody));

	}

	public void testHttpPost() throws Exception, IOException {

		User requestuUser = new User();
		// requestuUser.setAddress("adsfasdf");
		requestuUser.setCity("sz");
		requestuUser.setPhoneNumber("1241242134");
		requestuUser.setCreateTime(new Date());

		HttpClient client = new HttpClient();

		// PostMethod postMethod = new PostMethod("http://10.111.125.32:8080/Housekeeping/ws/UserService/User");
		PostMethod postMethod = new PostMethod("http://localhost:8080/Housekeeping/ws/UserService/User");
		// PostMethod postMethod = new PostMethod("http://10.111.125.32:8080/Housekeeping/ManagerServlet.do");
		postMethod.setRequestHeader("Content-Type", "application/json");

		// postMethod.setRequestBody("{\"User\":" + new Gson().toJson(requestuUser) + "}");
		// System.out.println("new Gson().toJson(requestuUser)转换");
		// System.out.println("{\"User\":" + new Gson().toJson(requestuUser) + "}");

		postMethod.setRequestBody("{\"User\":" + JsonUtils.toJson(requestuUser, false) + "}");
		System.out.println("JSONUtils.toJson(requestuUser, false)转换");
		System.out.println("{\"User\":" + JsonUtils.toJson(requestuUser, false) + "}");

		int statusCode = client.executeMethod(postMethod);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + postMethod.getStatusLine());
		}
		byte[] responseBody = postMethod.getResponseBody();
		System.out.println(new String(responseBody));
	}

	public void testGson() {
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("abc", "asdb");
		map.put("asdf", "asdg");
		String string = gson.toJson(map);
		System.out.println(gson.toJson(map));
	}

	public static void main(String[] args) throws Exception {

		Calendar savedTime = Calendar.getInstance(Locale.getDefault());
		savedTime.setTime(new Date());

		savedTime.add(Calendar.HOUR, -1);
		savedTime.set(Calendar.MINUTE, 0);
		savedTime.set(Calendar.SECOND, 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd:hhmmss");
		System.out.println(format.format(savedTime.getTime()));

		System.out.println("************************************************8");

		// org.springframework.asm.ClassVisitor
		// org.springframework.asm.ClassVisitor
		// org/hibernate/QueryTimeoutException
		// org.hibernate.QueryTimeoutException
		// org.hibernate.QueryTimeoutException
		// org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean

		// 生成Json字符串

		Gson gson = new Gson();
		List<User> persons = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			User p = new User();
			p.setUserName("name" + i);
			// p.setCity("" + i * 5);
			persons.add(p);
		}
		String str = gson.toJson(persons);
		System.out.println(str);
		String json = "{\"total\":100,\"rows\":[{\"key\":\"key1\",\"value\":\"value1\"},{\"key\":\"key2\",\"value\":\"value2\"}]}";
		System.out.println(json);
		JsonParser jsonParser = new JsonParser();
		System.out.println(jsonParser.parse(json).getAsJsonObject().get("rows"));

		/* JSONObject object = new JSONObject(json);
		 * Object jObject =object.get("rows");
		 * System.out.println(jObject.toString()); */

		JsonElement element = new JsonObject();

		RetObject retObject = new RetObject();
		retObject.setData(persons);
		retObject.isSuccess = true;
		retObject.setMessage("asdf");
		System.out.println(gson.toJson(retObject));

	}

	static class RetObject {
		private boolean isSuccess;

		private String message;

		private Object data;

		/** @return the isSuccess */
		public boolean isSuccess() {
			return isSuccess;
		}

		/** @param isSuccess
		 *            the isSuccess to set */
		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}

		/** @return the message */
		public String getMessage() {
			return message;
		}

		/** @param message
		 *            the message to set */
		public void setMessage(String message) {
			this.message = message;
		}

		/** @return the data */
		public Object getData() {
			return data;
		}

		/** @param data
		 *            the data to set */
		public void setData(Object data) {
			this.data = data;
		}
	}
}
