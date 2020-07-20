/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午4:24:27
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
public interface WorkSheetMapper extends CommonMapper<WorkSheet> {

	@Select("select * from t_worksheet where id=#{id} for update")
	WorkSheet selectWithLock(Long id);

	@SelectProvider(type = SheetMapperProvider.class, method = "selectByLines")
	List<WorkSheet> selectByLines(List<Integer> trainIds);

	@Select("select * from t_worksheet where user_id=#{userId} order by create_time desc")
	List<WorkSheet> selectByUserId(Long userId);

	@SelectProvider(type = SheetMapperProvider.class, method = "selectEditSheet")
	List<WorkSheet> selectEditSheet(@Param("ids") List<Long> ids);

	@SelectProvider(type = SheetMapperProvider.class, method = "listWorkSheetWithDetail")
	@Results({

			@Result(property = "detail", column = "detail_id", one = @One(select = "com.hirain.phm.bd.ground.maintenance.dao.WorkDetailMapper.selectDetail"))

	})
	List<WorkSheet> listWorkSheetWithDetail(String project, String train, Integer faultType, Long userId, Integer limit, Integer offset);

	@SelectProvider(type = SheetMapperProvider.class, method = "listWorkSheetOfProjects")
	@Results({

			@Result(property = "detail", column = "detail_id", one = @One(select = "com.hirain.phm.bd.ground.maintenance.dao.WorkDetailMapper.selectDetail"))

	})
	List<WorkSheet> listWorkSheetOfProjects(@Param("ids") List<Long> ids, String train, Integer faultType, Long userId, Integer limit,
			Integer offset);

	@SelectProvider(type = SheetMapperProvider.class, method = "selectBySheetId")
	@Results({

			@Result(property = "detail", column = "detail_id", one = @One(select = "com.hirain.phm.bd.ground.maintenance.dao.WorkDetailMapper.selectDetail"))

	})
	WorkSheet selectBySheetId(@Param("id") long id);

	@Select("select fault_type from t_worksheet group by fault_type")
	List<Integer> getFaultTypes();

	@SelectProvider(type = SheetMapperProvider.class, method = "countWorkSheetOfProjects")
	Integer countWorkSheetOfProjects(@Param("ids") List<Long> ids, String train, Integer faultType, Long userId);

	@SelectProvider(type = SheetMapperProvider.class, method = "countWorksheetWithDetail")
	Integer countWorksheetWithDetail(String project, String train, Integer faultType, Long userId);

	class SheetMapperProvider {

		public String selectBySheetId(long id) {
			String sql = "select tws.*,tp.name project, tu.user_name user, tm.mode_name fault_mode from t_worksheet tws ";
			sql += "left join t_project tp on tws.project_id=tp.id ";
			sql += "left join t_user tu on tu.user_id=tws.user_id ";
			sql += "left join t_mode tm on tm.id=tws.mode_id ";
			sql += "where tws.id=#{id}";
			return sql;
		}

		public String listWorkSheetWithDetail(String project, String train, Integer faultType, Long userId, Integer limit, Integer offset) {
			String sql = "select tws.*,tp.name project, tu.user_name user, tm.mode_name fault_mode from t_worksheet tws ";
			sql += "left join t_project tp on tws.project_id=tp.id ";
			sql += "left join t_user tu on tu.user_id=tws.user_id ";
			sql += "left join t_mode tm on tm.id=tws.mode_id ";
			sql += "where ((tws.state='创建工单' and (tws.user_id is NULL or tws.user_id=#{userId})) ";
			sql += " or tws.state <> '创建工单')";
			if (project != null) {
				sql += "and tp.name=#{project} ";
			}
			if (train != null) {
				sql += "and tws.train_id=#{train} ";
			}
			if (faultType != null) {
				sql += "and tws.fault_type=#{faultType} ";
			}
			sql += "order by tws.fault_time desc, tws.id desc ";
			if (limit != null && offset != null) {
				sql += " limit " + offset + "," + limit;
			}
			return sql;
		}

		public String countWorksheetWithDetail(String project, String train, Integer faultType, Long userId) {
			String sql = "select count(tws.id) from t_worksheet tws ";
			sql += "left join t_project tp on tws.project_id=tp.id ";
			sql += "where ((tws.state='创建工单' and (tws.user_id is NULL or tws.user_id=#{userId}))";
			sql += "or tws.state <> '创建工单')";
			if (project != null) {
				sql += "and tp.name=#{project} ";
			}
			if (train != null) {
				sql += "and tws.train_id=#{train} ";
			}
			if (faultType != null) {
				sql += "and tws.fault_type=#{faultType} ";
			}
			return sql;
		}

		public String listWorkSheetOfProjects(List<Long> ids, String train, Integer faultType, Long userId, Integer limit, Integer offset) {
			String sql = "select tws.*,tp.name project, tu.user_name user, tm.mode_name fault_mode from t_worksheet tws ";
			sql += "left join t_project tp on tws.project_id=tp.id ";
			sql += "left join t_user tu on tu.user_id=tws.user_id ";
			sql += "left join t_mode tm on tm.id=tws.mode_id ";
			sql += "where tws.state='创建工单' and tws.project_id in ";
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			ids.forEach(t -> {
				sb.append(t).append(",");
			});
			sql += sb.substring(0, sb.length() - 1) + ") ";
			sql += " and (tws.user_id is NULL or tws.user_id=#{userId})";
			if (train != null) {
				sql += "and tws.train_id=#{trainNo} ";
			}
			if (faultType != null) {
				sql += "and tws.fault_type=#{faultType} ";
			}
			sql += "order by tws.fault_time desc, tws.id desc  ";
			if (limit != null && offset != null) {
				sql += " limit " + offset + "," + limit;
			}
			return sql;
		}

		public String countWorkSheetOfProjects(List<Long> ids, String train, Integer faultType, Long userId) {
			String sql = "select count(tws.id) from t_worksheet tws ";
			sql += "where tws.state='创建工单' and tws.project_id in ";
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			ids.forEach(t -> {
				sb.append(t).append(",");
			});
			sql += sb.substring(0, sb.length() - 1) + ") ";
			sql += " and (tws.user_id is NULL or tws.user_id=#{userId})";
			if (train != null) {
				sql += "and tws.train_id=#{trainNo} ";
			}
			if (faultType != null) {
				sql += "and tws.fault_type=#{faultType} ";
			}
			return sql;
		}

		public String selectEditSheet(List<Long> ids) {
			String sql = "select t_worksheet.*,t_project.name project from t_worksheet" +

					" left join t_project on t_project.id=t_worksheet.project_id" +

					" where state='创建工单' and project_id in ";
			StringBuilder sb = new StringBuilder();
			ids.forEach(t -> {
				sb.append(t).append(",");
			});
			String in = sb.substring(0, sb.length() - 1);
			String out = sql + "(" + in + ")";
			System.err.println(out);
			return out;
		}

		public String selectByLines(List<Integer> trainIds) {
			String sql = "select * from t_worksheet where state in ('创建工单','关闭')  ";
			StringBuilder sb = new StringBuilder();
			sb.append(sql);
			if (trainIds != null) {
				sb.append("and train_id in");
				sb.append(" (");
				trainIds.forEach(t -> {
					sb.append(t).append(",");
				});
				sb.deleteCharAt(sb.length() - 1);
				sb.append(") ");
			}
			System.err.println(sb.toString());
			return sb.toString();
		}
	}
}
