package com.hirain.dome;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.qsy.shaft.dao.ManageMapper;

public class ManageMapperTest extends BaseTest {

	@Autowired
	ManageMapper manageMapper;

	@Test
	public void createInitialDataTableTest() {
		manageMapper.createInitialDataTable(1, partitions());
		int count = manageMapper.existInitialDataTable(1);
		assertEquals(1, count);
	}

	@Test
	public void createTableTest() {
		manageMapper.createExceptionDataTable(1, partitions());
	}

	@Test
	public void dropTableTest() {
		manageMapper.dropTable("t_initial_data_1");
	}

	@Test
	public void lastPartitionTest() {
		String lastPartition = manageMapper.lastPartition("t_initial_data_1");
		System.out.println(lastPartition);

	}

	@Test
	public void addPartitionTest() {
		Date date = new Date();
		addPartition(1, date);
	}

	private void addPartition(Integer trainId, Date acquisitionTime) {
		String partition = manageMapper.lastPartition("t_initial_data_" + trainId);
		if (partition.startsWith("p")) {
			partition = partition.substring(1);
		}
		// 最后一个分区时间
		final LocalDateTime lastPartitionDate = LocalDateTime.parse(partition + "000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		// 插入数据的最大时间
		LocalDateTime maxInsertDate = date2LocalDateTime(acquisitionTime);
		final Duration duration = Duration.between(lastPartitionDate, maxInsertDate);
		final long days = duration.toDays();
		if (days >= 0) {
			manageMapper.addPartitions("t_initial_data_" + trainId, partitions(lastPartitionDate, days));
		}
	}

	private List<String> partitions(LocalDateTime lastPartitionDate, long days) {
		final List<String> partitions = new ArrayList<>();
		for (int i = 0; i <= days; i++) {
			LocalDateTime firstDayOfNextMouth = lastPartitionDate.plusMonths(i + 1).with(TemporalAdjusters.firstDayOfMonth());
			final String time = firstDayOfNextMouth.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			partitions.add(time);
		}
		return partitions;
	}

	private LocalDateTime date2LocalDateTime(Date acquisitionTime) {
		Instant instant = acquisitionTime.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		return localDateTime;
	}

	private List<String> partitions() {
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
}
