/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.ftp;

import org.apache.commons.pool.PoolableObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jun 14, 2019 2:56:21 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 14, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Component
@Configuration
public class FtpServiceFactory implements PoolableObjectFactory<FTPService> {

	/**
	 * @see org.apache.commons.pool.PoolableObjectFactory#makeObject()
	 */
	@Override
	public FTPService makeObject() throws Exception {
		return getFtpService();
	}

	@Bean
	@Scope("prototype")
	public FTPService getFtpService() {
		return new FTPServiceImpl();
	}

	/**
	 * @see org.apache.commons.pool.PoolableObjectFactory#destroyObject(java.lang.Object)
	 */
	@Override
	public void destroyObject(FTPService obj) throws Exception {

	}

	/**
	 * @see org.apache.commons.pool.PoolableObjectFactory#validateObject(java.lang.Object)
	 */
	@Override
	public boolean validateObject(FTPService obj) {
		return true;
	}

	/**
	 * @see org.apache.commons.pool.PoolableObjectFactory#activateObject(java.lang.Object)
	 */
	@Override
	public void activateObject(FTPService obj) throws Exception {

	}

	/**
	 * @see org.apache.commons.pool.PoolableObjectFactory#passivateObject(java.lang.Object)
	 */
	@Override
	public void passivateObject(FTPService obj) throws Exception {

	}

}
