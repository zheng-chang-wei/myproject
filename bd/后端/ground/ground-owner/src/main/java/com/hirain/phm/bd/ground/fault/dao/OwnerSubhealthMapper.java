/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.SelectProvider;

import com.github.pagehelper.util.StringUtil;
import com.hirain.phm.bd.ground.fault.param.FaultCount;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.param.SubhealthResponse;

import tk.mybatis.mapper.common.Marker;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 5:31:32 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface OwnerSubhealthMapper extends Marker {

	@SelectProvider(type = OwnerSubhealthMapperProvider.class, method = "selectTop20")
	List<SubhealthResponse> selectTop20(String project, String trainNo);

	/**
	 * @param request
	 */
	@SelectProvider(type = OwnerSubhealthMapperProvider.class, method = "selectByParam")
	List<SubhealthResponse> selectByParam(FaultRequest request);

	@SelectProvider(type = OwnerSubhealthMapperProvider.class, method = "countByMonth")
	List<FaultCount> countByMonth(String project, String train, int month, List<String> subhealths);

	class OwnerSubhealthMapperProvider {

		public String countByMonth(String project, String train, int month, List<String> subhealths) {
			String sql = "select ti.subhealth_name item,td.car_no car,td.door_addr door,count(ti.subhealth_name) total";
			sql += " from t_subhealth_detail td";
			sql += " left join t_subhealth_type ti on td.subhealth_info_id =ti.subhealth_code";
			sql += " left join t_train tt on tt.id=td.train_id";
			sql += " left join t_project tp on tp.id=tt.project_id";
			sql += " where tp.name=#{project}";
			sql += " and tt.train_no=#{train}";
			sql += " and ti.subhealth_name in" + subhealths.stream().collect(Collectors.joining("','", "('", "')"));
			sql += " and MONTH(td.start_time)=#{month}";
			sql += " group by ti.subhealth_name,td.car_no,td.door_addr";
			return sql;
		}

		public String selectTop20(String project, String trainNo) {
			String sql = "select" +

					" tp.name project,tt.train_no,td.id,td.car_no,td.door_addr,td.start_time,td.end_time, tts.outline suggestion," +

					" ti.subhealth_name, tts.suggestion treatment, trs.suggestion repair " +

					" from t_subhealth_detail td" +

					" left join t_subhealth_type ti on td.subhealth_info_id =ti.subhealth_code" +

					" left join t_train tt on tt.id=td.train_id" +

					" left join t_project tp on tp.id=tt.project_id" +

					" left join t_push ts on ti.subhealth_code=ts.code and ts.type=1" +

					" left join t_treatment_suggestion tts on ts.treatment_id=tts.id" +

					" left join t_repair_suggestion trs on ts.repair_id=trs.id";

			sql += " where true";
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

		public String selectByParam(FaultRequest request) {
			String sql = "select" +

					" tp.name project,tt.train_no,td.id,td.car_no,td.door_addr,td.start_time,td.end_time, " +

					" ti.subhealth_name, trs.suggestion repair,ts.description, trs.solution solution " +

					" from t_subhealth_detail td" +

					" left join t_subhealth_type ti on td.subhealth_info_id =ti.subhealth_code" +

					" left join t_train tt on tt.id=td.train_id" +

					" left join t_project tp on tp.id=tt.project_id" +

					" left join t_push ts on ti.subhealth_code=ts.code and ts.type=1" +

					" left join t_repair_suggestion trs on ts.repair_id=trs.id";

			sql += " where true";
			if (StringUtil.isNotEmpty(request.getProject())) {
				sql += " and tp.name=#{project}";
			}
			if (StringUtil.isNotEmpty(request.getTrain())) {
				sql += " and tt.train_no=#{train}";
			}
			if (request.getStartDate() != null) {
				sql += " and td.start_time >= #{startDate}";
			}
			if (request.getEndDate() != null) {
				sql += " and td.start_time <= #{endDate}";
			}
			sql += " order by td.start_time desc";
			return sql;
		}
	}
}
