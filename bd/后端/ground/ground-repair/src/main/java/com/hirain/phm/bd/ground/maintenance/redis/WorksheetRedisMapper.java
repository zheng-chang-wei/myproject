/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.redis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 15, 2019 2:39:59 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 15, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class WorksheetRedisMapper {

	private static final String HANDLED_SHEET_KEY = "worksheet-handled";

	private static final String UNHANDLED_MONTH_AGO = "worksheet-month-ago";

	private static final String NEW_SHEET_KEY = "worksheet-new";

	@Autowired
	private RedisUtil redis;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * @param time
	 */
	public int countNewSheet(Date time) {
		String date = sdf.format(time);
		Object value = redis.hmget(NEW_SHEET_KEY, date);
		return value == null ? 0 : Integer.parseInt(value.toString());
	}

	/**
	 * @param time
	 */
	public int countHandledSheet(Date time) {
		String date = sdf.format(time);
		Object value = redis.hmget(HANDLED_SHEET_KEY, date);
		return value == null ? 0 : Integer.parseInt(value.toString());
	}

	/**
	 * 
	 */
	public int countUnHandled() {
		String value = redis.get(UNHANDLED_MONTH_AGO);
		return value == null ? 0 : Integer.parseInt(value.toString());
	}

	/**
	 * @param time
	 */
	public void addSheet(Date time) {
		String date = sdf.format(time);
		redis.hmincrement(NEW_SHEET_KEY, date);
	}

	/**
	 * @param time
	 */
	public void handleSheet(Date time) {
		int daysBeforeNow = GroundAccessHelper.calDaysBeforeNow(time);
		if (daysBeforeNow > 30) {
			redis.decrement(UNHANDLED_MONTH_AGO);
		} else {
			String date = sdf.format(time);
			redis.hmincrement(HANDLED_SHEET_KEY, date);
			if (!redis.hasKey(NEW_SHEET_KEY, date)) {
				addSheet(time);
			}
		}
	}

	public void initDeparts(Long sheetId, String[] departs) {
		redis.set(sheetId + "-total", String.valueOf(departs.length));
		redis.set(sheetId + "-count", String.valueOf(0));
		Map<Object, Object> map = new HashMap<>();
		for (String depart : departs) {
			map.put(depart, "false");
		}
		redis.hset(sheetId + "-department", map);
	}

	public boolean incrementAndCompare(Long sheetId) {
		long count = redis.increment(sheetId + "-count");
		long total = Long.parseLong(redis.get(sheetId + "-total"));
		boolean eqauls = count == total;
		return eqauls;
	}

	public void updateDepart(Long sheetId, String deptName) {
		redis.hmset(sheetId + "-department", deptName, "true");
	}

	public void delDeparts(Long sheetId) {
		redis.del(sheetId + "-count", sheetId + "-total", sheetId + "-department");
	}

	public void resetDeparts(Long sheetId, WorkSheet sheet) {
		redis.set(sheet.getId() + "-count", String.valueOf(0));
		Map<Object, Object> map = redis.hget(sheetId + "-department");
		for (Object key : map.keySet()) {
			map.put(key, "false");
		}
		redis.hset(sheetId + "-department", map);
	}

	public boolean checkDepart(Long sheetId, String deptName) {
		Object result = redis.hmget(sheetId + "-department", deptName);
		boolean check = "true".equals(result);
		return check;
	}

	public void integrateSheets() {
		Map<Object, Object> map = redis.hget(NEW_SHEET_KEY);
		Date now = new Date();
		for (Object key : map.keySet()) {
			try {
				Date date = sdf.parse(key.toString());
				if (GroundAccessHelper.calDaysBetween(date, now) > 30) {
					int total = countNewSheet(date);
					int handled = countHandledSheet(date);
					redis.increment(UNHANDLED_MONTH_AGO, total - handled);
					redis.hmdel(NEW_SHEET_KEY, key.toString());
					redis.hmdel(HANDLED_SHEET_KEY, key.toString());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
