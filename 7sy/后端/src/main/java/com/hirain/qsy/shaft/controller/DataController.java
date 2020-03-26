package com.hirain.qsy.shaft.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.annotation.DeleteRedisCache;
import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.service.DataService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月2日 下午5:15:28
 * @Description
 *              <p>
 *              平台数据管理controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月2日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {

	@Autowired
	DataService dataService;

	@GetMapping("/list")
	@RequiresAuthentication
	public ResponseBo list(QueryRequest request, String trainType, String trainNum) {
		try {
			Map<String, Object> map = dataService.getList(request, trainType, trainNum);
			return ResponseBo.ok(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseBo.error("系统异常");
		}
	}

	@GetMapping("/getStorage")
	@RequiresAuthentication
	public ResponseBo getStorage() {
		try {
			Map<String, Object> map = dataService.getStorage();
			return ResponseBo.ok(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseBo.error("系统异常");
		}

	}

	@Log("删除平台数据")
	@DeleteRedisCache
	@PostMapping("/delete")
	@RequiresAuthentication
	public ResponseBo delete(Integer trainId, String deadline) {
		try {
			dataService.delete(trainId, deadline);
			return ResponseBo.ok();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseBo.error("系统异常");
		}
	}

	@Log("删除平台数据")
	@DeleteRedisCache
	@PostMapping("/dropTable")
	@RequiresAuthentication
	public ResponseBo dropTable(Integer trainId) {
		try {
			dataService.dropTable(trainId);
			return ResponseBo.ok();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseBo.error("系统异常");
		}
	}

}
