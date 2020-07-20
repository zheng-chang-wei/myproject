package com.hirain.phm.synapsis.setting.common;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IService<T> {

	List<T> selectAll();

	T selectByKey(Object key);

	int insertGenearteKey(T entity);

	int insert(T entity);

	int delete(Object key);

	int deleteByExample(Object example);

	int batchDelete(List<String> list, String property, Class<T> clazz);

	int updateAll(T entity);

	int updateNotNull(T entity);

	List<T> selectByExample(Object example);

	int insertNotNull(T entity);
}