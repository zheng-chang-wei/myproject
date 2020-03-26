/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 3, 2019 11:33:10 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 3, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.github.pagehelper.util.StringUtil;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailParams;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthWithSuggestionParams;

public interface SubhealthQueryMapper {

	/**
	 * @param subdeDetailParams
	 * @return
	 */
	@SelectProvider(type = SubhealthQueryMapperProvider.class, method = "selectByExample")
	public List<SubhealthWithSuggestionParams> selectByExample(SubhealthDetailParams subdeDetailParams);

	/**
	 * @return
	 */
	@SelectProvider(type = SubhealthQueryMapperProvider.class, method = "selectToday")
	public List<SubhealthWithSuggestionParams> selectToday(String project, String trainNo);

	/**
	 * @author zepei.tao
	 */
	@SelectProvider(type = SubhealthQueryMapperProvider.class, method = "selectToday4Bode")
	public List<SubhealthWithSuggestionParams> selectToday4Bode(String startTime, String endTime);

	class SubhealthQueryMapperProvider {

		public String selectToday(String project, String trainNo) {
			String sql = "select" +

					" tp.name project,tt.train_no,td.id,td.car_no,td.door_addr,td.start_time,td.end_time, tts.outline suggestion," +

					" ti.subhealth_name, tts.suggestion treatment, trs.suggestion repair " +

					" from t_subhealth_detail td" +

					" left join t_subhealth_type ti on td.subhealth_type_id =ti.subhealth_code" +

					" left join t_train tt on tt.id=td.train_id" +

					" left join t_project tp on tp.id=tt.project_id" +

					" left join t_push ts on ti.subhealth_code=ts.code" +

					" left join t_treatment_suggestion tts on ts.treatment_id=tts.id" +

					" left join t_repair_suggestion trs on ts.repair_id=trs.id";

			sql += " where ts.type=1";
			if (StringUtil.isNotEmpty(project)) {
				sql += " and tp.name=#{project}";
			}
			if (StringUtil.isNotEmpty(trainNo)) {
				sql += " and tt.train_no=#{trainNo}";
			}
			sql += " order by td.start_time desc";
			sql += " LIMIT 20";
			return sql;
		}

		public String selectToday4Bode(String startTime, String endTime) {
			String sql = "select" +

					" tp.name project,tt.train_no,td.id,td.car_no,td.door_addr,td.start_time,td.end_time, tts.outline suggestion," +

					" ti.subhealth_name, tts.suggestion treatment, trs.suggestion repair " +

					" from t_subhealth_detail td" +

					" left join t_subhealth_type ti on td.subhealth_type_id =ti.subhealth_code" +

					" left join t_train tt on tt.id=td.train_id" +

					" left join t_project tp on tp.id=tt.project_id" +

					" left join t_push ts on ti.subhealth_code=ts.code" +

					" left join t_treatment_suggestion tts on ts.treatment_id=tts.id" +

					" left join t_repair_suggestion trs on ts.repair_id=trs.id";

			sql += " where ts.type=1";
			if (StringUtil.isNotEmpty(startTime)) {
				sql += " and td.start_time >= #{startTime}";
			}
			if (StringUtil.isNotEmpty(endTime)) {
				sql += " and td.start_time <= #{endTime}";
			}
			sql += " order by td.start_time desc";
			return sql;
		}

		public String selectByExample(SubhealthDetailParams subdeDetailParams) {
			String sql = "SELECT" +

					" tp.name project," +
					// 列车编号
					"	tt.train_no," +
					// 亚健康预警详情id
					"	td.id," +
					// 车厢号
					"	td.car_no," +
					// 门地址
					"	td.door_addr," +
					// 调试
					"	td.debug_mode," +
					// 预警开始时间
					"	td.start_time," +
					// 预警结束时间
					"	td.end_time," +
					// 故障名称
					"	ti.subhealth_name, " +

					" tts.suggestion treatment, trs.suggestion repair, ts.description, trs.solution solution " +

					" FROM" +
					// 故障详情表
					" t_subhealth_detail td " +
					// 故障信息表
					"LEFT JOIN t_subhealth_type ti ON td.subhealth_type_id = ti.id " +
					// 列车表
					"LEFT JOIN t_train tt ON tt.id = td.train_id " +

					// 项目表
					"LEFT JOIN t_project tp ON tp.id = tt.project_id " +

					"LEFT JOIN t_push ts on ti.subhealth_code=ts.code " +

					"LEFT JOIN t_treatment_suggestion tts on ts.treatment_id=tts.id " +

					"LEFT JOIN t_repair_suggestion trs on ts.repair_id=trs.id " +

					" where ts.type=1 ";
			if (StringUtil.isNotEmpty(subdeDetailParams.getProject())) {
				sql += " and tp.name=#{project}";
			}
			if (StringUtil.isNotEmpty(subdeDetailParams.getTrainNo())) {
				sql += " and tt.train_no = #{trainNo}";
			}
			if (subdeDetailParams.getCarNo() != null) {
				sql += " and td.car_no = #{carNo}";
			}
			if (StringUtil.isNotEmpty(subdeDetailParams.getDoorAddr())) {
				sql += " and td.door_addr = #{doorAddr}";
			}
			if (subdeDetailParams.getDebugMode() != null) {
				sql += " and td.debug_mode = #{debugMode}";
			}
			if (subdeDetailParams.getStartTime() != null) {
				sql += " and td.start_time >= #{startTime}";
			}
			if (subdeDetailParams.getEndTime() != null) {
				sql += " and td.start_time <= #{endTime}";
			}
			if (StringUtil.isNotEmpty(subdeDetailParams.getSubhealthName())) {
				sql += " and ti.subhealth_name = #{subhealthName}";
			}
			sql += " order by td.start_time desc";
			return sql;
		}

	}

}
