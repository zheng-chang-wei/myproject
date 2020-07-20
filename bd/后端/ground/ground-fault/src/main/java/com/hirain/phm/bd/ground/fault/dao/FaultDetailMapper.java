package com.hirain.phm.bd.ground.fault.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.github.pagehelper.util.StringUtil;
import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.fault.domain.FaultDetail;
import com.hirain.phm.bd.ground.fault.param.FaultDetailRequestParams;
import com.hirain.phm.bd.ground.fault.param.FaultDetailResponseParams;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年5月6日 下午5:30:24
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月6日 changwei.zheng@hirain.com 1.0 create file
 */
public interface FaultDetailMapper extends CommonMapper<FaultDetail> {

	@SelectProvider(type = FaultDetailMapperProvider.class, method = "findFaultDetailsByParms")
	List<FaultDetailResponseParams> findFaultDetailsByParms(FaultDetailRequestParams faultDetailParms);

	/**
	 * @author zepei.tao
	 */
	@SelectProvider(type = FaultDetailMapperProvider.class, method = "selectToday")
	List<FaultDetailResponseParams> selectToday(String startTime, String endTime);

	class FaultDetailMapperProvider {

		public String selectToday(String startTime, String endTime) {
			String sql = "select tp.name project,tt.train_no,td.id,td.fault_info_id,td.car_no,td.door_addr,td.debug_mode,td.fault_time,ti.fault_name"

					+ " from t_fault_detail td " +

					" left join t_fault_info ti on td.fault_info_id=ti.id" +

					" left join t_train tt on tt.id=td.train_id" +

					" left join t_project tp on tp.id=tt.project_id";

			sql += " where true";
			if (StringUtil.isNotEmpty(startTime)) {
				sql += " and td.fault_time >= #{startTime}";
			}
			if (StringUtil.isNotEmpty(endTime)) {
				sql += " and td.fault_time <= #{endTime}";
			}
			sql += " order by td.fault_time desc";
			return sql;
		}

		public String findFaultDetailsByParms(FaultDetailRequestParams faultDetailParms) {
			String sql = "SELECT" +

					" tp.name project," +
					// 列车编号
					"	tt.train_no," +
					// 故障详情id
					"	td.id," +
					// faultInfoId
					" td.fault_info_id," +
					// 车厢号
					"	td.car_no," +
					// 门地址
					"	td.door_addr," +
					// 调试
					"	td.debug_mode," +
					// 故障时间
					"	td.fault_time," +
					//
					"	td.statistics," +
					// 故障名称
					"	ti.fault_name," +
					// 故障代码
					"	ti.fault_code" +

					// 故障详情表
					" FROM t_fault_detail td " +
					// 故障信息表
					"LEFT JOIN t_fault_info ti ON td.fault_info_id = ti.id " +
					// 列车表
					"LEFT JOIN t_train tt ON tt.id = td.train_id " +

					"LEFT JOIN t_project tp on tp.id=tt.project_id " +

					"where true";
			if (StringUtil.isNotEmpty(faultDetailParms.getProject())) {
				sql += " and tp.name=#{project}";
			}
			if (StringUtil.isNotEmpty(faultDetailParms.getTrainNo())) {
				sql += " and tt.train_no = #{trainNo}";
			}
			if (faultDetailParms.getCarNo() != null) {
				sql += " and td.car_no = #{carNo}";
			}
			if (StringUtil.isNotEmpty(faultDetailParms.getDoorAddr())) {
				sql += " and td.door_addr = #{doorAddr}";
			}
			if (faultDetailParms.getDebugMode() != null) {
				sql += " and td.debug_mode = #{debugMode}";
			}
			if (faultDetailParms.getStartTime() != null) {
				sql += " and td.fault_time >= #{startTime}";
			}
			if (faultDetailParms.getEndTime() != null) {
				sql += " and td.fault_time <= #{endTime}";
			}
			if (StringUtil.isNotEmpty(faultDetailParms.getFaultName())) {
				sql += " and ti.fault_name = #{faultName}";
			}
			if (StringUtil.isNotEmpty(faultDetailParms.getFaultCode())) {
				sql += " and ti.fault_code = #{faultCode}";
			}
			sql += " order by td.fault_time desc";
			return sql;
		}
	}

}