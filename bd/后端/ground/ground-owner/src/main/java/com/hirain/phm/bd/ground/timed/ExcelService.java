/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.timed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.hirain.phm.bd.ground.life.LifeExportObject;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 6, 2020 5:45:12 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class ExcelService {

	/**
	 * @param template
	 * @param dest
	 * @param datas
	 * @param items
	 */
	public void write(String template, String dest, List<FaultCollection> datas, List<LifeExportObject> items) {
		ExcelWriter excelWriter = EasyExcel.write(dest).withTemplate(template).build();
		FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
		for (FaultCollection collection : datas) {
			WriteSheet sheet = EasyExcel.writerSheet(collection.getSheetName()).build();
			Map<String, Object> map = new HashMap<>();
			map.put("project", collection.getProject());
			map.put("train", collection.getTrain());
			excelWriter.fill(map, sheet);
			for (int i = 0; i < collection.getCarItems().size(); i++) {
				excelWriter.fill(new FillWrapper("car" + (i + 1), collection.getCarItems().get(i).getItems()), fillConfig, sheet);
			}
		}
		WriteSheet lifeSheet = EasyExcel.writerSheet("寿命").build();
		excelWriter.fill(items, lifeSheet);
		excelWriter.finish();
	}

}
