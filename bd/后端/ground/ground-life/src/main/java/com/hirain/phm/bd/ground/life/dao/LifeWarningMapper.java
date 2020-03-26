package com.hirain.phm.bd.ground.life.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.github.pagehelper.util.StringUtil;
import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.life.domain.LifeItem;
import com.hirain.phm.bd.ground.life.domain.LifeWarning;
import com.hirain.phm.bd.ground.life.param.LifeWarningParam;
import com.hirain.phm.bd.ground.life.param.TodayLifeWarning;

public interface LifeWarningMapper extends CommonMapper<LifeWarning> {

	/**
	 * 根据自定义的TrainParam来查找对应的TrainParam结果集合
	 */
	@SelectProvider(type = LifeWarningMapperProvider.class, method = "findLifeWarningsByParams")
	List<LifeWarningParam> findLifeWarningsByParams(LifeWarningParam param);

	class LifeWarningMapperProvider {

		public String findLifeWarningsByParams(LifeWarningParam param) {
			// @SuppressWarnings("unused")
			// SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql = "SELECT" +
			// id
					"   t_life_warning.id," +
					// 日期
					"   t_life_warning.warning_time," +
					// 部件
					"   t_life_warning.lifeitem_id," +
					// 剩余寿命
					"	t_life_warning.remainder_life," +

					"  t_project.name project," +
					// 列车编号
					"   t_train.train_no," +
					// 车厢号
					"   t_life_warning.car_no," +
					// 车门地址
					"   t_life_warning.door_addr," +
					// 是否调试
					"	t_life_warning.debug_mode," +
					// 部件表
					"   t_life_item.item_name as lifeitem_name " +
					// 寿命预警信息表
					" FROM  t_life_warning" +
					// 列车表
					" LEFT JOIN t_train ON t_life_warning.train_id = t_train.id" +

					" Left join t_project on t_project.id=t_train.project_id" +
					// 部件表
					" LEFT JOIN t_life_item ON t_life_item.id=t_life_warning.lifeitem_id where true";
			if (StringUtil.isNotEmpty(param.getProject())) {
				sql += " and t_project.name=#{project}";
			}
			if (StringUtil.isNotEmpty(param.getTrainNo())) {
				sql += " and t_train.train_no = #{trainNo}";
			}
			if (param.getCarNo() != null) {
				sql += " and t_life_warning.car_no=#{carNo}";
			}
			if (param.getDoorAddr() != null) {
				sql += " and t_life_warning.door_addr=#{doorAddr}";
			}
			if (param.getStartTime() != null) {
				sql += " and t_life_warning.warning_time >=#{startTime} ";
			}
			if (param.getEndTime() != null) {
				sql += " and t_life_warning.warning_time <= #{endTime}";
			}
			if (param.getLifeItemId() != null) {
				sql += " and t_life_warning.lifeitem_id=#{lifeItemId}";
			}
			if (param.getDebugMode() != null) {
				sql += " and t_life_warning.debug_mode=#{debugMode}";
			}

			return sql;
		}

		public String listLifeWarningToday(String project, String trainNo) {
			String sql = "SELECT" +
			// 日期
					"   t_life_warning.warning_time," +
					// 剩余寿命
					"	t_life_warning.remainder_life," +
					// 车厢号
					"   t_life_warning.car_no," +
					// 车门地址
					"   t_life_warning.door_addr," +
					// 部件表
					"   t_life_item.item_name," +
					// 总寿命参考值
					"   t_life_traininfo.reference_value" +
					// 寿命预警信息表
					" FROM  t_life_warning" +
					// 列车表
					" LEFT JOIN t_train ON t_life_warning.train_id = t_train.id" +

					" Left join t_project on t_project.id=t_train.project_id" +
					// 部件表
					" LEFT JOIN t_life_item ON t_life_item.id=t_life_warning.lifeitem_id" +
					//
					" LEFT JOIN t_life_traininfo ON t_life_traininfo.lifeitem_id=t_life_item.id AND t_life_traininfo.train_id=t_train.id" +
					// 查询数据
					" where true";
			if (StringUtil.isNotEmpty(project)) {
				sql += " and t_project.name=#{project}";
			}
			if (StringUtil.isNotEmpty(trainNo)) {
				sql += " and t_train.train_no = #{trainNo}";
			}
			sql += " order by t_life_warning.warning_time desc";
			sql += " LIMIT 20";
			return sql;
		}

		public String listLifeWarningToday4Bode(String startTime, String endTime) {
			String sql = "SELECT" +
			// 日期
					"   t_life_warning.warning_time," +
					// 剩余寿命
					"	t_life_warning.remainder_life," +
					// 车厢号
					"   t_life_warning.car_no," +
					// 车门地址
					"   t_life_warning.door_addr," +
					// 部件表
					"   t_life_item.item_name," +
					// 总寿命参考值
					"   t_life_traininfo.reference_value" +
					// 寿命预警信息表
					" FROM  t_life_warning" +
					// 列车表
					" LEFT JOIN t_train ON t_life_warning.train_id = t_train.id" +

					" Left join t_project on t_project.id=t_train.project_id" +
					// 部件表
					" LEFT JOIN t_life_item ON t_life_item.id=t_life_warning.lifeitem_id" +
					//
					" LEFT JOIN t_life_traininfo ON t_life_traininfo.lifeitem_id=t_life_item.id AND t_life_traininfo.train_id=t_train.id" +
					// 查询数据
					" where true";
			if (StringUtil.isNotEmpty(startTime)) {
				sql += " and t_life_warning.warning_time >= #{startTime}";
			}
			if (StringUtil.isNotEmpty(endTime)) {
				sql += " and t_life_warning.warning_time <= #{endTime}";
			}
			sql += " order by t_life_warning.warning_time desc";
			return sql;
		}

	}

	@Select("SELECT * FROM t_life_item")
	List<LifeItem> selectAllGroupByParam();

	@SelectProvider(type = LifeInfoMapperProvider.class, method = "findLifeInfosByCityAndLine")
	List<LifeItem> findLifeItemsByProject(String project);

	class LifeInfoMapperProvider {

		public String findLifeInfosByCityAndLine(String project) {
			String sql = "SELECT t_life_item.id,t_life_item.item_name FROM t_life_warning "

					+ "LEFT JOIN t_train  ON t_train.id = t_life_warning.train_id "

					+ "LEFT JOIN t_project  ON t_train.project_id=t_project.id"

					+ " left join t_life_item on t_life_item.id= t_life_warning.lifeitem_id"

					+ " where true";
			if (StringUtil.isNotEmpty(project)) {
				sql += " and t_project.name = #{project}";
			}
			sql += " GROUP BY t_life_item.id";
			return sql;
		}
	}

	@Select("SELECT remainder_life FROM t_life_warning where train_id=#{trainID} and lifeitem_id=#{itemID}")
	LifeWarning findLifeWarningByTrainIDItemId(Integer trainID, Integer itemID);

	@SelectProvider(type = LifeWarningMapperProvider.class, method = "listLifeWarningToday")
	List<TodayLifeWarning> listLifeWarningToday(String project, String trainNo);

	/**
	 * @author zepei.tao
	 */
	@SelectProvider(type = LifeWarningMapperProvider.class, method = "listLifeWarningToday4Bode")
	List<TodayLifeWarning> listLifeWarningToday4Bode(String startTime, String endTime);

}