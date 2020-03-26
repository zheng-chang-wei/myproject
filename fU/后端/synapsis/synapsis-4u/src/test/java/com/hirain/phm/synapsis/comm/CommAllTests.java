/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.comm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * @Version 1.0   
 * @Author jianwen.xin@hirain.com  
 * @Created Dec 6, 2019 1:52:53 PM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 6, 2019     jianwen.xin@hirain.com             1.0        create file 
 */
@RunWith(Suite.class)
@SuiteClasses({ TestMessageCodec.class, TestTargetSelector.class })
public class CommAllTests {

}
