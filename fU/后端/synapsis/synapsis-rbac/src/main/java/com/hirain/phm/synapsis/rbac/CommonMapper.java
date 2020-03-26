package com.hirain.phm.synapsis.rbac;

import com.hirain.mapper.derby.DerbyMapper;

import tk.mybatis.mapper.common.Mapper;

public interface CommonMapper<T> extends Mapper<T>, DerbyMapper<T> {

}