package com.hirain.qsy.shaft.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.qsy.shaft.common.annotation.DeleteRedisCache;
import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.util.HttpContextUtils;
import com.hirain.qsy.shaft.common.util.IPUtils;
import com.hirain.qsy.shaft.common.util.RedisService;
import com.hirain.qsy.shaft.model.InitialAxleData;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.InitialDataService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/initialdata")
@Slf4j
public class InitialDataController {

	@Autowired
	InitialDataService initialDataService;

	@Autowired
	RedisService redisService;

	@PostMapping("/uploadfile")
	@DeleteRedisCache
	@RequiresAuthentication
	public void saveInitialData(List<MultipartFile> files, int count) {
		log.info(count + "");
		if (files != null) {
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
			String ip = IPUtils.getIpAddr(request);
			String realPath = System.getProperty("user.dir") + File.separator + ip;
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdir();
			}
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			for (MultipartFile multipartFile : files) {
				String originalFilename = multipartFile.getOriginalFilename();
				File targetFile = new File(realPath + File.separator + originalFilename);
				try {
					multipartFile.transferTo(targetFile);
				} catch (Exception e) {
					log.error("复制文件失败", e);
				}
				try {
					initialDataService.importFile(realPath, originalFilename, user.getUsername(), ip);
				} catch (Exception e) {
					log.error("上传文件失败", e);
				}
			}
		}
	}

	@GetMapping("/getdata")
	@RequiresAuthentication
	@Log("历史数据查询")
	public ResponseBo getInitialData(String trainType, String trainNum, String startDate, String endDate, int axleNum) {

		try {
			List<InitialAxleData> list = initialDataService.findByTrainInfoAndTime(trainType, trainNum, startDate, endDate, axleNum);
			return ResponseBo.ok(list);
		} catch (Exception e) {
			log.error("获取轴信息失败", e);
			return ResponseBo.error("获取轴信息失败，请联系网站管理员！");
		}
	}

	@GetMapping("/listLastMonthExceptionData")
	@RequiresAuthentication
	public ResponseBo listLastMonthExceptionData(QueryRequest request, String trainType, String trainNum) {
		try {
			Map<String, Object> map = initialDataService.listLastMonthExceptionData(request, trainType, trainNum);
			return ResponseBo.ok(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseBo.error("系统异常");
		}
	}
}
