package com.hirain.dome;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws Exception {

		LocalDateTime now = LocalDateTime.now();
		// 五天前
		LocalDateTime localDateTime = now.plusHours(-2);
		LocalDateTime localDateTime1 = localDateTime.plusMinutes(-5);
		System.out.println(localDateTime1);
	}

//	public static List<String> partitions() {
//		final List<String> partitions = new ArrayList<>();
//		final LocalDateTime date = LocalDateTime.now();
//		LocalDateTime nextMonth = date.plusMonths(1);
//		for (int i = 24; i >= 0; i--) {
//			LocalDateTime firstDayOfNextMouth = nextMonth.plusMonths(-i).with(TemporalAdjusters.firstDayOfMonth());
//			final String time = firstDayOfNextMouth.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//			partitions.add(time);
//		}
//		return partitions;
//	}

	public static Date getFirstDayOfWeek(Date date) {
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
		return cal.getTime();
	}

	public static boolean isSameWeek(Date old, Date now) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			old = sdf.parse(sdf.format(old));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long oneDayTime = 1000 * 60 * 60 * 24L;
		Long old_count = old.getTime() / oneDayTime;
		Long now_other = now.getTime() / oneDayTime;
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
}
