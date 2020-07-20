/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.controller;

import com.hirain.phm.bd.ground.train.domain.Project;

/**
 * @Version 1.0   
 * @Author jianwen.xin@hirain.com  
 * @Created Mar 27, 2020 6:57:14 PM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Mar 27, 2020     jianwen.xin@hirain.com             1.0        create file 
 */
public interface ProjectGateWay {

	Project selectProjectByName(String project);

}