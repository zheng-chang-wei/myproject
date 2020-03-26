/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.mapper.derby;

import java.util.Set;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.Version;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.version.VersionException;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 13, 2019 6:39:58 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 13, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class SynapsisSqlHelper {

	/**
	 * insert-values()列
	 *
	 * @param entityClass
	 * @param skipId
	 *            是否从列中忽略id类型
	 * @param notNull
	 *            是否判断!=null
	 * @param notEmpty
	 *            是否判断String类型!=''
	 * @return
	 */
	public static String insertValuesColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
		StringBuilder sql = new StringBuilder();
		sql.append("<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
		// 获取全部列
		Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
		// 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
		for (EntityColumn column : columnSet) {
			if (!column.isInsertable()) {
				continue;
			}
			if (skipId && column.isId()) {
				continue;
			}
			if (notNull) {
				sql.append(SqlHelper.getIfNotNull(column, getColumnHolder(column) + ",", notEmpty));
			} else {
				sql.append(getColumnHolder(column) + ",");
			}
		}
		sql.append("</trim>");
		return sql.toString();
	}

	public static String updateSetColumns(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty) {
		StringBuilder sql = new StringBuilder();
		sql.append("<set>");
		// 获取全部列
		Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
		// 对乐观锁的支持
		EntityColumn versionColumn = null;
		// 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
		for (EntityColumn column : columnSet) {
			if (column.getEntityField().isAnnotationPresent(Version.class)) {
				if (versionColumn != null) {
					throw new VersionException(entityClass.getCanonicalName() + " 中包含多个带有 @Version 注解的字段，一个类中只能存在一个带有 @Version 注解的字段!");
				}
				versionColumn = column;
			}
			if (!column.isId() && column.isUpdatable()) {
				if (column == versionColumn) {
					Version version = versionColumn.getEntityField().getAnnotation(Version.class);
					String versionClass = version.nextVersion().getCanonicalName();
					// version = ${@tk.mybatis.mapper.version@nextVersionClass("versionClass", version)}
					sql.append(column.getColumn()).append(" = ${@tk.mybatis.mapper.version.VersionUtil@nextVersion(").append("@").append(versionClass)
							.append("@class, ").append(column.getProperty()).append(")},");
				} else if (notNull) {
					sql.append(SqlHelper.getIfNotNull(entityName, column, getColumnEqualsHolder(entityName, column) + ",", notEmpty));
				} else {
					sql.append(getColumnEqualsHolder(entityName, column) + ",");
				}
			} else if (column.isId() && column.isUpdatable()) {
				// set id = id,
				sql.append(column.getColumn()).append(" = ").append(column.getColumn()).append(",");
			}
		}
		sql.append("</set>");
		return sql.toString();
	}

	/**
	 * @param entityName
	 * @param column
	 * @return
	 */
	private static String getColumnEqualsHolder(String entityName, EntityColumn column) {
		return column.getColumn() + " = " + getColumnHolder(column);
	}

	private static String getColumnHolder(EntityColumn column) {
		StringBuffer sb = new StringBuffer("#{");
		sb.append(column.getProperty());
		sb.append(",jdbcType=");
		JdbcType jdbcType = getJdbcType(column.getJavaType());
		sb.append(jdbcType.toString());

		sb.append("}");
		return sb.toString();
	}

	/**
	 * 根据java类型获得jdbc类型
	 * 
	 * @param javaType
	 */
	private static JdbcType getJdbcType(Class<?> javaType) {
		if (javaType.getName().equals("java.lang.String")) {
			return JdbcType.VARCHAR;
		} else if (javaType.getName().equals("java.lang.Integer")) {
			return JdbcType.INTEGER;
		} else if (javaType.getName().equals("java.lang.Long")) {
			return JdbcType.BIGINT;
		} else if (javaType.getName().equals("java.util.Date")) {
			return JdbcType.TIMESTAMP;
		} else if (javaType.getName().equals("java.lang.Double")) {
			return JdbcType.DOUBLE;
		} else if (javaType.getName().equals("java.lang.Double")) {
			return JdbcType.BOOLEAN;
		}
		return JdbcType.VARCHAR;
	}
}
