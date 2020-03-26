package com.hirain.phm.bd.ground.fault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.hirain.phm.bd.ground")
@MapperScan("com.hirain.phm.bd.ground.**.dao")
@EnableFeignClients
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
}
