package com.hirain.phm.bd.ground.statistics.dao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.hirain.phm.bd.ground.statistics.domain.MonthStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.StatisticsParam;
import com.hirain.phm.bd.ground.statistics.domain.TypeResult;

import tk.mybatis.mapper.common.Marker;

@Repository
public interface SimpleStatisticsMapper extends Marker {

	@SelectProvider(type = SimpleStatisticsSelectProvider.class, method = "getTrainIdByTrainNo")
	List<String> getTrainIdByTrainNo(String trainNo);

	@SelectProvider(type = SimpleStatisticsSelectProvider.class, method = "countByMonth")
	@ResultType(MonthStatisticsResult.class)
	List<MonthStatisticsResult> countByMonth(StatisticsParam param);

	@SelectProvider(type = SimpleStatisticsSelectProvider.class, method = "countByYear")
	@ResultType(MonthStatisticsResult.class)
	List<MonthStatisticsResult> countByYear(StatisticsParam param);

	@SelectProvider(type = SimpleStatisticsSelectProvider.class, method = "countByFaultType")
	@ResultType(TypeResult.class)
	List<TypeResult> countByFaultType(StatisticsParam param);

	@SelectProvider(type = SimpleStatisticsSelectProvider.class, method = "countBySubHealthType")
	@ResultType(TypeResult.class)
	List<TypeResult> countBySubhealthType(StatisticsParam param);

	@SelectProvider(type = SimpleStatisticsSelectProvider.class, method = "countFault")
	Double countFault(StatisticsParam param);

	@SelectProvider(type = SimpleStatisticsSelectProvider.class, method = "countSubhealth")
	Double countSubhealth(StatisticsParam param);

	class SimpleStatisticsSelectProvider {

		public String getTrainIdByTrainNo(String trainNo) {
			String sql = "select id from t_train where train_no in (";
			sql += trainNo;
			sql += ") and project_id in (select id from t_project where line='7' and city='深圳')";
			return sql;
		}

		public String countByMonth(StatisticsParam param) {
			String trains = getTrainString(param);
			StringBuilder sql = new StringBuilder();
			sql.append("select  r.year,r.month,sum(r.faults) fault,sum(r.subhealth) subhealth, sum(r.faults+r.subhealth) total from (\r\n");
			sql.append(
					"select date_format(fault_time,'%Y%m') time,year(fault_time) year, month(fault_time) month,count(id) faults,0 as subhealth from t_fault_detail \r\n");
			sql.append("where train_id in ").append(trains).append("\r\n");
			sql.append("group by time\r\n");
			sql.append("union\r\n");
			sql.append(
					"select date_format(start_time,'%Y%m') time,year(start_time) year,month(start_time) month,0,count(id) subhealth from t_subhealth_detail\r\n");
			sql.append("where train_id in").append(trains).append("\r\n");
			sql.append("group by time\r\n");
			sql.append(") r group by r.time order by r.time desc");
			return sql.toString();
		}

		public String countByYear(StatisticsParam param) {
			String trains = getTrainString(param);
			StringBuilder sql = new StringBuilder();
			sql.append("select  r.year,r.month,sum(r.faults) fault,sum(r.subhealth) subhealth, sum(r.faults+r.subhealth) total from (\r\n");
			sql.append(
					"select date_format(fault_time,'%Y%m') time,year(fault_time) year, month(fault_time) month,count(id) faults,0 as subhealth from t_fault_detail \r\n");
			sql.append("where year(fault_time)=").append(param.getYear());
			sql.append(" and ").append("train_id in ").append(trains).append("\r\n");
			sql.append(" group by time\r\n");
			sql.append("union\r\n");
			sql.append(
					"select date_format(start_time,'%Y%m') time,year(start_time) year,month(start_time) month,0,count(id) subhealth from t_subhealth_detail\r\n");
			sql.append("where year(start_time)=").append(param.getYear());
			sql.append(" and ").append("train_id in ").append(trains).append("\r\n");
			sql.append(" group by time\r\n");
			sql.append(") r group by r.time");
			return sql.toString();
		}

		public String countByFaultType(StatisticsParam param) {
			String trains = getTrainString(param);
			StringBuilder sql = new StringBuilder();
			sql.append("select t2.fault_name type,count(t1.id) percent\r\n");
			sql.append("from t_fault_detail as t1,t_fault_info as t2\r\n");
			sql.append("where t1.fault_info_id=t2.id \r\n");
			String condition = getFaultCondition(param, trains);
			sql.append(condition);
			sql.append("group by t1.fault_info_id");
			return sql.toString();
		}

		private String getFaultCondition(StatisticsParam param, String trains) {
			StringBuilder sql = new StringBuilder();
			if (param.getStart_month() != null) {
				sql.append("and month(t1.fault_time)>=").append(param.getStart_month()).append(" ");
			}
			if (param.getEnd_month() != null) {
				sql.append("and ").append("month(t1.fault_time)<=").append(param.getEnd_month()).append(" ");
			}
			sql.append("and ").append("year(t1.fault_time)=").append(param.getYear()).append(" ");
			sql.append("and ").append("t1.train_id in").append(trains).append("\r\n");
			return sql.toString();
		}

		public String countBySubHealthType(StatisticsParam param) {
			String trains = getTrainString(param);
			StringBuilder sql = new StringBuilder();
			sql.append("select t2.subhealth_name type,count(t1.id) percent\r\n");
			sql.append("from t_subhealth_detail as t1,t_subhealth_type as t2\r\n");
			sql.append("where t1.subhealth_info_id=t2.id \r\n");
			String condition = getSubHealthCondition(param, trains);
			sql.append(condition);
			sql.append("group by t1.subhealth_info_id");

			return sql.toString();
		}

		private String getSubHealthCondition(StatisticsParam param, String trains) {
			StringBuilder sql = new StringBuilder();
			if (param.getStart_month() != null) {
				sql.append("and month(t1.start_time)>=").append(param.getStart_month()).append(" ");
			}
			if (param.getEnd_month() != null) {
				sql.append("and ").append("month(t1.start_time)<=").append(param.getEnd_month()).append(" ");
			}
			sql.append("and ").append("year(t1.start_time)=").append(param.getYear()).append(" ");
			sql.append("and ").append("t1.train_id in").append(trains).append("\r\n");
			return sql.toString();
		}

		public String countFault(StatisticsParam param) {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*)\r\n");
			sql.append("from t_fault_detail\r\n");
			sql.append("where train_id in ").append(getTrainString(param));
			if (param.getStart_month() != null) {
				sql.append(" and month(fault_time)>=").append(param.getStart_month());
			}
			if (param.getEnd_month() != null) {
				sql.append(" and month(fault_time)<=").append(param.getEnd_month());
			}
			sql.append(" and year(fault_time)=").append(param.getYear());
			return sql.toString();
		}

		public String countSubhealth(StatisticsParam param) {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*)\r\n");
			sql.append("from t_subhealth_detail\r\n");
			sql.append("where train_id in ").append(getTrainString(param));
			if (param.getStart_month() != null) {
				sql.append(" and month(start_time)>=").append(param.getStart_month());
			}
			if (param.getEnd_month() != null) {
				sql.append(" and month(start_time)<=").append(param.getEnd_month());
			}
			sql.append(" and year(start_time)=").append(param.getYear());
			return sql.toString();
		}

		private String getTrainString(StatisticsParam param) {
			List<String> trainList = Arrays.asList(param.getTrains());
			String trains = trainList.stream().collect(Collectors.joining(",", "(", ")"));
			return trains;
		}
	}
}
