/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.CommonMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 5:53:34 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface BoardSettingMapper extends CommonMapper<BoardSetting> {

	/**
	 * @param settingId
	 * @param slotId
	 */
	@Select("select * from t_board_setting where setting_id=#{settingId} and slot_id=#{slotId}")
	BoardSetting selectBySlotId(int settingId, int slotId);

	/**
	 * {@link com.hirain.phm.synapsis.setting.dao.SettingMapper#selectDetails(int)}
	 * 
	 * @param settingId
	 * @return
	 */
	@Select("select tbs.* from t_board_setting tbs where tbs.setting_id=#{settingId}")
	@Results({

			@Result(property = "id", column = "id", id = true),
			@Result(property = "variableGroups", column = "id", many = @Many(select = "com.hirain.phm.synapsis.setting.dao.VariableGroupMapper.selectByBoard")) })
	List<BoardSetting> selectSetting(int settingId);

	/**
	 * @param settingId
	 */
	@Select("delete from t_board_setting where setting_id=#{settingId}")
	void deleteBySettingId(int settingId);

}
