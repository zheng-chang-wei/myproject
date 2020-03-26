/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月9日 下午5:39:01
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月9日 changwei.zheng@hirain.com 1.0 create file
 */
public class DateUtil {

	private static String patternSecond = "yyyy-MM-dd HH:mm:ss";

	private static String patternDay = "yyyy-MM-dd";

	/**
	 * 获取指定日期的前一个月的每一天
	 * 
	 * @param time
	 * @return
	 */
	public static List<String> getLastMonthDays(String time) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(patternSecond);
		LocalDateTime parse = LocalDateTime.parse(time, dateTimeFormatter);
		// 上个月时间
		LocalDateTime lastMonth = parse.minusMonths(1);
		List<String> list = getDaysBetweenDate(lastMonth, parse);
		return list;
	}

	public static List<String> getDaysBetweenDate(String date1, String date2) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(patternSecond);
		return getDaysBetweenDate(LocalDateTime.parse(date1, dateTimeFormatter), LocalDateTime.parse(date2, dateTimeFormatter));
	}

	public static int compareDate(String dateString1, String dateString2) {
		Date date1 = string2Date_Day(dateString1);
		Date date2 = string2Date_Day(dateString2);
		if (date2 == null) {
			return 1;
		}
		if (date1.getTime() > date2.getTime()) {
			return 1;
		} else if (date1.getTime() < date2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	public static int compareDate(Date date1, Date date2) {
		if (date2 == null) {
			return 1;
		}
		if (date1.getTime() > date2.getTime()) {
			return 1;
		} else if (date1.getTime() < date2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	public static String date2StringSecond(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(patternSecond);
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String date2StringDay(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(patternDay);
		String dateString = formatter.format(date);
		return dateString;
	}

	public static Date string2Date_Second(String date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(patternSecond);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date string2Date_Day(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(patternDay);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isSameDay(Date date1, Date date2) {
		return DateUtils.isSameDay(date1, date2);
	}

	public static boolean isSameWeek(Date date1, Date date2) {
		SimpleDateFormat sdf = new SimpleDateFormat(patternDay);
		try {
			date1 = sdf.parse(sdf.format(date1));
			date2 = sdf.parse(sdf.format(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long oneDayTime = 1000 * 60 * 60 * 24L;
		Long old_count = date1.getTime() / oneDayTime;
		Long now_other = date2.getTime() / oneDayTime;
		return (old_count + 4) / 7 == (now_other + 4) / 7;
	}

	public static boolean isSameMonth(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		return isSameMonth;
	}

	public static boolean isSameYear(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		return isSameYear;
	}

	public static String getFirstDayOfWeek(String day) {
		Date date = string2Date_Day(day);
		return getFirstDayOfWeek(date);
	}

	/**
	 * 根据某天的日期获取当前周的周一的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(patternDay);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		return sdf.format(cal.getTime());
	}

	public static boolean isSame(Date date1, Date date2, Integer granularityTime) {
		switch (granularityTime) {
		case 0:
			return isSameDay(date1, date2);
		case 1:
			return isSameWeek(date1, date2);
		case 2:
			return isSameMonth(date1, date2);
		case 3:
			return isSameYear(date1, date2);
		default:
			break;
		}
		return false;
	}

	/**
	 * 获取当前月
	 * 
	 * @param time
	 * @return
	 */
	public static String getCurrentMonth(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(time);
	}

	/**
	 * 获取当前年
	 * 
	 * @param time
	 * @return
	 */
	public static String getCurrentYear(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(time);
	}

	private static List<String> getDaysBetweenDate(LocalDateTime date1, LocalDateTime date2) {
		List<String> list = new ArrayList<>();
		while (date1.isBefore(date2)) {
			list.add(date1.format(DateTimeFormatter.ofPattern(patternDay)));
			date1 = date1.plusDays(1);
		}
		list.add(date2.format(DateTimeFormatter.ofPattern(patternDay)));
		return list;
	}

	/**
	 * 获取本周的所有天
	 * 
	 * @param week
	 * @return
	 */
	public static List<String> getDaysCurrentWeek(String week) {
		Date date = string2Date_Day(week);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		List<String> list = new ArrayList<>();
		list.add(week);
		for (int i = 0; i < 6; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			list.add(date2StringDay(cal.getTime()));
		}
		return list;
	}

	// private static LocalDateTime string2LocalDateTime(String date) {
	// return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(patternSecond));
	// }
	//
	// private static String localDateTime2String(LocalDateTime date) {
	// return date.format(DateTimeFormatter.ofPattern(patternSecond));
	// }

	public static void main(String[] args) {
		List<String> daysCurrentWeek = getDaysCurrentWeek("2019-10-21");
		for (String string : daysCurrentWeek) {
			System.out.println(string);
		}
	}
}
