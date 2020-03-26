/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.common.annotation.Log;
import com.hirain.phm.bd.ground.common.model.PageResultBean;
import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.common.page.QueryRequest;
import com.hirain.phm.bd.ground.maintenance.FlowException;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.domain.WorkStep;
import com.hirain.phm.bd.ground.maintenance.export.WorksheetExportService;
import com.hirain.phm.bd.ground.maintenance.param.AfterSaleInput;
import com.hirain.phm.bd.ground.maintenance.param.CommitBean;
import com.hirain.phm.bd.ground.maintenance.param.CommonInput;
import com.hirain.phm.bd.ground.maintenance.param.FaultType;
import com.hirain.phm.bd.ground.maintenance.param.QualityInvestInput;
import com.hirain.phm.bd.ground.maintenance.param.SheetCountResponse;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetRecord;
import com.hirain.phm.bd.ground.maintenance.param.WorksheetPacket;
import com.hirain.phm.bd.ground.maintenance.service.FlowReadService;
import com.hirain.phm.bd.ground.maintenance.service.FlowService;

import io.swagger.annotations.Api;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 上午9:42:39
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
@Api(value = "维修履历controller", tags = { "维修流程操作接口" })
@RestController
@RequestMapping("/repair")
public class MaintenanceController {

	@Autowired
	private FlowService flowService;

	@Autowired
	private FlowReadService readService;

	@Autowired
	private WorksheetExportService exportService;

	@GetMapping("/dashboard")
	public ResultBean<SheetCountResponse> countSheets() {
		return new ResultBean<>(readService.countSheets());
	}

	@GetMapping("/worksheets")
	public PageResultBean<WorkSheetRecord> findWorksheets(QueryRequest request, WorkSheetQueryParam param) {
		List<WorkSheetRecord> sheets = readService.listWorkSheets(param);
		int start = request.getPageSize() * (request.getPageNum() - 1);
		List<WorkSheetRecord> records = new ArrayList<>();
		for (int i = start; i < Math.min(start + request.getPageSize(), sheets.size()); i++) {
			records.add(sheets.get(i));
		}
		return new PageResultBean<>(records, sheets.size());
	}

	@GetMapping("/worksheet")
	public ResultBean<WorksheetPacket> getWorkSheet(Long sheetId) {
		return new ResultBean<>(readService.getWorksheet(sheetId));
	}

	@GetMapping("/samestep")
	public ResultBean<WorkStep> getSameStep(@RequestParam("sheetId") Long sheetId, @RequestParam("state") String state) {
		return new ResultBean<>(readService.findLastSameStep(sheetId, state));
	}

