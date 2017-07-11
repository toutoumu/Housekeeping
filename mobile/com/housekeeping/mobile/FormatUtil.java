package com.housekeeping.mobile;

import java.util.HashMap;
import java.util.Map;

import com.housekeeping.utils.JsonUtils;

public class FormatUtil {
	private static Map<Integer, String> orderState = new HashMap<Integer, String>();

	private static Map<Integer, String> paywayState = new HashMap<Integer, String>();

	private static Map<Integer, String> payState = new HashMap<Integer, String>();
	static {
		/*orderState.put(0, "未知");
		orderState.put(1, "待确定");
		orderState.put(2, "已确定");
		orderState.put(3, "已完成");
		orderState.put(4, "已取消");
		*/
		// 订单状态
		orderState.put(0, "未知");
		orderState.put(1, "待支付");
		orderState.put(2, "待用户确认");
		orderState.put(3, "已确认");
		orderState.put(4, "已评价");
		orderState.put(5, "待后台确认");
		orderState.put(6, "已取消");

		// 支付方式
		paywayState.put(0, "未知");
		paywayState.put(1, "支付宝");
		paywayState.put(2, "余额");
		paywayState.put(3, "上门");

		// 支付状态
		payState.put(0, "未知");
		payState.put(1, "已支付");
		payState.put(2, "未支付");
	}

	/**
	 * 取得订单状态字符描述
	 * @param key int类型的key值
	 * @return
	 */
	public static String getOrderState(Integer key) {
		return orderState.get(key);
	}

	/**
	 * 取得支付方式的字符描述
	 * @param key int类型的key值
	 */
	public static String getPaywayState(Integer key) {
		return paywayState.get(key);
	}

	/**
	 * 取得支付状态的字符描述
	 * @param key int类型的key值
	 * @return
	 */
	public static String getPayState(Integer key) {
		return payState.get(key);
	}

	/**
	 * 生成 var xxx = xxx;的js变量
	 * @param key
	 * @param value
	 * @return
	 */
	public static String createJSParameter(String key, Object value) {
		if (value == null) {
			return "var " + key + " = null";
		}
		return "var " + key + "=" + JsonUtils.toJson(value) + ";";
	}
}
