/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.page;

import java.util.List;
import java.util.function.Supplier;

import com.hirain.phm.synapsis.response.PageResultBean;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月10日 下午6:54:11
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月10日 jianwen.xin@hirain.com 1.0 create file
 */

public interface PageService {

	<T> PageResultBean<List<T>> selectByPage(QueryRequest request, Supplier<List<T>> s);
}
