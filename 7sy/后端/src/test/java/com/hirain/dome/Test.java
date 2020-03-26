package com.hirain.dome;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Test {

	public static void main(String[] args) throws Exception {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
		// String date1 = "2019-04-14 23:19:58";
		// // String date2 = "2019-04-14 08:19:51";
		// //
		// // System.out.println(isSameWeek(sdf.parse(date2), sdf.parse(date1)));
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(sdf.parse(date1));
		// // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		// cal.add(Calendar.HOUR_OF_DAY, 2);
		// System.out.println(sdf.format(cal.getTime()));
		// final LocalDateTime date = LocalDateTime.parse("20191101000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		// final LocalDateTime now = LocalDateTime.now();
		// final Duration duration = Duration.between(date, now);
		// final long days = duration.toDays();
		// System.out.println(days);
		// List<String> partitions = partitions();
		// for (String string : partitions) {
		// System.out.println(string);
		// }
		File file = new File("D://a.txt");
		System.out.println(file.exists());
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("", "");
		hashMap.get("");
	}

	public static List<String> partitions() {
		final List<String> partitions = new ArrayList<>();
		final LocalDateTime date = LocalDateTime.now();
		LocalDateTime nextMonth = date.plusMonths(1);
		for (int i = 24; i >= 0; i--) {
			LocalDateTime firstDayOfNextMouth = nextMonth.plusMonths(-i).with(TemporalAdjusters.firstDayOfMonth());
			final String time = firstDayOfNextMouth.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			partitions.add(time);
		}
		return partitions;
	}

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
