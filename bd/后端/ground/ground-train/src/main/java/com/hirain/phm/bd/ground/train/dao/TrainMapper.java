package com.hirain.phm.bd.ground.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.github.pagehelper.util.StringUtil;
import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.param.TrainParam;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Apr 25, 2019 3:51:06 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 25, 2019 zepei.tao@hirain.com 1.0 create file
 */

public interface TrainMapper extends CommonMapper<Train> {

	@SelectProvider(type = TrainMapperProvider.class, method = "findTrains")
	List<Train> findTrains(TrainParamHeader trainParms);

	/**
	 * 根据自定义的TrainParam来查找对应的TrainParam结果集合
	 */
	@SelectProvider(type = TrainMapperProvider.class, method = "findTrainsByParams")
	List<TrainParam> findTrainsByParams(TrainParam trainParms);

	@Select("select t_train.* from t_train  left join t_project_user tpu on tpu.project_id=t_train.project_id where tpu.user_id=#{userId}")
	List<Train> findTrainsByUserId(Long userId);

	/**
	 * @param project
	 * @param train
	 * @return
	 */
	@Select("select t_train.* from t_project left join t_train on t_project.id=t_train.project_id where t_project.name=#{project} and t_train.train_no=#{train}")
	Train selectByProjectAndNo(String project, String train);

	/**
	 * @param city
	 * @param line
	 * @return
	 */
	@Select("select t_train.* from t_project left join t_train on t_project.id=t_train.project_id where t_project.city=#{city} and t_project.line=#{line}")
	List<Train> selectByCityAndLine(String city, String line);

	/**
	 * @param project
	 * @return
	 */
	@Select("select t_train.* from t_project left join t_train on t_project.id=t_train.project_id where t_project.name=#{project}")
	List<Train> selectByProject(String project);

	/**
	 * @return
	 */
	@Select("select sum(door_count) from t_train where train_online=1")
	Integer selectCountOfOnlineDoor();

	class TrainMapperProvider {

		public String findTrainsByParams(TrainParam trainParms) {
			String sql = "SELECT" +

					"  t_project.name project," +
					// 列车编号
					"	t_train.train_no," +
					// 车辆当前状态
					"	t_train.train_status," +
					// 车辆在线状态
					"	t_train.train_online from t_train "

					+ "left join t_project on t_train.project_id=t_project.id" + " where true";
			if (StringUtil.isNotEmpty(trainParms.getProject())) {
				sql += " and t_project.name=#{project}";
			}
			if (StringUtil.isNotEmpty(trainParms.getTrainNo())) {
				sql += " and t_train.train_no = #{trainNo}";
			}
			sql += " order by t_train.train_online desc ,t_train.train_status";
			return sql;
		}

		public String findTrains(TrainParamHeader trainParms) {
			String sql = "SELECT"

					+ "	t_train.id,t_train.train_no from t_train "

					+ "left join t_project on t_train.project_id=t_project.id" + " where true";
			if (StringUtil.isNotEmpty(trainParms.getProject())) {
				sql += " and t_project.name=#{project}";
			}
			if (StringUtil.isNotEmpty(trainParms.getTrainNo())) {
				sql += " and t_train.train_no = #{trainNo}";
			}
			sql += " order by t_train.train_no";
			return sql;
		}

	}

}