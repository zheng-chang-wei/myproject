/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.authority.domain.User;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Jul 25, 2019 9:32:41 AM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jul 25, 2019     zepei.tao@hirain.com             1.0        create file 
 */
public interface UserExcelService {

	public List<User> resolveExcel(MultipartFile file) throws Exception;
}
