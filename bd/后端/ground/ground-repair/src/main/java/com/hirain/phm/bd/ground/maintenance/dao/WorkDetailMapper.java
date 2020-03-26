/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;
import com.hirain.phm.bd.ground.maintenance.param.FaultStatisticsRequestParm;
import com.hirain.phm.bode.core.util.StringUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午4:26:24
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
public interface WorkDetailMapper extends CommonMapper<WorkDetail> {

	@SelectProvider(type = WorkDetailMapperProvider.class, method = "countProjectFaultByParms")
	List<CommonStatisticsResult> countProjectFaultByParms(FaultStatisticsRequestParm parm);

	@SelectProvider(type = WorkDetailMapperProvider.class, method = "countPartsFaultByParms")
	List<CommonStatisticsResult> countPartsFaultByParms(FaultStatisticsRequestParm parm);

	@SelectProvider(type = WorkDetailMapperProvider.class, method = "countFaultNameByParms")
	List<CommonStatisticsResult> countFaultNameByParms(FaultStatisticsRequestParm parm);

	@SelectProvider(type = WorkDetailMapperProvider.class, method = "getTopFiveProjectFaultByParms")
	List<CommonStatisticsResult> getTopFiveProjectFaultByParms(FaultStatisticsRequestParm parm);

	@Select("select twd.*, twm.mode_name fault_mode, tdy.door_type_name door_type, te.effect_name effect, ts.stage_name stage "

			+ "from t_workdetail twd "

			+ "left join t_mode twm on twm.id=twd.mode_id "

			+ "left join t_door_type tdy on tdy.id=twd.door_type_id "

			+ "left join t_effect te on te.id=twd.effect_id "

			+ "left join t_stage ts on ts.id=twd.stage_id "

			+ "where twd.id=#{detailId}")
	WorkDetail selectDetail(long detailId);

	class WorkDetailMapperProvider {

		public String countProjectFaultByParms(FaultStatisticsRequestParm parm) {
			String sql = "SELECT tp.`name` x, count(twd.id) y FROM t_workdetail twd "
					//
					+ "LEFT JOIN t_worksheet tws ON twd.id = tws.detail_id "
					//
					+ "LEFT JOIN t_fault_detail tfd ON tws.fault_id = tfd.id "
					//
					+ "LEFT JOIN t_project tp ON tws.project_id = tp.id "
					//
					+ "WHERE tws.fault_type = 0 AND tfd.statistics=TRUE";
			sql += getSql(parm);
			sql += " GROUP BY tp.`name`";
			sql += " ORDER BY y DESC";
			sql += " LIMIT 10";
			return sql;
		}

		public String countPartsFaultByParms(FaultStatisticsRequestParm parm) {
			String sql = "SELECT twd.level_two x, count(twd.id) y FROM t_workdetail twd "
					//
					+ "LEFT JOIN t_worksheet tws ON twd.id = tws.detail_id "
					//
					+ "LEFT JOIN t_fault_detail tfd ON tws.fault_id = tfd.id "
					//
					+ "WHERE tws.fault_type = 0 AND tfd.statistics=TRUE";
			sql += getSql(parm);
			sql += " GROUP BY twd.level_two";
			sql += " ORDER BY y DESC";
			return sql;
		}

		public String countFaultNameByParms(FaultStatisticsRequestParm parm) {
			String sql = "SELECT  tws.fault_name x,count(tws.id) y FROM t_worksheet tws "
					//
					+ "LEFT JOIN t_workdetail twd ON twd.id = tws.detail_id "
					//
					+ "LEFT JOIN t_fault_detail tfd ON tws.fault_id = tfd.id "
					//
					+ "LEFT JOIN t_project tp ON tws.project_id = tp.id "
					//
					+ "WHERE tws.fault_type = 0 AND tfd.statistics=TRUE";
			sql += getSql(parm);
			sql += " GROUP BY x";
			return sql;
		}

		public String getTopFiveProjectFaultByParms(FaultStatisticsRequestParm parm) {
			String sql = "SELECT tp.id x, count(twd.id) count,tp.name y FROM t_workdetail twd "
					//
					+ "LEFT JOIN t_worksheet tws ON twd.id = tws.detail_id "
					//
					+ "LEFT JOIN t_fault_detail tfd ON tws.fault_id = tfd.id "
					//
					+ "LEFT JOIN t_project tp ON twd.project_id = tp.id "
					//
					+ "WHERE tws.fault_type = 0 AND tfd.statistics=TRUE";
			sql += getSql(parm);
			sql += " GROUP BY tp.`name` ";
			sql += " ORDER BY count DESC ";
			sql += " LIMIT 5";
			return sql;
		}

		private String getSql(FaultStatisticsRequestParm parm) {
			String sql = "";
			if (parm.getProjectId() != null) {
				sql += " AND tws.project_id=#{projectId}";
			}
			if (parm.getDoorTypeId() != null) {
				sql += " AND twd.door_type_id=#{doorTypeId}";
			}
			if (parm.getEffectId() != null) {
				sql += " AND twd.effect_id=#{effectId}";
			}
			if (parm.getModeId() != null) {
				sql += " AND twd.mode_id=#{modeId}";
			}
			if (parm.getStageId() != null) {
				sql += " AND twd.stage_id=#{stageId}";
			}
			if (StringUtil.isNotEmpty(parm.getStartTime())) {
				sql += " AND twd.fault_time >= #{startTime}";
			}
			if (StringUtil.isNotEmpty(parm.getEndTime())) {
				sql += " AND twd.fault_time <= #{endTime}";
			}
			return sql;
		}
	}

}