	@GetMapping(value = "/export")
	public void export(WorkSheetQueryParam param, HttpServletResponse response) throws IOException, Exception {
		response.setHeader("content-type", "application/force-download");
		response.setContentType("text/csv; charset=\"GBK\"");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("工单列表.csv", "UTF-8"));
		exportService.export(param, response.getOutputStream());
	}

	/**
	 * 上传图片文件
	 * 
	 * @param file
	 * @return 文件路径
	 * @throws IOException
	 */
	@PostMapping("/upload")
	public ResultBean<String> upload(MultipartFile file) throws IOException {
		return new ResultBean<>(flowService.upload(file));
	}

	/**
	 * 删除上传的图片
	 * 
	 * @param filepaths
	 * @return
	 */
	@PostMapping("/delete")
	public ResultBean<String> delete(@RequestParam("filepaths") String filepaths) {
		return new ResultBean<>(flowService.delete(filepaths));
	}

	/**
	 * 创建工单并提交
	 * 
	 * @param requestBean
	 * @return
	 * @throws FlowException
	 */
	@PostMapping("/create")
	@Transactional
	@Log("创建工单并提交")
	public ResultBean<String> createAndCommit(@RequestBody WorkDetail requestBean) throws FlowException {
		return new ResultBean<>(flowService.createAndCommit(requestBean));
	}

	/**
	 * 提交工单
	 * 
	 * @param requestBean
	 * @return
	 * @throws FlowException
	 */
	@PostMapping("/commit")
	@Transactional
	@Log("提交工单")
	public ResultBean<String> commit(@RequestBody CommitBean<WorkDetail> requestBean) throws FlowException {
		return new ResultBean<>(flowService.commitWorkSheet(requestBean.getSheetId(), requestBean.getObject()));
	}

	/**
	 * 售后
	 * 
	 * @param requestBean
	 * @return
	 * @throws FlowException
	 */
	@PostMapping("/aftersale")
	@Transactional
	@Log("提交售后审核")
	public ResultBean<WorkSheet> passAfterSale(@RequestBody CommitBean<AfterSaleInput> requestBean) throws FlowException {
		return new ResultBean<>(flowService.commitAfterSale(requestBean.getSheetId(), requestBean.isResult(), requestBean.getObject()));
	}

	@PostMapping("/terminate")
	@Transactional
	public ResultBean<WorkSheet> terminate(@RequestBody CommitBean<AfterSaleInput> requestBean) throws FlowException {
		return new ResultBean<>(flowService.terminate(requestBean.getSheetId(), requestBean.isResult(), requestBean.getObject()));
	}

	@PostMapping("/qualityinvest")
	@Transactional
	@Log("提交质量调查信息")
	public ResultBean<WorkSheet> commitQualityInvest(@RequestBody CommitBean<QualityInvestInput> requestBean) throws FlowException {
		return new ResultBean<>(flowService.commitQualityInvest(requestBean.getSheetId(), requestBean.isResult(), requestBean.getObject()));
	}

	@PostMapping("/quality")
	@Transactional
	@Log("提交质量审核信息")
	public ResultBean<WorkSheet> passQuality(@RequestBody CommitBean<CommonInput> requestBean) throws FlowException {
		return new ResultBean<>(flowService.commitQuality(requestBean.getSheetId(), requestBean.isResult(), requestBean.getObject()));
	}

	@PostMapping("/resolve")
	@Transactional
	@Log("提交问题解决信息")
	public ResultBean<WorkSheet> resolveProblem(@RequestBody CommitBean<CommonInput> requestBean) throws FlowException {
		return new ResultBean<>(flowService.resolveProblem(requestBean.getSheetId(), requestBean.isResult(), requestBean.getObject()));
	}

	/**
	 * 问题解决驳回
	 * 
	 * @param requestBean
	 * @return
	 * @throws FlowException
	 */
	@PostMapping("/appeal")
	@Transactional
	public ResultBean<WorkSheet> appeal(@RequestBody CommitBean<CommonInput> requestBean) throws FlowException {
		return new ResultBean<>(flowService.appeal(requestBean.getSheetId(), requestBean.isResult(), requestBean.getObject()));
	}

	@PostMapping("/close")
	@Transactional
	@Log("提交问题跟踪信息")
	public ResultBean<WorkSheet> close(@RequestBody CommitBean<CommonInput> requestBean) throws FlowException {
		return new ResultBean<>(flowService.closeWorkSheet(requestBean.getSheetId(), requestBean.isResult(), requestBean.getObject()));
	}

	/**
	 * 问题跟踪驳回
	 * 
	 * @param requestBean
	 * @return
	 * @throws FlowException
	 */
	@PostMapping("/repetition")
	@Transactional
	public ResultBean<WorkSheet> repetition(@RequestBody CommitBean<CommonInput> requestBean) throws FlowException {
		return new ResultBean<>(flowService.problemRepetition(requestBean.getSheetId(), requestBean.isResult(), requestBean.getObject()));
	}

	@GetMapping("/getFaultTypes")
	public ResultBean<List<FaultType>> getFaultTypes() {
		return new ResultBean<>(readService.getFaultTypes());
	}
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}
}
