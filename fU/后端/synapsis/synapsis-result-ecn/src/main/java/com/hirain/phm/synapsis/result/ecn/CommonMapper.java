package com.hirain.phm.synapsis.result.ecn;

import com.hirain.mapper.derby.DerbyMapper;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 2:02:00 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               6
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface CommonMapper<T> extends Mapper<T>, DerbyMapper<T> {

}
