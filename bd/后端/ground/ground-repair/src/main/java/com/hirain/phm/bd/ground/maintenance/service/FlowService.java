/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.maintenance.FlowException;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.param.AfterSaleInput;
import com.hirain.phm.bd.ground.maintenance.param.CommonInput;
import com.hirain.phm.bd.ground.maintenance.param.QualityInvestInput;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 上午9:33:00
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
public interface FlowService {

	String createAndCommit(WorkDetail detail) throws FlowException;

	WorkSheet createWorksheet(UnifiedFaultRecord fault);

	/**
	 * @param sheetId
	 * @param object
	 * @return
	 * @throws FlowException
	 */
	String commitWorkSheet(Long sheetId, WorkDetail object) throws FlowException;

	WorkSheet commitAfterSale(Long sheetId, boolean result, AfterSaleInput input) throws FlowException;

	/**
	 * @param sheetId
	 * @param result
	 * @param input
	 * @return
	 * @throws FlowException
	 */
	WorkSheet terminate(Long sheetId, boolean result, AfterSaleInput input) throws FlowException;

	/**
	 * @param sheetId
	 * @param result
	 * @param object
	 * @return
	 * @throws FlowException
	 */
	WorkSheet commitQualityInvest(Long sheetId, boolean result, QualityInvestInput object) throws FlowException;

	/**
	 * @param sheetId
	 * @param result
	 * @param object
	 * @return
	 * @throws FlowException
	 */
	WorkSheet commitQuality(Long sheetId, boolean result, CommonInput object) throws FlowException;

	/**
	 * @param sheetId
	 * @param result
	 * @param object
	 * @return
	 * @throws FlowException
	 */
	WorkSheet resolveProblem(Long sheetId, boolean result, CommonInput object) throws FlowException;

	/**
	 * @param sheetId
	 * @param result
	 * @param object
	 * @return
	 * @throws FlowException
	 */
	WorkSheet closeWorkSheet(Long sheetId, boolean result, CommonInput object) throws FlowException;

	/**
	 * @param sheetId
	 * @param result
	 * @param object
	 * @return
	 * @throws FlowException
	 */
	WorkSheet appeal(Long sheetId, boolean result, CommonInput object) throws FlowException;

	/**
	 * @param sheetId
	 * @param result
	 * @param object
	 * @return
	 * @throws FlowException
	 */
	WorkSheet problemRepetition(Long sheetId, boolean result, CommonInput object) throws FlowException;

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	String upload(MultipartFile file) throws IOException;

	/**
	 * @param filepaths
	 * @return
	 */
	String delete(String filepaths);
}
