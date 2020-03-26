/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.protocol.ecn;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * @Version 1.0   
 * @Author jianwen.xin@hirain.com  
 * @Created Feb 21, 2020 10:26:31 AM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Feb 21, 2020     jianwen.xin@hirain.com             1.0        create file 
 */
@RunWith(Suite.class)
@SuiteClasses({ ECNGroupConverterTest.class, ECNVariableConverterTest.class, ECNVariableQueryTest.class, ParseServiceTest.class })
public class AllTests {

}
