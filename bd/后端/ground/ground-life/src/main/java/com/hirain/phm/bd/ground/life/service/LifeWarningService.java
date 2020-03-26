/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.life.domain.LifeItem;
import com.hirain.phm.bd.ground.life.domain.LifeWarning;
import com.hirain.phm.bd.ground.life.param.LifeWarningParam;
import com.hirain.phm.bd.ground.life.param.TodayLifeWarning;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 29, 2019 5:08:53 PM
 * @Description
 *              <p>
 *              处理t_life_warning表的service
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 29, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface LifeWarningService extends IService<LifeWarning> {

	/**
	 * 从数据库中查询寿命预警信息
	 */
	List<LifeWarningParam> findLifeWarningsByParam(LifeWarningParam lifeWarningParam);

	/**
	 * 下载选中的寿命预警信息
	 */
	void downloadWarningData();

	/**
	 * 获取所有部件信息
	 * 
	 * @return
	 */
	List<LifeItem> selectAllGroupByLifeItemName();

	/**
	 * @param project
	 * @return
	 */
	List<LifeItem> findLifeItems(String project);

	/**
	 * 根据trainID 和 lifeitemID从t_life_warning表中找到相应的数据
	 */
	LifeWarning findLifeWarningByTrainIDItemId(Integer trainID, Integer itemID);

	/**
	 * 查询当天的寿命预警
	 * 
	 * @param project
	 *            项目名
	 * @param trainNo
	 *            列车编号
	 * @return
	 */
	List<TodayLifeWarning> listLifeWarningToday(String project, String trainNo);

	/**
	 * 查询当天的寿命预警 @author zepei.tao
	 * 
	 * @param project
	 *            项目名
	 * @param trainNo
	 *            列车编号
	 * @return
	 */
	List<TodayLifeWarning> listLifeWarningToday();

}
