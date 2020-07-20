package com.hirain.phm.bd.ground.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan({ "com.hirain.phm.bd.ground.**.dao", "com.hirain.phm.bd.ground.**.**.dao" })
// @EnableFeignClients
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
}