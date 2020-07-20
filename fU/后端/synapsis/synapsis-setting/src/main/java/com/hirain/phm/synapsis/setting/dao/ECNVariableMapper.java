/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.common.CommonMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 5:27:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface ECNVariableMapper extends CommonMapper<ECNVariable> {

	/**
	 * @see com.hirain.phm.synapsis.setting.dao.VariableMapper#selectVariables(int)
	 */
	@Select("select * from t_ecn_variable where group_id=#{group_id}")
	List<ECNVariable> selectVariables(int groupId);

	/**
	 * @see com.hirain.phm.synapsis.setting.dao.VariableMapper#insertVariables(int, java.util.List)
	 */
	@Insert({

			"<script> ",

			"insert into t_ecn_variable",

			" (group_id,source_ip,com_id,data_type,byte_offset,bit_offset,bit_len,name,unit,signal_name) Values",

			"<foreach item='v' collection='variables' separator=','> ",

			" (#{groupId,jdbcType=INTEGER},#{v.sourceIp,jdbcType=VARCHAR},#{v.comId,jdbcType=BIGINT},#{v.dataType,jdbcType=VARCHAR},#{v.byteOffset,jdbcType=INTEGER},#{v.bitOffset,jdbcType=INTEGER},#{v.bitLen,jdbcType=INTEGER},#{v.name,jdbcType=VARCHAR},#{v.unit,jdbcType=VARCHAR},#{v.signalName,jdbcType=VARCHAR})",

			" </foreach>",

			"</script>" })
	int insertVariables(int groupId, List<? extends Variable> variables);

	@Delete("delete from t_ecn_variable where group_id=#{groupId}")
	void deleteVariables(int groupId);

}
