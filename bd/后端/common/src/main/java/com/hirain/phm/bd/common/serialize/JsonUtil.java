package com.hirain.phm.bd.common.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 下午3:30:46
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
public class JsonUtil {

	public static String toString(Object obj) {
		return toJson(obj);
	}

	/**
	 * @param obj
	 * @return
	 */
	private static String toJson(Object obj) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.toJson(obj);
	}

	public static <T> T fromString(String string, Class<T> clz, String pattern) {
		Gson gson = new GsonBuilder().setDateFormat(pattern).create();
		return gson.fromJson(string, clz);
	}

	public static <T> T fromString(String string, Class<T> clz) {
		return fromJson(string, clz);
	}

	/**
	 * @param json
	 * @param clz
	 * @return
	 */
	private static <T> T fromJson(String json, Class<T> clz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clz);
	}
}
