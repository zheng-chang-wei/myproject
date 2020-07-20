/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.ADVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.common.CommonMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 5:33:26 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface ADVariableMapper extends CommonMapper<ADVariable> {

	@Select("select * from t_ad_variable where group_id=#{groupId}")
	List<ADVariable> selectVariables(int groupId);

	/**
	 * @see com.hirain.phm.synapsis.setting.dao.VariableMapper#insertVariables(int, java.util.List)
	 */
	@Insert({

			"<script> ",

			"insert into t_ad_variable (group_id,chn_id,sample_rate,clock_src,frequence_factor,signal_name) Values",

			"<foreach item='v' collection='variables' separator=','> ",

			" (#{groupId,jdbcType=INTEGER},#{v.chnId,jdbcType=INTEGER},#{v.sampleRate,jdbcType=DOUBLE},#{v.clockSrc,jdbcType=DOUBLE},#{v.frequenceFactor,jdbcType=DOUBLE},#{v.signalName,jdbcType=VARCHAR})",

			" </foreach>",

			"</script>" })
	int insertVariables(int groupId, List<? extends Variable> variables);

	@Delete("delete from t_ad_variable where group_id=#{groupId}")
	void deleteVariables(int groupId);
}
