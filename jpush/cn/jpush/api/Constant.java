package cn.jpush.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constant {
	public static String appKey = null;

	public static String masterSecret = null;
	static {
		// 加载配置文件
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = Constant.class.getResourceAsStream("/jpush-api.properties");
			properties.load(inputStream);
			appKey = properties.getProperty("appKey");
			masterSecret = properties.getProperty("masterSecret");
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException e) {

		}
	}
}
