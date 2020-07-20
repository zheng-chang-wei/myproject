/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.service.ExcelUtils;
import com.hirain.phm.bd.ground.life.param.LifeDoorItemAVG;
import com.hirain.phm.bd.ground.life.service.LifeDoorItemService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 9, 2020 6:49:35 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class OwnerLifeServiceImpl implements OwnerLifeService {

	@Autowired
	private LifeDoorItemService lifeDoorItemService;

	@Override
	public void exportLifeItems(FaultRequest request, OutputStream stream) {
		List<LifeExportObject> items = getExportItems(request.getProject(), request.getTrain());

		ExcelUtils.write(stream, "寿命", items, LifeExportObject.class);
	}

	@Override
	public List<LifeExportObject> getExportItems(String project, String train) {
		List<LifeDoorItemAVG> list = findDoorItemAVGValue(project, train);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<LifeExportObject> items = list.stream().map(a -> {
			LifeExportObject o = new LifeExportObject();
			o.setSystem("车门系统");
			o.setSource("北京博得");
			o.setProject(project);
			o.setTrain(train);
			o.setItem(a.getItemName());
			o.setTime(sdf.format(a.getTime()));
			double lastLife = a.getReferenceValue() - a.getUsedLife();
			o.setLife(lastLife > 0 ? lastLife : 0);
			return o;
		}).collect(Collectors.toList());
		return items;
	}

	@Override
	public List<LifeDoorItemAVG> findDoorItemAVGValue(String project, String train) {
		List<LifeDoorItemAVG> list = lifeDoorItemService.findDoorItemAVGValue(project, train);
		return list;
	}

}
