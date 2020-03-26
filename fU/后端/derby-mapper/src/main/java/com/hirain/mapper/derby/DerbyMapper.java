/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.mapper.derby;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 13, 2019 6:10:22 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 13, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@RegisterMapper
public interface DerbyMapper<T> {

	/**
	 * 插入数据，自动生成id，允许非主键属性空值
	 * 
	 * @param record
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@InsertProvider(type = DerbyProvider.class, method = "dynamicSQL")
	int insertGenerateKey(T record);

	/**
	 * 更新数据，非主键属性空值也会更新
	 * 
	 * @param record
	 * @return
	 */
	@UpdateProvider(type = DerbyProvider.class, method = "dynamicSQL")
	int updateByKey(T record);
}
