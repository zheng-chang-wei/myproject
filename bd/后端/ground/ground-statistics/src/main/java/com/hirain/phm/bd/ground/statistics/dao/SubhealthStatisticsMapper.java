package com.hirain.phm.bd.ground.statistics.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;
import com.hirain.phm.bd.ground.statistics.service.CommonCountStatisticsMapper;
import com.hirain.phm.bode.core.util.StringUtil;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月12日 下午4:04:21
 * @Description
 *              <p>
 *              博得亚健康统计
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月12日 changwei.zheng@hirain.com 1.0 create file
 */
public interface SubhealthStatisticsMapper extends CommonCountStatisticsMapper {

	@SelectProvider(type = SubhealthStatisticsSelectProvider.class, method = "countUnhealthByProjectName")
	List<CommonStatisticsResult> statisticsCountByProjectName(String projectName, String year);

	class SubhealthStatisticsSelectProvider {

		public String countUnhealthByProjectName(String projectName, String year) {
			String sql = "SELECT MONTH(t_subhealth_detail.start_time) x,"
					//
					+ "COUNT(*) y "
					//
					+ "FROM t_subhealth_detail "
					//
					+ "LEFT JOIN t_train ON t_train.id = t_subhealth_detail.train_id "
					//
					+ "LEFT JOIN t_project ON t_project.id = t_train.project_id "
					//
					+ "WHERE t_subhealth_detail.statistics=true AND YEAR(t_subhealth_detail.start_time)=" + year;

			if (StringUtil.isNotEmpty(projectName)) {
				sql += " and t_project.`name`='" + projectName + "' ";
			}
			sql += " GROUP BY DATE_FORMAT(t_subhealth_detail.start_time,'%Y-%m')";
			return sql;
		}

	}

}
