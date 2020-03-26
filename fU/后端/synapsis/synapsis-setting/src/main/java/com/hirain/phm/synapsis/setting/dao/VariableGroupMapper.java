/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.CommonMapper;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 4:32:58 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableGroupMapper extends CommonMapper<VariableGroup> {

	/**
	 * {@link com.hirain.phm.synapsis.setting.dao.AlgorithmSettingMapper#selectSetting(int)}
	 * 
	 * @param id
	 */
	@Select("select tvg.* from t_variable_group tvg left join t_algorithm_variable tav on tav.group_id=tvg.id where tav.algorithm_id=#{id}")
	List<VariableGroup> selectByAlgorithm(int id);

	/**
	 * {@link com.hirain.phm.synapsis.setting.dao.BoardSettingMapper#selectSetting(int)}
	 * 
	 * @param boardId
	 */
	@Select("select tvg.* from t_variable_group tvg left join t_board_variable tbv on tbv.group_id=tvg.id where tbv.board_id=#{boardId}")
	List<VariableGroup> selectByBoard(int boardId);

	/**
	 * {@link com.hirain.phm.synapsis.setting.dao.SettingMapper#selectDetails(int)}
	 * 
	 * @param settingId
	 * @return
	 */
	@Select("select tvg.* from t_variable_group tvg left join t_storage_variable tsv on tsv.group_id=tvg.id where tsv.setting_id=#{settingId}")
	List<VariableGroup> selectStoreVariables(int settingId);

	/**
	 * {@link com.hirain.phm.synapsis.setting.dao.SettingMapper#selectDetails(int)}
	 * 
	 * @param settingId
	 * @return
	 */
	@Select("select tvg.* from t_variable_group tvg left join t_time_setting ttv on ttv.group_id=tvg.id where ttv.setting_id=#{settingId}")
	List<VariableGroup> selectTimeVariables(int settingId);

	@Insert({

			"<script> ",

			"insert into t_board_variable ",

			" (board_id,group_id) VALUES ",

			" <foreach item='group' collection='groups' separator=','> ",

			" (#{boardId},#{group.id}) ",

			"</foreach>",

			"</script>" })
	void insertBoardGroups(int boardId, List<VariableGroup> groups);

	/**
	 * @param algorithmId
	 * @param variableGroups
	 */
	@Insert({

			"<script> ",

			"insert into t_algorithm_variable ",

			" (algorithm_id,group_id) VALUES ",

			" <foreach item='group' collection='groups' separator=','> ",

			" (#{algorithmId},#{group.id}) ",

			"</foreach>",

			"</script>" })
	void insertAlgorithmGroups(Integer algorithmId, List<VariableGroup> groups);

	/**
	 * @param settingId
	 * @param storeVariables
	 */
	@Insert({

			"<script> ",

			"insert into t_storage_variable ",

			" (setting_id,group_id) VALUES ",

			" <foreach item='group' collection='groups' separator=','> ",

			" (#{settingId},#{group.id}) ",

			"</foreach>",

			"</script>" })
	void insertStoreGroups(Integer settingId, List<VariableGroup> groups);

	/**
	 * @param settingId
	 * @param id
	 */
	@Insert("insert into t_time_setting (setting_id,group_id) values(#{settingId},#{groupId})")
	void insertTimeGroup(Integer settingId, Integer groupId);

	/**
	 * @param settingId
	 */
	@Delete("delete from t_time_setting where setting_id=#{settingId}")
	void deleteTimeVariables(Integer settingId);

	/**
	 * @param settingId
	 */
	@Delete("delete from t_storage_variable where setting_id=#{settingId}")
	void deleteStoreVariables(Integer settingId);

	/**
	 * @param boardId
	 */
	@Delete("delete from t_board_variable where board_id=#{boardId}")
	void deleteBoardVariables(Integer boardId);

	/**
	 * @param algorithmId
	 */
	@Delete("delete from t_algorithm_variable where algorithm_id=#{algorithmId}")
	void deleteAlgorithmVariables(Integer algorithmId);
}
