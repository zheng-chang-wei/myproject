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
import com.hirain.phm.bd.ground.fault.param.FaultResponse;

import tk.mybatis.mapper.common.Marker;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 24, 2020 5:30:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface OwnerFaultMapper extends Marker {

	@SelectProvider(type = OwnerFaultMapperProvider.class, method = "selectTop20")
	List<FaultResponse> selectTop20(String projectName, String trainNo);

	@SelectProvider(type = OwnerFaultMapperProvider.class, method = "selectByParam")
	List<FaultResponse> selectByParam(FaultRequest request);

	@SelectProvider(type = OwnerFaultMapperProvider.class, method = "countByMonth")
	List<FaultCount> countByMonth(String project, String train, int month, List<String> items);

	class OwnerFaultMapperProvider {

		public String countByMonth(String project, String train, int month, List<String> items) {
			String sql = "select ti.fault_name item,td.car_no car,td.door_addr door,count(td.fault_info_id) total ";
			sql += " from t_fault_detail td";
			sql += " left join t_fault_info ti on td.fault_info_id=ti.id";
			sql += " left join t_train tt on tt.id=td.train_id";
			sql += " left join t_project tp on tp.id=tt.project_id";
			sql += " where tp.name=#{project}";
			sql += " and tt.train_no=#{train}";
			sql += " and ti.fault_name in " + items.stream().collect(Collectors.joining("','", "('", "')"));
			sql += " and MONTH(td.fault_time)=#{month}";
			sql += " group by ti.fault_name,td.car_no,td.door_addr";
			return sql;
		}

		public String selectTop20(String projectName, String trainNo) {
			String sql = "select tp.name project,tt.train_no,td.id,td.car_no,td.door_addr,td.debug_mode,td.fault_time,ti.fault_name,tts.outline suggestion,tts.suggestion treatment, trs.suggestion repair"

					+ " from t_fault_detail td " +

					" left join t_fault_info ti on td.fault_info_id=ti.id" +

					" left join t_train tt on tt.id=td.train_id" +

					" left join t_project tp on tp.id=tt.project_id" +

					" left join t_push ts on ti.fault_code=ts.code and ts.type=0" +

					" left join t_treatment_suggestion tts on ts.treatment_id=tts.id" +

					" left join t_repair_suggestion trs on ts.repair_id=trs.id";

			sql += " where true";
			if (StringUtil.isNotEmpty(projectName)) {
				sql += " and tp.name=#{projectName}";
			}
			if (StringUtil.isNotEmpty(trainNo)) {
				sql += " and tt.train_no=#{trainNo}";
			}
			sql += " order by td.fault_time desc";
			sql += " LIMIT 20";
			return sql;
		}

		public String selectByParam(FaultRequest request) {
			String sql = "select tp.name project,tt.train_no,td.id,td.car_no,td.door_addr,td.fault_time,ti.fault_name,ts.level,tts.outline suggestion,tts.suggestion treatment,trs.suggestion repair,ts.description, trs.solution solution"
					+ " from t_fault_detail td " +

					" left join t_fault_info ti on td.fault_info_id=ti.id" +

					" left join t_train tt on tt.id=td.train_id" +

					" left join t_project tp on tp.id=tt.project_id" +

					" left join t_push ts on ti.fault_code=ts.code and ts.type=0" +

					" left join t_treatment_suggestion tts on ts.treatment_id=tts.id" +

					" left join t_repair_suggestion trs on ts.repair_id=trs.id";

			sql += " where true";
			if (StringUtil.isNotEmpty(request.getProject())) {
				sql += " and tp.name=#{project}";
			}
			if (StringUtil.isNotEmpty(request.getTrain())) {
				sql += " and tt.train_no=#{train}";
			}
			if (request.getStartDate() != null) {
				sql += " and td.fault_time >= #{startDate}";
			}
			if (request.getEndDate() != null) {
				sql += " and td.fault_time <= #{endDate}";
			}
			sql += " order by td.fault_time desc";
			return sql;
		}
	}
}
