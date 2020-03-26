/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.push.dao.PushInfoMapper;
import com.hirain.phm.bd.ground.push.dao.RepairSuggestionMapper;
import com.hirain.phm.bd.ground.push.dao.TreatmentSuggestionMapper;
import com.hirain.phm.bd.ground.push.domain.PushInfo;
import com.hirain.phm.bd.ground.push.domain.RepairSuggestion;
import com.hirain.phm.bd.ground.push.domain.TreatmentSuggestion;
import com.hirain.phm.bd.ground.repair.TestApplication;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 18, 2019 9:47:17 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class PushInsert {

	@Autowired
	private TreatmentSuggestionMapper treatMapper;

	@Autowired
	private RepairSuggestionMapper repairMapper;

	@Autowired
	private PushInfoMapper pushMapper;

	@Test
	@Ignore
	public void insertTreatmentSuggestion() throws IOException {
		File file = new File("D:\\data.txt");
		List<String> lines = FileUtils.readLines(file, Charset.forName("utf-8"));
		for (String line : lines) {
			String[] items = line.split(",");
			TreatmentSuggestion suggestion = new TreatmentSuggestion();
			suggestion.setId(Integer.parseInt(items[0]));
			suggestion.setOutline(items[1]);
			suggestion.setSuggestion(items[2].replaceAll("；", "；\r\n"));
			treatMapper.insert(suggestion);
		}
	}

	@Test
	@Ignore
	public void insertRepairSuggestion() throws IOException {
		File file = new File("D:\\datanew.txt");
		List<String> lines = FileUtils.readLines(file, Charset.forName("utf-8"));
		for (String line : lines) {
			String[] item = line.split(",");
			RepairSuggestion suggestion = new RepairSuggestion();
			suggestion.setId(Integer.parseInt(item[0]));
			suggestion.setSuggestion(item[1].replaceAll("；", "；\r\n"));
			suggestion.setSolution(item[2].replaceAll("；", "；\r\n"));
			repairMapper.insert(suggestion);
		}
	}

	@Test
	@Ignore
	public void insertPushInfo() throws IOException {
		File file = new File("D:\\data3.txt");
		List<String> lines = FileUtils.readLines(file, Charset.forName("utf-8"));
		for (String line : lines) {
			String[] item = line.split(",");
			PushInfo push = new PushInfo();
			push.setType(Integer.parseInt(item[0]));
			push.setCode(Integer.parseInt(item[1]));
			push.setNum(Integer.parseInt(item[2]));
			push.setLevel(Integer.parseInt(item[3]));
			push.setTreatmentId(Integer.parseInt(item[4]));
			if (!item[5].equals("null")) {
				push.setRepairId(Integer.parseInt(item[5]));
			}
			pushMapper.insert(push);
		}
	}

	@Test
	@Ignore
	public void updatePushInfo() throws IOException {
		File file = new File("D:\\data4.txt");
		List<String> lines = FileUtils.readLines(file, Charset.forName("utf-8"));

		for (int i = 0; i < lines.size(); i++) {
			PushInfo info = new PushInfo();
			info.setType(0);
			info.setCode(i + 1);
			info.setDescription(lines.get(i));
			pushMapper.updateByPrimaryKeySelective(info);
		}
	}
}
