package com.hirain.phm.bd.ground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.hirain.phm.bd")
@MapperScan({ "com.hirain.phm.bd.ground.**.dao" })
@EnableTransactionManagement
@EnableFeignClients
@ServletComponentScan
public class GroundWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroundWebApplication.class, args);
	}
}
