package com.hirain.phm.bd.ground.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hirain.phm.bd.data.DataQueryService;
import com.hirain.phm.bd.ground.bigdata.service.TestDataQueryServiceImpl;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.hirain.phm.bd.ground")
@MapperScan("com.hirain.phm.bd.ground.**.dao")
public class BigDataTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigDataTestApplication.class, args);
	}

	@Bean
	public DataQueryService get() {
		return new TestDataQueryServiceImpl();
	}
}
