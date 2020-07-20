/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.rbac.CommonMapper;
import com.hirain.phm.synapsis.update.domain.VersionInfo;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 9, 2020 2:42:37 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 9, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface VersionInfoMapper extends CommonMapper<VersionInfo> {

	@Delete("delete from t_version_info where package_name=#{packageName}")
	void deleteVersionInfoByPackageName(String packageName);

	@Select("select * from t_version_info where package_name=#{packageName}")
	VersionInfo selectByPackageName(String packageName);

	@Select("select * from t_version_info order by upload_time desc")
	List<VersionInfo> listVersions();
}
