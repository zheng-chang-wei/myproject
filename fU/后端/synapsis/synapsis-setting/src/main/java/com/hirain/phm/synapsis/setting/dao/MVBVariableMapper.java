/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.common.CommonMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 4:45:41 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface MVBVariableMapper extends CommonMapper<MVBVariable> {

	/**
	 * @see com.hirain.phm.synapsis.setting.dao.VariableMapper#selectVariables(int)
	 */
	@Select("select * from t_mvb_variable where group_id=#{groupId}")
	List<MVBVariable> selectVariables(int groupId);

	/**
	 * @see com.hirain.phm.synapsis.setting.dao.VariableMapper#insertVariables(int, java.util.List)
	 */
	@Insert({

			"<script> ",

			"insert into t_mvb_variable ",

			"(group_id,device,device_name,carriage,system,port,fcode,data_type,byte_offset,bit_offset,bit_len,name,unit,signal_name) Values",

			"<foreach item='v' collection='variables' separator=','> ",

			" (#{groupId,jdbcType=INTEGER},#{v.device,jdbcType=INTEGER},#{v.deviceName,jdbcType=VARCHAR},#{v.carriage,jdbcType=VARCHAR},#{v.system,jdbcType=VARCHAR},",

			"#{v.port,jdbcType=INTEGER},#{v.fcode,jdbcType=INTEGER},#{v.dataType,jdbcType=VARCHAR},#{v.byteOffset,jdbcType=INTEGER},#{v.bitOffset,jdbcType=INTEGER},#{v.bitLen,jdbcType=INTEGER},#{v.name,jdbcType=VARCHAR},#{v.unit,jdbcType=VARCHAR},#{v.signalName,jdbcType=VARCHAR})",

			" </foreach>",

			"</script>" })
	int insertVariables(int groupId, List<? extends Variable> variables);

	@Delete("delete from t_mvb_variable where group_id=#{groupId}")
	void deleteVariables(int groupId);

}
